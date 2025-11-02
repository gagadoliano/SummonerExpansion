package summonerexpansion.summonarmorsetbonus;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.RavenlordsSetBonusBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.RavenLordFeatherFollowingMob;
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import summonerexpansion.summonminions.SetRavenlordMinion;

import java.awt.*;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public class RavenlordSummonerSetBonus extends SetBonusBuff
{
    public FloatUpgradeValue featherDamage = (new FloatUpgradeValue(0.0F, 0.2F)).setBaseValue(50.0F).setUpgradedValue(1.0F, 50.0F);
    public static int FEATHER_SPAWN_RUN_DISTANCE = 2800;

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.getGndData().setDouble("distanceRan", buff.owner.getDistanceRan());
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        Mob owner = buff.owner;
        if (owner.isItemAttacker)
        {
            double distanceRan = owner.getDistanceRan();
            double distanceRanSinceLastFeatherSpawn = buff.getGndData().getDouble("distanceRan");
            if (distanceRan - distanceRanSinceLastFeatherSpawn > (double)FEATHER_SPAWN_RUN_DISTANCE)
            {
                this.summonFeather(buff, (ItemAttackerMob)owner);
                buff.getGndData().setDouble("distanceRan", distanceRan);
            }
        }
    }

    private void summonFeather(ActiveBuff buff, ItemAttackerMob itemAttacker)
    {
        if (itemAttacker.isServer() && itemAttacker.serverFollowersManager.getFollowerCount("ravenlordminion") < 6.0F)
        {
            SetRavenlordMinion mob = new SetRavenlordMinion();
            itemAttacker.serverFollowersManager.addFollower("ravenlordminion", mob, FollowPosition.LARGE_PYRAMID, "summonedmob", 1.0F, 6, null, false);
            mob.updateDamage(new GameDamage(DamageTypeRegistry.SUMMON, featherDamage.getValue(buff.getUpgradeTier())));
            itemAttacker.getLevel().entityManager.addMob(mob, itemAttacker.x, itemAttacker.y);
        }
    }

    public static float getFinalDamage(Mob mob, float baseDamage)
    {
        return (mob.buffManager.getModifier(BuffModifiers.SPEED) - 1.0F) * baseDamage;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = this.featherDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage = getFinalDamage(currentValues.owner, damage) * GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip ravenDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "ravenlordsummonsettip")).append("\n").append(new LocalMessage("itemtooltip", "ravenlordsummonsettip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = this.featherDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage = getFinalDamage(lastValues.owner, compareDamage) * GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }

            ravenDamageTip.setCompareValue(compareDamage);
        }
        list.add(ravenDamageTip);
    }
}