package summonerexpansion.objects.storages;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.container.InventoryObject;
import necesse.level.maps.Level;

import java.awt.*;
import java.io.FileNotFoundException;

public class BeehiveChestInventoryObject extends InventoryObject
{
    public BeehiveChestInventoryObject(String textureName, int slots, ToolType toolType, Color mapColor)
    {
        super(textureName, slots, new Rectangle(32, 32), toolType, mapColor);
    }

    public BeehiveChestInventoryObject(String textureName, int slots, Color mapColor)
    {
        super(textureName, slots, new Rectangle(32, 32), mapColor);
        this.setItemCategory("objects", "furniture", "misc");
    }

    public Rectangle getCollision(Level level, int x, int y, int rotation)
    {
        return rotation % 2 == 0 ? new Rectangle(x * 32 + 3, y * 32 + 6, 26, 20) : new Rectangle(x * 32 + 6, y * 32 + 4, 20, 24);
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y)
    {
        return new BeehiveChestObjectEntity(level, x, y, 20, 0.50F);
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
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "beehivechesttip"));
        return tooltips;
    }
}