package summonerexpansion.summonmisc;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.miscItem.PouchItem;

public class ShadowHorrorBag extends PouchItem
{
    public ShadowHorrorBag()
    {
        this.rarity = Rarity.UNCOMMON;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "shadowbagtip"));
        tooltips.add(Localization.translate("itemtooltip", "rclickinvopentip"));
        tooltips.add(Localization.translate("itemtooltip", "storedshadowtip", "items", this.getStoredItemAmounts(item)));
        return tooltips;
    }

    public boolean isValidPouchItem(InventoryItem item) {
        return this.isValidRequestType(item.item.type);
    }

    public boolean isValidRequestItem(Item item) {
        return this.isValidRequestType(item.type);
    }

    public boolean isValidRequestType(Item.Type type) {
        return type == Type.TOOL;
    }

    public int getInternalInventorySize() {
        return 20;
    }
}