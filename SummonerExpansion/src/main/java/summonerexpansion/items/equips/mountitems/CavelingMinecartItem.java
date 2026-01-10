package summonerexpansion.items.equips.mountitems;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.*;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.mountItem.MountItem;

public class CavelingMinecartItem extends MountItem
{
    public CavelingMinecartItem(Item.Rarity rarityTier)
    {
        super("cavelingminecartmount");
        setMounterPos = false;
        rarity = rarityTier;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = getBaseTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "cavelingminecarttip"));
        return tooltips;
    }
}