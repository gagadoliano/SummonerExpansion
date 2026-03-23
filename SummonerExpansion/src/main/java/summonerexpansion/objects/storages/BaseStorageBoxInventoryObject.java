package summonerexpansion.objects.storages;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.container.StorageBoxInventoryObject;

import java.awt.*;
import java.io.FileNotFoundException;

public class BaseStorageBoxInventoryObject extends StorageBoxInventoryObject
{
    public String boxTooltip;

    public BaseStorageBoxInventoryObject(String textureName, int slots, ToolType toolType, Color mapColor)
    {
        super(textureName, slots, toolType, mapColor);
    }

    public BaseStorageBoxInventoryObject(String textureName, int slots, Color mapColor, String boxTooltip, String... category)
    {
        super(textureName, slots, mapColor, category);
        this.boxTooltip = boxTooltip;
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

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/storages/" + this.textureName);

        try
        {
            this.openTexture = GameTexture.fromFileRaw("objects/storages/" + this.textureName + "_open");
        }
        catch (FileNotFoundException var2)
        {
            this.openTexture = null;
        }
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", boxTooltip));
        return tooltips;
    }
}