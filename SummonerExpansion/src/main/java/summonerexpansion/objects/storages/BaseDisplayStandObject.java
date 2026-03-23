package summonerexpansion.objects.storages;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.container.DisplayStandObject;

import java.awt.*;

public class BaseDisplayStandObject extends DisplayStandObject
{
    public BaseDisplayStandObject(String textureName, ToolType toolType, Color mapColor, int itemHeight, String... category)
    {
        super(textureName, toolType, mapColor, itemHeight, category);
        this.textureName = textureName;
        this.toolType = toolType;
        this.mapColor = mapColor;
        this.itemHeight = itemHeight;
        if (category.length > 0)
        {
            this.setItemCategory(category);
            this.setCraftingCategory(category);
        }
        else
        {
            this.setItemCategory("objects", "furniture");
            this.setCraftingCategory("objects", "furniture");
        }
    }

    public BaseDisplayStandObject(String textureName, Color mapColor, int itemHeight, String... category)
    {
        this(textureName, ToolType.ALL, mapColor, itemHeight, category);
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/storages/" + this.textureName);
    }
}
