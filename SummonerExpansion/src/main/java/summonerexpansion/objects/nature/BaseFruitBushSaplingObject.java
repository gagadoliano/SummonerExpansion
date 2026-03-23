package summonerexpansion.objects.nature;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.SaplingObject;

public class BaseFruitBushSaplingObject extends SaplingObject
{
    public BaseFruitBushSaplingObject(String textureName, String resultObjectStringID, int minGrowTimeInSeconds, int maxGrowTimeInSeconds, boolean addAnySaplingIngredient, String... additionalValidTiles)
    {
        super(textureName, resultObjectStringID, minGrowTimeInSeconds, maxGrowTimeInSeconds, addAnySaplingIngredient, additionalValidTiles);
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }
}