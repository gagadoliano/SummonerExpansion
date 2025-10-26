package summonerexpansion.summonarmorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasKilledEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.RicochetableProjectile;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class PharaohsMaskSetBonus extends SetBonusBuff
{
    public IntUpgradeValue maxLocustCount = (new IntUpgradeValue()).setBaseValue(2).setUpgradedValue(1F, 4).setUpgradedValue(10F, 10);
    public FloatUpgradeValue locustDamage = (new FloatUpgradeValue(0.0F, 0.2F)).setBaseValue(28.0F).setUpgradedValue(1.0F, 50.0F);
    public int locustCooldown;

    public PharaohsMaskSetBonus()
    {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        locustCooldown = 30;
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        if (locustCooldown < 30)
        {
            locustCooldown++;
        }
    }

    public void onHasKilledTarget(ActiveBuff buff, MobWasKilledEvent event)
    {
        ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
        if (buff.owner.isItemAttacker)
        {
            float count = attackerMob.serverFollowersManager.getFollowerCount("locustminion");
            if (count < maxLocustCount.getValue(buff.getUpgradeTier()) && locustCooldown >= 30)
            {
                GameDamage explosionDamge = new GameDamage(DamageTypeRegistry.SUMMON, locustDamage.getValue(buff.getUpgradeTier()));
                AttackingFollowingMob locust = (AttackingFollowingMob) MobRegistry.getMob("locustminion", attackerMob.getLevel());
                (attackerMob).serverFollowersManager.addFollower("locustminion", locust, FollowPosition.WALK_CLOSE, "summonedlocust", 1.0F, 10, null, false);
                Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(locust, attackerMob.getLevel(), attackerMob.x, attackerMob.y);
                locust.updateDamage(explosionDamge);
                attackerMob.getLevel().entityManager.addMob(locust, spawnPoint.x, spawnPoint.y);
                locustCooldown = 0;
            }
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "pharaohsmasksettip", "value", maxLocustCount.getValue(ab.getUpgradeTier())));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}