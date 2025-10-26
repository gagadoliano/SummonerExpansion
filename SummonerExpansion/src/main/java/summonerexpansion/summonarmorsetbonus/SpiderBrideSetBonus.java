package summonerexpansion.summonarmorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

import java.awt.geom.Point2D;
import java.util.LinkedList;

public class SpiderBrideSetBonus extends SetBonusBuff implements BuffAbility
{
    public FloatUpgradeValue spiderDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(35F).setUpgradedValue(1.0F, 35.0F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);

    public SpiderBrideSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setMaxModifier(BuffModifiers.SLOW, 0.0F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON)
        {
            event.target.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.WIDOW_POISON, event.target, 5.0F, event.attacker), event.target.isServer());
        }
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        float cooldown = 75F;

        if (player.isServer())
        {
            GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, spiderDamage.getValue(buff.getUpgradeTier()));
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("spiderbrideminion", buff.owner.getLevel());
            attackerMob.serverFollowersManager.addFollower("spiderbridebuff", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1F, 1, null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(mob, buff.owner.getLevel(), attackerMob.x, attackerMob.y);
            mob.updateDamage(damage);
            mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("spiderbridehelmet"), CheckSlotType.HELMET);
            mob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
        }

        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("spiderbridecooldown"), player, cooldown, null), false);
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.buffManager.hasBuff(BuffRegistry.getBuff("spiderbridecooldown"));
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "spiderbridesettip"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, false).addLastValues(lastValues).excludeLimits(BuffModifiers.SLOW).buildToStatList(list);
    }
}