package summonerexpansion.items.equips.mountitems;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.mountItem.MountItem;

public class BouncingBone extends MountItem
{
    public BouncingBone(Item.Rarity rarityTier)
    {
        super("skeletonthrowersummonmount");
        rarity = rarityTier;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bouncingbonetip"));
        return  tooltips;
    }
}