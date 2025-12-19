package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasKilledEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class PharaohsMaskSetBonus extends SetBonusBuff
{
    public IntUpgradeValue armorPen = (new IntUpgradeValue()).setBaseValue(5).setUpgradedValue(1F, 10).setUpgradedValue(10F, 20);
    public IntUpgradeValue maxLocustCount = (new IntUpgradeValue()).setBaseValue(2).setUpgradedValue(1F, 4).setUpgradedValue(10F, 10);
    public FloatUpgradeValue locustDamage = (new FloatUpgradeValue(0.0F, 0.2F)).setBaseValue(28.0F).setUpgradedValue(1.0F, 50.0F);
    public int locustCooldown;

    public PharaohsMaskSetBonus()
    {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        locustCooldown = 30;
        buff.setModifier(BuffModifiers.ARMOR_PEN_FLAT, armorPen.getValue(buff.getUpgradeTier()));
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
            float count = attackerMob.serverFollowersManager.getFollowerCount("summonedlocustbuff");
            if (count < maxLocustCount.getValue(buff.getUpgradeTier()) && locustCooldown >= 30)
            {
                GameDamage explosionDamage = new GameDamage(DamageTypeRegistry.SUMMON, locustDamage.getValue(buff.getUpgradeTier()));
                AttackingFollowingMob locust = (AttackingFollowingMob) MobRegistry.getMob("locustminion", attackerMob.getLevel());
                (attackerMob).serverFollowersManager.addFollower("summonedlocustbuff", locust, FollowPosition.WALK_CLOSE, "summonedlocust", 1.0F, 10, null, false);
                Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(locust, attackerMob.getLevel(), attackerMob.x, attackerMob.y);
                locust.updateDamage(explosionDamage);
                attackerMob.getLevel().entityManager.addMob(locust, spawnPoint.x, spawnPoint.y);
                locustCooldown = 0;
            }
        }
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = locustDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip minionDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder()).append(Localization.translate("itemtooltip", "pharaohsmasksettip", "amount", maxLocustCount.getValue(currentValues.getUpgradeTier()))).append("\n").append(new LocalMessage("itemtooltip", "pharaohsmasksettip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = locustDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }
            minionDamageTip.setCompareValue(compareDamage);
        }
        list.add(minionDamageTip);
    }
}