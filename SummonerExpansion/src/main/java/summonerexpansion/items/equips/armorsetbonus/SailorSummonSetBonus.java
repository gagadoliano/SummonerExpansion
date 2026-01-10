package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import summonerexpansion.mobs.summonminions.setminions.SetSailorMinion;

import java.awt.*;
import java.util.LinkedList;

public class SailorSummonSetBonus extends SetBonusBuff
{
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1, 0.10F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2);
    public FloatUpgradeValue minionDamage = (new FloatUpgradeValue()).setBaseValue(10F).setUpgradedValue(1, 20F).setUpgradedValue(10, 50F);
    public IntUpgradeValue minionGroupSize = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2).setUpgradedValue(10F, 10);
    public int summonTimer = 0;

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SPEED, speed.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        Mob owner = buff.owner;
        if (owner.isItemAttacker && owner.isRiding())
        {
            summonTimer++;
            if (summonTimer >= 300)
            {
                summonSailor(buff, (ItemAttackerMob)owner);
                summonTimer = 0;
            }
        }
    }

    private void summonSailor(ActiveBuff buff, ItemAttackerMob itemAttacker)
    {
        if (itemAttacker.isServer() && itemAttacker.serverFollowersManager.getFollowerCount("summonedsetsailorbuff") < minionGroupSize.getValue(buff.getUpgradeTier()))
        {
            SetSailorMinion mob = new SetSailorMinion();
            itemAttacker.serverFollowersManager.addFollower("summonedsetsailorbuff", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 10, null, false);
            mob.updateDamage(new GameDamage(DamageTypeRegistry.SUMMON, minionDamage.getValue(buff.getUpgradeTier())));
            mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("sailorsummonhat"), CheckSlotType.HELMET);
            itemAttacker.getLevel().entityManager.addMob(mob, itemAttacker.x, itemAttacker.y);
        }
    }

    public static float getFinalDamage(Mob mob, float baseDamage)
    {
        return (mob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY) - 1.0F) * baseDamage;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = minionDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage = getFinalDamage(currentValues.owner, damage) * GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip minionDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "sailorsettip")).append("\n").append(new LocalMessage("itemtooltip", "sailorsettip2", "damage", getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = minionDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage = getFinalDamage(lastValues.owner, compareDamage) * GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }

            minionDamageTip.setCompareValue(compareDamage);
        }
        list.add(minionDamageTip);
    }
}