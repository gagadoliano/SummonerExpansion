package summonerexpansion.items.fishing;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.placeableItem.fishingRodItem.FishingRodItem;

public class CombinedFishingRod extends FishingRodItem
{
    public CombinedFishingRod()
    {
        super(50, 52, 46, 150, 400, 3, 80, 45, Rarity.LEGENDARY);
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/fishing/" + getStringID());
    }
}