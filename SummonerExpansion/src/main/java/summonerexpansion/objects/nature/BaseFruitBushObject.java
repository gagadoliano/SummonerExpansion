package summonerexpansion.objects.nature;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.FruitBushObject;

import java.awt.*;

public class BaseFruitBushObject extends FruitBushObject
{
    public BaseFruitBushObject(String textureName, String seedStringID, float minGrowTimeSeconds, float maxGrowTimeSeconds, String fruitStringID, float fruitPerStage, int maxStage, Color mapColor)
    {
        super(textureName, seedStringID, minGrowTimeSeconds, maxGrowTimeSeconds, fruitStringID, fruitPerStage, maxStage);
        this.mapColor = mapColor;
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }
}
