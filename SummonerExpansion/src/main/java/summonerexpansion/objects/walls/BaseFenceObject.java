package summonerexpansion.objects.walls;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.FenceObject;

import java.awt.*;

public class BaseFenceObject extends FenceObject
{
    public BaseFenceObject(String textureName, Color mapColor, int collisionWidth, int collisionHeight, int torchYOffset)
    {
        super(textureName, mapColor, collisionWidth, collisionHeight, torchYOffset);
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/walls/" + this.getStringID());
    }
}