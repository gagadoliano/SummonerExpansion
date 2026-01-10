package summonerexpansion.objects.summonobjects;

import necesse.level.gameObject.TreeObject;

import java.awt.*;

public class AncientTreeObject extends TreeObject
{
    public AncientTreeObject(String textureName, String logStringID, String saplingStringID, Color mapColor, int leavesCenterWidth, int leavesMinHeight, int leavesMaxHeight, String leavesTextureName)
    {
        super(textureName, logStringID, saplingStringID, mapColor, leavesCenterWidth,leavesMinHeight, leavesMaxHeight, leavesTextureName);
        objectHealth = 500;
    }
}
