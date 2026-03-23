package summonerexpansion.objects.walls;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.RockObject;

import java.awt.*;

public class BaseRockWallObject extends RockObject
{
    public BaseRockWallObject(String rockTexture, Color rockColor, String droppedStone, int minStoneAmount, int maxStoneAmount, int placedStoneAmount, String... category)
    {
        super(rockTexture, rockColor, droppedStone, minStoneAmount, maxStoneAmount, placedStoneAmount, category);
    }

    public void loadTextures() {
        rockTextures = GameTexture.fromFile("objects/walls/" + this.rockTextureName);
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/walls/" + this.getStringID());
    }
}
