package summonerexpansion.summonarmorsetbonus;

import java.util.LinkedList;
import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.MobHealthChangedEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;

public class BloodplateMaskSetBonus extends SetBonusBuff
{
    public BloodplateMaskSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        eventSubscriber.subscribeEvent(MobHealthChangedEvent.class, (event) -> {
            if (event.currentHealth < event.lastHealth && !event.fromUpdatePacket)
            {
                ActiveBuff activeBuff = new ActiveBuff(BuffRegistry.BLOODPLATE_COWL_ACTIVE, buff.owner, 4.0F, null);
                buff.owner.buffManager.addBuff(activeBuff, false);
            }
        });
    }

    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bloodplatemasksettip"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}