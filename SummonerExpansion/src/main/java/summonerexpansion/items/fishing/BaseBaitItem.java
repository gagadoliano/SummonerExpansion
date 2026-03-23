package summonerexpansion.items.fishing;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.baitItem.BaitItem;

public class BaseBaitItem extends BaitItem
{
    public BaseBaitItem(boolean sinks, int fishingPower)
    {
        super(sinks, fishingPower);
        this.sinks = sinks;
        this.fishingPower = fishingPower;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/fishing/" + getStringID());
    }
}
