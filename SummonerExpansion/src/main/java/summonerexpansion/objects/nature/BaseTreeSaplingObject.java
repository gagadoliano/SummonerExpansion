package summonerexpansion.objects.nature;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.TreeSaplingObject;

import java.awt.*;

public class BaseTreeSaplingObject extends TreeSaplingObject
{
    public BaseTreeSaplingObject(String textureName, Color mapColor, String resultObjectStringID, int minGrowTimeInSeconds, int maxGrowTimeInSeconds, boolean addAnySaplingIngredient, String... additionalValidTiles)
    {
        super(textureName, mapColor, resultObjectStringID, minGrowTimeInSeconds, maxGrowTimeInSeconds, addAnySaplingIngredient, additionalValidTiles);
    }

    public BaseTreeSaplingObject(String textureName, Color mapColor, String resultObjectStringID, int minGrowTimeInSeconds, int maxGrowTimeInSeconds, boolean addAnySaplingIngredient)
    {
        super(textureName, mapColor, resultObjectStringID, minGrowTimeInSeconds, maxGrowTimeInSeconds, addAnySaplingIngredient, new String[0]);
    }

    public String getForestryResultObjectStringID() {
        return this.resultObjectStringID;
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }
}
