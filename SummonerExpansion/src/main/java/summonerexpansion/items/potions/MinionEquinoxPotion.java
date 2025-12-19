package summonerexpansion.items.potions;

import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionEquinoxPotion extends SimplePotionItem
{
    public MinionEquinoxPotion()
    {
        super(100, Rarity.COMMON,"minionequinoxbuff", 300, "minionequinoxtip");
        setItemCategory(ItemCategory.craftingManager, "consumable", "buffpotions");
    }
}