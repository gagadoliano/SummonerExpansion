package summonerexpansion.items.potions;

import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionCritPotion extends SimplePotionItem
{
    public MinionCritPotion()
    {
        super(100, Rarity.COMMON,"minioncritbuff", 300, "minioncrittip");
        setItemCategory(ItemCategory.craftingManager, "consumable", "buffpotions");
    }
}
