package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public class TitaniumSummonSetBonus extends TitaniumSetBonus
{
    public FloatUpgradeValue summonCritChance = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1, 0.10F).setUpgradedValue(10, 0.35F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
    public FloatUpgradeValue minionDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(10F).setUpgradedValue(1, 40F);

    public TitaniumSummonSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMON_CRIT_CHANCE, summonCritChance.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        checkAndUpdateSpawnedWolfs(buff);
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, GNDItemMap attackMap)
    {
        super.onItemAttacked(buff, targetX, targetY, attackerMob, attackHeight, item, slot, animAttack, attackMap);
        if (!attackerMob.isClient())
        {
            if (buff.owner.isItemAttacker)
            {
                updateSpawnedWolfs(buff);
            }
        }
    }

    protected void checkAndUpdateSpawnedWolfs(ActiveBuff buff)
    {
        int currentActiveSummons = getCurrentActiveSummons((ItemAttackerMob)buff.owner);
        GNDItemMap gndData = buff.getGndData();
        int lastOccupiedSummons = gndData.getInt("lastOccupiedSummons");
        if (lastOccupiedSummons != currentActiveSummons)
        {
            updateSpawnedWolfs(buff);
            gndData.setInt("lastOccupiedSummons", currentActiveSummons);
        }
    }

    protected int getCurrentWolfs(ItemAttackerMob attackerMob)
    {
        return (int)attackerMob.serverFollowersManager.streamFollowers().filter((f) -> f.summonType.equals("summonedtitanium")).count();
    }

    protected int getCurrentActiveSummons(ItemAttackerMob attackerMob)
    {
        return (int)attackerMob.serverFollowersManager.getFollowerCount("summonedmob");
    }

    protected int getMaxWolfs(ItemAttackerMob attackerMob)
    {
        return getCurrentActiveSummons(attackerMob) / 4;
    }

    protected void updateSpawnedWolfs(ActiveBuff buff)
    {
        if (buff.owner.isItemAttacker)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            int currentWolfs = getCurrentWolfs(attackerMob);
            int desiredWolfs = getMaxWolfs(attackerMob);
            for(int i = currentWolfs; i < desiredWolfs; ++i)
            {
                spawnWolf(buff.owner, buff);
            }
        }
    }

    private void spawnWolf(Mob owner, ActiveBuff buff)
    {
        if (!owner.isClient())
        {
            GameDamage spiderDamage = new GameDamage(minionDamage.getValue(buff.getUpgradeTier()));
            AttackingFollowingMob wolf = (AttackingFollowingMob)MobRegistry.getMob("settitaniumsummonminion", owner.getLevel());
            ((ItemAttackerMob)owner).serverFollowersManager.addFollower("summonedtitanium", wolf, FollowPosition.PYRAMID, "summonedtitaniumminionbuff", 1.0F, this::getMaxWolfs, null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(wolf, owner.getLevel(), owner.x, owner.y);
            wolf.updateDamage(spiderDamage);
            wolf.setRemoveWhenNotInInventory(ItemRegistry.getItem("titaniumsummonhelmet"), CheckSlotType.HELMET);
            owner.getLevel().entityManager.addMob(wolf, spawnPoint.x, spawnPoint.y);
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "titaniumsettip"));
        tooltips.add(Localization.translate("itemtooltip", "titaniumsummontip"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}