package summonerexpansion.items.equips.armorsetbonus;

import java.awt.*;
import java.util.LinkedList;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffManager;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

public class AgedSummonerSetBonus extends SetBonusBuff
{
    public IntUpgradeValue maxResilience = (new IntUpgradeValue()).setBaseValue(30).setUpgradedValue(1.0F, 30);
    public FloatUpgradeValue resilienceGain = (new FloatUpgradeValue()).setBaseValue(0.2F).setUpgradedValue(1.0F, 0.2F);
    public FloatUpgradeValue summonSpeed = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(0.20F).setUpgradedValue(1.0F, 0.20F);
    public FloatUpgradeValue agedDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(25F).setUpgradedValue(1.0F, 25.0F);

    public AgedSummonerSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, maxResilience.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.RESILIENCE_GAIN, resilienceGain.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.SUMMONS_SPEED, summonSpeed.getValue(buff.getUpgradeTier()));
    }

    public void serverTick(ActiveBuff buff)
    {
        boolean fullHealth = buff.owner.getHealthPercent() == 1.0F;
        if (buff.owner.isItemAttacker && fullHealth)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("summonedagedchampionbuff");
            if (count <= 0.0F)
            {
                GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, agedDamage.getValue(buff.getUpgradeTier()));
                Level level = buff.owner.getLevel();
                AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("agedchampionminion", level);
                attackerMob.serverFollowersManager.addFollower("summonedagedchampionbuff", mob, FollowPosition.WALK_CLOSE, "summonedagedchampionminionbuff", 1.0F, 1, null, false);
                mob.updateDamage(damage);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
        else
        {
            if (buff.owner.isServer() && buff.owner.buffManager.hasBuff("summonedagedchampionminionbuff"))
            {
                buff.owner.buffManager.removeBuff("summonedagedchampionminionbuff", true);
            }
        }
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        if (buff.owner.buffManager.hasBuff(BuffRegistry.PERFECT_BLOCK))
        {
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.AGED_CHAMPION_PROWESS, buff.owner, 5000, null), false);
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

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = agedDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip minionDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "agedsummonersettip")).append("\n").append(new LocalMessage("itemtooltip", "agedsummonersettip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = agedDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }
            minionDamageTip.setCompareValue(compareDamage);
        }
        list.add(minionDamageTip);
    }
}