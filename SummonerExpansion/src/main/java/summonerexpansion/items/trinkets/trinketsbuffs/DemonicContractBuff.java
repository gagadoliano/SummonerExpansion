package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.MobWasKilledEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class DemonicContractBuff extends TrinketBuff
{
    public DemonicContractBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(BuffModifiers.BLEED_DAMAGE_FLAT, 1.50F);
    }

    public void onHasKilledTarget(ActiveBuff buff, MobWasKilledEvent event)
    {
        if (buff.owner.isServer())
        {
            if(event.target.isBoss())
            {
                for (int i = 0; i < 250; i++)
                {
                    buff.owner.getLevel().entityManager.pickups.add((new InventoryItem("coin")).getPickupEntity(buff.owner.getLevel(), buff.owner.x + GameRandom.getIntBetween(GameRandom.globalRandom, -50, 50), buff.owner.y + GameRandom.getIntBetween(GameRandom.globalRandom, -50, 50)));
                }
            }
            else if(event.target.isHostile)
            {
                for (int i = 0; i < 10; i++)
                {
                    buff.owner.getLevel().entityManager.pickups.add((new InventoryItem("coin")).getPickupEntity(buff.owner.getLevel(), buff.owner.x + GameRandom.getIntBetween(GameRandom.globalRandom, -50, 50), buff.owner.y + GameRandom.getIntBetween(GameRandom.globalRandom, -50, 50)));
                }
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "demoniccontracttip"));
        return tooltips;
    }
}
