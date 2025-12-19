package summonerexpansion.items.equips.mounts;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.*;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.mountItem.MountItem;

public class ChieftainHat extends MountItem
{
    public ChieftainHat(Item.Rarity rarityTier)
    {
        super("chiefsummonmount");
        rarity = rarityTier;
    }

    @Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "chieftainhattip"));
        return  tooltips;
    }
}
