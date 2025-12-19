package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import summonerexpansion.mobs.summonminions.setminions.*;

import java.awt.*;
import java.util.LinkedList;

public class ChefSummonerHatSetBonus extends SetBonusBuff
{
    public FloatUpgradeValue chefDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(50F).setUpgradedValue(1.0F, 50F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1F, 2);
    public IntUpgradeValue maxFood = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1F, 1).setUpgradedValue(10F, 2);
    public IntUpgradeValue minionDuration = (new IntUpgradeValue()).setBaseValue(300).setUpgradedValue(1F, 400).setUpgradedValue(10F, 1200);

    public ChefSummonerHatSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.MAX_FOOD_BUFFS, maxFood.getValue(buff.getUpgradeTier()));
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
        if (attackerMob.isServer() && attackerMob.serverFollowersManager.getFollowerCount("summonedchefbuff") < 3.0F)
        {
            SetChefMinion mob = new SetChefMinion();
            attackerMob.serverFollowersManager.addFollower("summonedchefbuff", mob, FollowPosition.LARGE_PYRAMID, "summonedmob", 1.0F, 3, null, false);
            mob.updateDamage(new GameDamage(DamageTypeRegistry.SUMMON, chefDamage.getValue(buff.getUpgradeTier())));
            mob.lifeTime = minionDuration.getValue(buff.getUpgradeTier());
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x, attackerMob.y);
        }
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = chefDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip minionDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "chefsummonerhattip")).append("\n").append(new LocalMessage("itemtooltip", "chefsummonerhattip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = chefDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }
            minionDamageTip.setCompareValue(compareDamage);
        }
        list.add(minionDamageTip);
    }
}