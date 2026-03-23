package summonerexpansion.items.materials;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.Item;
import necesse.inventory.item.matItem.MatItem;

public class BaseMatItem extends MatItem
{
    public BaseMatItem(int stackSize, String... globalIngredients)
    {
        super(stackSize);
        this.addGlobalIngredient(globalIngredients);
    }

    public BaseMatItem(int stackSize, Item.Rarity rarity, String... globalIngredients)
    {
        this(stackSize, globalIngredients);
        this.rarity = rarity;
    }

    public BaseMatItem(int stackSize, Item.Rarity rarity, String tooltipKey)
    {
        this(stackSize, rarity);
        this.tooltipKey = tooltipKey;
    }

    public BaseMatItem(int stackSize, Item.Rarity rarity, String tooltipKey, String... globalIngredients)
    {
        this(stackSize, rarity, globalIngredients);
        this.tooltipKey = tooltipKey;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/materials/" + getStringID());
    }
}