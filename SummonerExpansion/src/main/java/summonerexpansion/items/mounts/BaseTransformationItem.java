package summonerexpansion.items.mounts;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.mountItem.MountItem;

public class BaseTransformationItem extends MountItem
{
    String desctip;

    public BaseTransformationItem(Item.Rarity rarityTier, String mountMob, String toolTip)
    {
        super(mountMob);
        rarity = rarityTier;
        desctip = toolTip;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", desctip));
        return  tooltips;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/transformations/" + getStringID());
    }
}