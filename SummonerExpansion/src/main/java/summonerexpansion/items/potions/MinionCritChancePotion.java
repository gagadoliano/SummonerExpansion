package summonerexpansion.items.potions;

import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionCritChancePotion extends SimplePotionItem
{
    public MinionCritChancePotion()
    {
        super(100, Rarity.COMMON,"minioncritchancebuff", 300, "minioncritchancetip");
        setItemCategory(ItemCategory.craftingManager, "consumable", "buffpotions");
    }
}
