package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class FishianEggsBuff extends TrinketBuff
{
    public FishianEggsBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(BuffModifiers.LIFE_ESSENCE_DURATION, 0.50F);
        buff.setModifier(BuffModifiers.LIFE_ESSENCE_GAIN, -0.50F);
    }

    public void serverTick(ActiveBuff buff)
    {
        updateModifiers(buff);
    }

    public void clientTick(ActiveBuff buff)
    {
        updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        if (buff.owner.buffManager.hasBuff(BuffRegistry.LIFE_ESSENCE))
        {
            if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 5 * 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 50);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.10F);
            }
            else if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 4 * 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 40);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.10F);
            }
            else if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 3 * 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 30);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.10F);
            }
            else if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 2 * 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 20);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.10F);
            }
            else if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 10);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.10F);
            }
            else
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 0);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.0F);
            }
        }
        else
        {
            buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 0);
            buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.0F);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "fishianeggstip"));
        return tooltips;
    }
}