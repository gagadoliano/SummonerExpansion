package summonerexpansion.items.foods;

import necesse.engine.modifiers.ModifierValue;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.Item;
import necesse.inventory.item.placeableItem.consumableItem.food.FoodConsumableItem;
import necesse.level.maps.levelData.settlementData.settler.FoodQuality;

public class BaseFoodItemConsumable extends FoodConsumableItem
{
    public BaseFoodItemConsumable(int stackSize, Item.Rarity rarity, FoodQuality quality, int nutrition, int buffSecondsDuration, boolean drinkSound, ModifierValue<?>... modifiers)
    {
        super(stackSize, rarity, quality, nutrition, buffSecondsDuration, drinkSound, modifiers);
        this.duration = buffSecondsDuration;
        this.rarity = rarity;
        this.quality = quality;
        this.nutrition = nutrition;
        this.drinkSound = drinkSound;
        this.modifiers = modifiers;
        if (quality != null)
        {
            this.setItemCategory(quality.masterCategoryTree);
        }
        else
        {
            this.setItemCategory("consumable", "food");
        }
        this.keyWords.add("food");
        this.dropDecayTimeMillis = 1800000L;
        this.dropsAsMatDeathPenalty = false;
    }

    public BaseFoodItemConsumable(int stackSize, Item.Rarity rarity, FoodQuality quality, int nutrition, int buffSecondsDuration, ModifierValue<?>... modifiers)
    {
        this(stackSize, rarity, quality, nutrition, buffSecondsDuration, false, modifiers);
    }

    protected void loadItemTextures()
    {
        if (this.cropTextureName != null)
        {
            this.itemTexture = new GameTexture(GameTexture.fromFile("objects/" + this.cropTextureName), 0, 0, 32);
        }
        else
        {
            this.itemTexture = GameTexture.fromFile("items/foods/" + this.getStringID(), true);
        }

    }
}
