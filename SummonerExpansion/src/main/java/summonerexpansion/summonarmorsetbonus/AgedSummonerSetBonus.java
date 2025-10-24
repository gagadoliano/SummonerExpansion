package summonerexpansion.summonarmorsetbonus;

import java.util.LinkedList;
import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

public class AgedSummonerSetBonus extends SetBonusBuff
{
    public IntUpgradeValue maxResilience = (new IntUpgradeValue()).setBaseValue(30).setUpgradedValue(1.0F, 30);
    public FloatUpgradeValue resilienceGain = (new FloatUpgradeValue()).setBaseValue(0.2F).setUpgradedValue(1.0F, 0.2F);

    public AgedSummonerSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, maxResilience.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.RESILIENCE_GAIN, resilienceGain.getValue(buff.getUpgradeTier()));
    }

    public void clientTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void serverTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    private void updateModifiers(ActiveBuff buff)
    {
        boolean fullHealth = buff.owner.getHealthPercent() == 1.0F;
        buff.setModifier(BuffModifiers.RESILIENCE_REGEN_FLAT, fullHealth ? 1.0F : 0.0F);
        buff.setModifier(BuffModifiers.SUMMON_CRIT_CHANCE, fullHealth ? 0.2F : 0.0F);
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        if (buff.owner.buffManager.hasBuff(BuffRegistry.PERFECT_BLOCK))
        {
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.AGED_CHAMPION_PROWESS, buff.owner, 5000, null), false);
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "agedsummonersettip"));
        return tooltips;
    }
}