package summonerexpansion.objects.nature;

import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.MergeFunction;
import necesse.level.gameObject.SingleOreRockSmall;

import java.awt.*;

public class BaseSingleOreRockSmall extends SingleOreRockSmall
{
    public BaseSingleOreRockSmall(String droppedStone, float toolTier, String rockLocalizationKey, String oreLocalizationKey, String rockTextureName, String oreTextureName, Color oreColor, String droppedOre, int droppedOreMin, int droppedOreMax, int placedDroppedOre, boolean isIncursionExtractionObject, String... category)
    {
        super(droppedStone, toolTier, rockLocalizationKey, oreLocalizationKey, rockTextureName, oreTextureName, oreColor, droppedOre, droppedOreMin, droppedOreMax, placedDroppedOre, isIncursionExtractionObject, category);
        this.droppedStone = droppedStone;
        this.toolTier = toolTier;
        this.rockLocalizationKey = rockLocalizationKey;
        this.oreLocalizationKey = oreLocalizationKey;
        this.rockTextureName = rockTextureName;
        this.oreTextureName = oreTextureName;
        this.mapColor = oreColor;
        this.droppedOre = droppedOre;
        this.droppedOreMin = droppedOreMin;
        this.droppedOreMax = droppedOreMax;
        this.placedDroppedOre = placedDroppedOre;
        this.isIncursionExtractionObject = isIncursionExtractionObject;
        this.oreHash = oreColor.hashCode();
    }

    public BaseSingleOreRockSmall(String droppedStone, float toolTier, String rockLocalizationKey, String oreLocalizationKey, String rockTextureName, String oreTextureName, Color oreColor, String droppedOre, int droppedOreMin, int droppedOreMax, int placedDroppedOre, String... category)
    {
        this(droppedStone, toolTier, rockLocalizationKey, oreLocalizationKey, rockTextureName, oreTextureName, oreColor, droppedOre, droppedOreMin, droppedOreMax, placedDroppedOre, true, category);
    }

    public GameTexture generateItemTexture()
    {
        GameTexture rockTexture = new GameTexture(GameTexture.fromFile("items/objects/" + this.rockTextureName));
        GameTexture oreTexture = GameTexture.fromFile("items/objects/" + this.oreTextureName);
        rockTexture.merge(oreTexture, 0, 0, MergeFunction.NORMAL);
        rockTexture.makeFinal();
        return rockTexture;
    }
}
