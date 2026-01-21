package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class TimewornCoinsBuff extends TrinketBuff
{
    public int hitCount;
    public TimewornCoinsBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(BuffModifiers.SPEED, 0.20F);
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        if (!event.wasPrevented && buff.owner.isServer())
        {
            if (hitCount < 5)
            {
                hitCount += 1;
            }
            else
            {
                for (int i = 0; i < 5; i++)
                {
                    buff.owner.getLevel().entityManager.pickups.add((new InventoryItem("coin")).getPickupEntity(buff.owner.getLevel(), buff.owner.x + GameRandom.getIntBetween(GameRandom.globalRandom, -50, 50), buff.owner.y + GameRandom.getIntBetween(GameRandom.globalRandom, -50, 50)));
                }
                hitCount = 0;
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "timeworncoinstip"));
        return tooltips;
    }
}