package summonerexpansion.objects.walls;

import necesse.level.gameObject.FenceGateObject;

import java.awt.*;

public class BaseFenceGateObject extends FenceGateObject
{
    public BaseFenceGateObject(int counterID, boolean isOpen, String textureName, Color mapColor, int collisionWidth, int collisionHeight)
    {
        super(counterID, isOpen, textureName, mapColor, collisionWidth, collisionHeight);
    }
}