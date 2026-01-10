package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffManager;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.LinkedList;

public class RainSummonSetBonus extends SetBonusBuff
{
    public FloatUpgradeValue summonRange = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1, 0.10F).setUpgradedValue(10, 0.50F);
    public IntUpgradeValue fishLine = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 3).setUpgradedValue(10, 5);
    public IntUpgradeValue fishLine2 = (new IntUpgradeValue()).setBaseValue(2).setUpgradedValue(1, 6).setUpgradedValue(10, 10);
    public FloatUpgradeValue minionDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(5F).setUpgradedValue(1, 25.0F);

    public RainSummonSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMONS_TARGET_RANGE, summonRange.getValue(buff.getUpgradeTier()));
        updateModifiers(buff);
    }

    public void clientTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void serverTick(ActiveBuff buff)
    {
        updateModifiers(buff);
        if (buff.owner.isItemAttacker)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("summonedcloudminion");
            if (count <= 0.0F)
            {
                GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, minionDamage.getValue(buff.getUpgradeTier()));
                Level level = buff.owner.getLevel();
                FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("setcloudminion", level);
                attackerMob.serverFollowersManager.addFollower("summonedcloudminion", mob, FollowPosition.WALK_CLOSE, "summonedcloudminionbuff", 1.0F, 1, null, false);
                mob.updateDamage(damage);
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("rainsummonhat"), CheckSlotType.HELMET);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
        else
        {
            if (buff.owner.isServer() && buff.owner.buffManager.hasBuff("summonedcloudminionbuff"))
            {
                buff.owner.buffManager.removeBuff("summonedcloudminionbuff", true);
            }
        }
    }

    public void onRemoved(ActiveBuff buff)
    {
        super.onRemoved(buff);
        BuffManager buffManager = buff.owner.buffManager;
        if (buff.owner.isServer() && buffManager.hasBuff("summonedagedchampionminionbuff"))
        {
            buffManager.removeBuff("summonedagedchampionminionbuff", true);
        }
    }

    private void updateModifiers(ActiveBuff buff)
    {
        Mob owner = buff.owner;
        if (owner.getLevel() != null)
        {
            if (owner.getLevel().weatherLayer.isRaining())
            {
                buff.setModifier(BuffModifiers.FISHING_LINES, fishLine2.getValue(buff.getUpgradeTier()));
                buff.owner.buffManager.forceUpdateBuffs();
            }
            else
            {
                buff.setModifier(BuffModifiers.FISHING_LINES, fishLine.getValue(buff.getUpgradeTier()));
                buff.owner.buffManager.forceUpdateBuffs();
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
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "rainsummonsettip")).append("\n").append(new LocalMessage("itemtooltip", "rainsummonsettip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
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