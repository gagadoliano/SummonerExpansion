package summonerexpansion.objects.nature;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.placeableItem.tileItem.GrassSeedItem;

public class BaseGrassSeedItem extends GrassSeedItem
{
    public BaseGrassSeedItem(String grassStringID)
    {
        super(grassStringID);
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/objects/" + getStringID());
    }
}
