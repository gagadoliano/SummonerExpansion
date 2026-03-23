package summonerexpansion.objects.decoration;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.TableDecorationObject;

import java.awt.*;
import java.io.FileNotFoundException;

public class BaseTableDecorationObject extends TableDecorationObject
{
    public BaseTableDecorationObject(String textureName, ToolType toolType, Color mapColor, int decorationWidth, int decorationHeight, int holderDrawXOffset, int holderDrawYOffset)
    {
        super(textureName, toolType, mapColor, decorationWidth, decorationHeight, holderDrawXOffset, holderDrawYOffset);
        this.textureName = textureName;
        this.toolType = toolType;
        this.mapColor = mapColor;
        this.decorationWidth = decorationWidth;
        this.decorationHeight = decorationHeight;
        this.holderDrawXOffset = holderDrawXOffset;
        this.holderDrawYOffset = holderDrawYOffset;
    }

    public BaseTableDecorationObject(String textureName, Color mapColor, int decorationWidth, int decorationHeight, int holderDrawXOffset, int holderDrawYOffset)
    {
        this(textureName, ToolType.ALL, mapColor, decorationWidth, decorationHeight, holderDrawXOffset, holderDrawYOffset);
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/decorations/" + this.textureName);

        try
        {
            this.texture_decor = GameTexture.fromFileRaw("objects/decorations/" + this.textureName + "_decor");
        }
        catch (FileNotFoundException var2)
        {
            this.texture_decor = this.texture;
        }
    }
}