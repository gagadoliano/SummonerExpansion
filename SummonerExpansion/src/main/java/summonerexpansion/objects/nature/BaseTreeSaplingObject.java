package summonerexpansion.objects.nature;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.TreeSaplingObject;

public class BaseTreeSaplingObject extends TreeSaplingObject
{
    public BaseTreeSaplingObject(String textureName, String resultObjectStringID, int minGrowTimeInSeconds, int maxGrowTimeInSeconds, boolean addAnySaplingIngredient, String... additionalValidTiles)
    {
        super(textureName, resultObjectStringID, minGrowTimeInSeconds, maxGrowTimeInSeconds, addAnySaplingIngredient, additionalValidTiles);
    }

    public BaseTreeSaplingObject(String textureName, String resultObjectStringID, int minGrowTimeInSeconds, int maxGrowTimeInSeconds, boolean addAnySaplingIngredient)
    {
        super(textureName, resultObjectStringID, minGrowTimeInSeconds, maxGrowTimeInSeconds, addAnySaplingIngredient, new String[0]);
    }

    public String getForestryResultObjectStringID() {
        return this.resultObjectStringID;
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }
}
