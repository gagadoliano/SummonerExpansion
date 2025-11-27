package summonerexpansion.items.summonpotions;

import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionRangePotion extends SimplePotionItem
{
    public MinionRangePotion()
    {
        super(100, Rarity.COMMON,"minionrangebuff", 300, "minionrangetip");
        setItemCategory(ItemCategory.craftingManager, "consumable", "buffpotions");
    }
}
