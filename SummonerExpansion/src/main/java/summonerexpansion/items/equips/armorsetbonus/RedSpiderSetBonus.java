package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.CaveSpiderSpitEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.hostile.GiantCaveSpiderMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

import java.util.LinkedList;

public class RedSpiderSetBonus extends SetBonusBuff
{
    public FloatUpgradeValue slowDuration = (new FloatUpgradeValue()).setBaseValue(5F).setUpgradedValue(1, 10F).setUpgradedValue(10, 30F);
    public FloatUpgradeValue summonDMG = (new FloatUpgradeValue()).setBaseValue(0F).setUpgradedValue(1, 0.10F).setUpgradedValue(10, 0.40F);
    public FloatUpgradeValue summonAttackSpeed = (new FloatUpgradeValue()).setBaseValue(0F).setUpgradedValue(1, 0.10F).setUpgradedValue(10, 0.40F);
    public FloatUpgradeValue minionDamage = (new FloatUpgradeValue()).setBaseValue(0F).setUpgradedValue(1, 10F).setUpgradedValue(10, 50F);
    public int summonTimer = 0;

    public RedSpiderSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setMaxModifier(BuffModifiers.SLOW, 0F);
        buff.setMaxModifier(BuffModifiers.BLINDNESS, 0F);
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, summonDMG.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, summonAttackSpeed.getValue(buff.getUpgradeTier()));
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON)
        {
            event.target.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.SPIDER_WEB_SLOW, event.target, slowDuration.getValue(buff.getUpgradeTier()), event.attacker), event.target.isServer());
            if (event.isCrit && summonTimer >= 300 && minionDamage.getValue(buff.getUpgradeTier()) > 0)
            {
                GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, minionDamage.getValue(buff.getUpgradeTier()));
                CaveSpiderSpitEvent spiderEvent = new CaveSpiderSpitEvent(buff.owner, (int)event.target.x, (int)event.target.y, GameRandom.globalRandom, GiantCaveSpiderMob.Variant.BLACK, damage, Integer.MAX_VALUE);
                buff.owner.getLevel().entityManager.events.add(spiderEvent);
                summonTimer = 0;
            }
        }
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        if (summonTimer < 600)
        {
            summonTimer++;
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "redspidersettip"));
        if (summonDMG.getValue(ab.getUpgradeTier()) > 0)
        {
            tooltips.add(Localization.translate("itemtooltip", "redspidersettip2"));
        }
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).excludeLimits(BuffModifiers.SLOW, BuffModifiers.BLINDNESS).buildToStatList(list);
    }
}