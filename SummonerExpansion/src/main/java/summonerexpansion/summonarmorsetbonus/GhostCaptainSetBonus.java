package summonerexpansion.summonarmorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
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
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import summonerexpansion.summonminions.SetGhostCaptainMinion;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class GhostCaptainSetBonus extends SetBonusBuff implements BuffAbility
{
    public FloatUpgradeValue ghostCaptainDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(50F).setUpgradedValue(1.0F, 50.0F);
    public FloatUpgradeValue summonRange = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(0.10F).setUpgradedValue(1.0F, 0.15F);
    public IntUpgradeValue minionDuration = (new IntUpgradeValue()).setBaseValue(300).setUpgradedValue(1F, 400).setUpgradedValue(10F, 1200);

    public GhostCaptainSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMONS_TARGET_RANGE, summonRange.getValue(buff.getUpgradeTier()));
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        float cooldown = 75F;

        if (player.isServer())
        {
            GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, ghostCaptainDamage.getValue(buff.getUpgradeTier()));
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            SetGhostCaptainMinion mob = new SetGhostCaptainMinion();
            attackerMob.serverFollowersManager.addFollower("ghostcaptainsbuff", mob, FollowPosition.WIDE_CIRCLE_MOVEMENT, "summonedmob", 1F, 1, null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(mob, buff.owner.getLevel(), attackerMob.x, attackerMob.y);
            mob.updateDamage(damage);
            mob.lifeTime = minionDuration.getValue(buff.getUpgradeTier());
            mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("ghostcaptainshat"), CheckSlotType.HELMET);
            attackerMob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
        }
        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("ghostcaptainscooldown"), player, cooldown, null), false);
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.buffManager.hasBuff(BuffRegistry.getBuff("ghostcaptainscooldown"));
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "ghostcaptainssettip"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = ghostCaptainDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip minionDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "ghostcaptainssettip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = ghostCaptainDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }
            minionDamageTip.setCompareValue(compareDamage);
        }
        list.add(minionDamageTip);
    }
}