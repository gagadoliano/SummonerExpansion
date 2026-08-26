package summonerexpansion.items.armors.armorsets;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.items.armors.minions.SetTitaniumMagicMinion;

import java.awt.*;
import java.util.LinkedList;

public class TitaniumMagicSetBonus extends TitaniumSetBonus
{
    public IntUpgradeValue armorPen = (new IntUpgradeValue()).setBaseValue(5).setUpgradedValue(1, 15).setUpgradedValue(10, 30);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
    public FloatUpgradeValue minionDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(10F).setUpgradedValue(1, 40F);

    public TitaniumMagicSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.ARMOR_PEN_FLAT, armorPen.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
    }

    public void serverTick(ActiveBuff buff)
    {
        boolean halfMana = buff.owner.getMana() >= ((float) buff.owner.getMaxMana() / 2);
        if (buff.owner.isItemAttacker && halfMana)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("summonedtitaniumstaff");
            if (count <= 0.0F)
            {
                Level level = buff.owner.getLevel();
                SetTitaniumMagicMinion mob = new SetTitaniumMagicMinion();
                attackerMob.serverFollowersManager.addFollower("summonedtitaniumstaff", mob, FollowPosition.WIDE_CIRCLE_MOVEMENT, "summonedtitaniumminionbuff", 1F, 1, null, false);
                mob.updateDamage(new GameDamage(DamageTypeRegistry.SUMMON, minionDamage.getValue(buff.getUpgradeTier())));
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("titaniummagichelmet"), CheckSlotType.HELMET);
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

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = minionDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip minionDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder())
                        .append(new LocalMessage("itemtooltip", "titaniumsettip"))
                        .append("\n")
                        .append(new LocalMessage("itemtooltip", "titaniummagictip"))
                        .append("\n")
                        .append(new LocalMessage("itemtooltip", "titaniummagictip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = minionDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }
            minionDamageTip.setCompareValue(compareDamage);
        }
        list.add(minionDamageTip);
    }
}