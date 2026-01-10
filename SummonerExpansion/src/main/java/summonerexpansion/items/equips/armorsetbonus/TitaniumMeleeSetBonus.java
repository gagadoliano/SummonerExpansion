package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.LinkedList;

public class TitaniumMeleeSetBonus extends TitaniumSetBonus
{
    public FloatUpgradeValue summonAttackSpeed = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1, 0.10F).setUpgradedValue(10, 0.35F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
    public FloatUpgradeValue minionDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(10F).setUpgradedValue(1, 40F);

    public TitaniumMeleeSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, summonAttackSpeed.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
    }

    public void serverTick(ActiveBuff buff)
    {
        boolean fullHealth = buff.owner.getHealthPercent() >= 0.50F;
        if (buff.owner.isItemAttacker && fullHealth)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("summonedtitaniumsword");
            if (count <= 0.0F)
            {
                GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, minionDamage.getValue(buff.getUpgradeTier()));
                Level level = buff.owner.getLevel();
                FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("settitaniummeleeminion", level);
                attackerMob.serverFollowersManager.addFollower("summonedtitaniumsword", mob, FollowPosition.WALK_CLOSE, "summonedtitaniumminionbuff", 1.0F, 1, null, false);
                mob.updateDamage(damage);
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("titaniummeleehelmet"), CheckSlotType.HELMET);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
        else
        {
            if (buff.owner.isServer() && buff.owner.buffManager.hasBuff("summonedtitaniumminionbuff"))
            {
                buff.owner.buffManager.removeBuff("summonedtitaniumminionbuff", true);
            }
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "titaniumsettip"));
        tooltips.add(Localization.translate("itemtooltip", "titaniummeleetip"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}