package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import java.util.LinkedList;

public class LeatherSummonerSetBonus extends SetBonusBuff
{
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
    public FloatUpgradeValue staminaRegen = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1, 0.25F).setUpgradedValue(10, 0.50F);
    public FloatUpgradeValue staminaMax = (new FloatUpgradeValue()).setBaseValue(0F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 1.00F);
    public FloatUpgradeValue summonDMG = (new FloatUpgradeValue()).setBaseValue(0F).setUpgradedValue(1, 0.10F).setUpgradedValue(10, 0.80F);

    public LeatherSummonerSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.STAMINA_REGEN, staminaRegen.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.STAMINA_CAPACITY, staminaMax.getValue(buff.getUpgradeTier()));
        updateModifiers(buff);
    }

    public void clientTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void serverTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    private void updateModifiers(ActiveBuff buff)
    {
        Mob owner = buff.owner;
        if (owner.isRiding())
        {
            buff.setModifier(BuffModifiers.SUMMON_DAMAGE, summonDMG.getValue(buff.getUpgradeTier()));
            buff.owner.buffManager.forceUpdateBuffs();
        }
        else
        {
            buff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0F);
            buff.owner.buffManager.forceUpdateBuffs();
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        if (summonDMG.getValue(ab.getUpgradeTier()) > 0)
        {
            tooltips.add(Localization.translate("itemtooltip", "leathersettip"));
        }
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}