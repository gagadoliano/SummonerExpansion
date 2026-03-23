package summonerexpansion.items.potions;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class BaseConsumePotion extends SimplePotionItem
{
    public BaseConsumePotion(int stack, Rarity rarity, String potionBuff, int duration, String tooltip)
    {
        super(stack, rarity,potionBuff, duration, tooltip);
        setItemCategory(ItemCategory.craftingManager, "consumable", "buffpotions");
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/potions/" + getStringID());
    }
}