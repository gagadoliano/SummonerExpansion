package summonerexpansion.items.fishing;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.placeableItem.fishingRodItem.FishingRodItem;

public class AncientWoodFishingRod extends FishingRodItem
{
    public AncientWoodFishingRod()
    {
        super(10, 46, 44, 90, 200, 2, 40, 45, Rarity.NORMAL);
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/fishing/" + getStringID());
    }
}