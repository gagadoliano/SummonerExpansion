package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import summonerexpansion.codes.registry.SummonerModifiers;

public class FlowerBroochBuff extends TrinketBuff
{
    public FlowerBroochBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(SummonerModifiers.SENTRY_ATTACK_SPEED, 0.10F);
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
        if (!buff.owner.isAttacking)
        {
            buff.setModifier(BuffModifiers.TARGET_RANGE, -0.50F);
        }
        else
        {
            buff.setModifier(BuffModifiers.TARGET_RANGE, 0.0F);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "flowerbroochtip"));
        return tooltips;
    }
}