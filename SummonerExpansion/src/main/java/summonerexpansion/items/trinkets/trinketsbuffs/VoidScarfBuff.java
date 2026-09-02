package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import summonerexpansion.codes.registries.RegistryTrinkets;

public class VoidScarfBuff extends TrinketBuff
{
    float speedValue;
    public VoidScarfBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {

    }

    public void serverTick(ActiveBuff buff)
    {
        updateModifiers(buff);
        if (speedValue < 2.00F)
        {
            speedValue += 0.001F;
        }
    }

    public void clientTick(ActiveBuff buff)
    {
        updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        if (buff.owner.isInCombat())
        {
            buff.setModifier(BuffModifiers.SPEED, 0.0F);
            speedValue = 0;
        }
        else
        {
            buff.setModifier(BuffModifiers.SPEED, speedValue);
        }
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        if (!event.wasPrevented)
        {
            buff.owner.buffManager.addBuff(new ActiveBuff(RegistryTrinkets.TrinketBuffs.VOIDSCARFSPEED, buff.owner, 3.0F, null), true);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "voidscarftip"));
        return tooltips;
    }
}