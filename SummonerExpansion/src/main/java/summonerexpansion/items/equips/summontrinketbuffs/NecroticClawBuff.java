package summonerexpansion.items.equips.summontrinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class NecroticClawBuff extends TrinketBuff
{
    public NecroticClawBuff() {}

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.KNOCKBACK_INCOMING_MOD, 0F);
        activeBuff.setModifier(BuffModifiers.ARMOR_PEN_FLAT, 25);
        activeBuff.setModifier(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.25F);
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON)
        {
            event.target.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.CHILLED, event.target, 5.0F, event.attacker), event.target.isServer());
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "necroticclawtip"));
        return tooltips;
    }
}
