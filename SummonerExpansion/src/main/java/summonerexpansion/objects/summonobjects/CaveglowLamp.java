package summonerexpansion.objects.summonobjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.codes.entity.CaveglowLampObjectEntity;

import java.awt.*;
import java.util.List;

public class CaveglowLamp extends GameObject
{
    public GameTexture texture;

    public CaveglowLamp()
    {
        super(new Rectangle(11, 11, 10, 10));
        this.mapColor = new Color(0, 200, 200);
        this.displayMapTooltip = true;
        this.lightLevel = 300;
        this.objectHealth = 1;
        this.stackSize = 500;
        this.toolType = ToolType.ALL;
        this.isLightTransparent = true;
        this.setItemCategory("objects", "lighting");
        this.setCraftingCategory("objects", "lighting");
        this.roomProperties.add("lights");
        this.canPlaceOnShore = true;
        this.replaceCategories.add("torch");
        this.canReplaceCategories.add("torch");
        this.canReplaceCategories.add("furniture");
        this.canReplaceCategories.add("column");
        this.replaceRotations = false;
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/" + this.getStringID());
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        boolean active = this.isActive(level, tileX, tileY);
        final TextureDrawOptions options = this.texture.initDraw().sprite(0, active ? 0 : 1, 32, 96).addObjectDamageOverlay(this, level, tileX, tileY).light(light).pos(drawX, drawY - this.texture.getHeight() / 2 + 32);
        list.add(new LevelSortedDrawable(this, tileX, tileY)
        {
            public int getSortY()
            {
                return 16;
            }
            public void draw(TickManager tickManager)
            {
                options.draw();
            }
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera)
    {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        this.texture.initDraw().sprite(0, 0, 32, this.texture.getHeight() / 2).alpha(alpha).draw(drawX, drawY - this.texture.getHeight() / 2 + 32);
    }

    public int getLightLevel(Level level, int layerID, int tileX, int tileY)
    {
        return this.isActive(level, tileX, tileY) ? this.lightLevel : 0;
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y)
    {
        return new CaveglowLampObjectEntity(level, x, y);
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "caveglowlamptip"));
        return tooltips;
    }

    public boolean isActive(Level level, int x, int y)
    {
        byte rotation = level.getObjectRotation(x, y);
        return this.getMultiTile(rotation).streamIDs(x, y).noneMatch((c) -> level.wireManager.isWireActiveAny(c.tileX, c.tileY));
    }

    public void onWireUpdate(Level level, int layerID, int tileX, int tileY, int wireID, boolean active)
    {
        byte rotation = level.getObjectRotation(tileX, tileY);
        Rectangle rect = this.getMultiTile(rotation).getTileRectangle(tileX, tileY);
        level.lightManager.updateStaticLight(rect.x, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 1, true);
    }
}