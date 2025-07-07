package summonerexpansion.summonsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import java.util.LinkedList;

public class LeatherSummonerSetBonus extends SetBonusBuff
{
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2);

    public LeatherSummonerSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.STAMINA_REGEN, 0.20F);
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}