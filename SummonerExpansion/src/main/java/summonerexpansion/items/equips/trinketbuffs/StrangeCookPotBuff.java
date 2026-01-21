package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import summonerexpansion.codes.events.CookpotHealEvent;

public class StrangeCookPotBuff extends TrinketBuff
{
    public StrangeCookPotBuff() {}

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.MAX_FOOD_BUFFS, 1);
        activeBuff.setModifier(BuffModifiers.POTION_DURATION, 1.50F);
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        float splashRange = buff.owner.buffManager.getModifier(BuffModifiers.SUMMONS_TARGET_RANGE);
        if (!event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON && GameRandom.globalRandom.getChance(0.10F))
        {
            buff.owner.getLevel().entityManager.events.add(new CookpotHealEvent(event.target.x, event.target.y, (int)(50 * splashRange), new GameDamage(0.0F), 0.0F, buff.owner));
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "strangecookpottip"));
        return tooltips;
    }
}