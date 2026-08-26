package summonerexpansion.objects.decoration;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.ObjectRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.HappinessObject;
import necesse.level.gameObject.StaticMultiObject;
import necesse.level.gameObject.furniture.RoomFurniture;
import necesse.level.maps.Level;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

public class SwampShooterDollObject extends StaticMultiObject implements RoomFurniture, HappinessObject
{
    protected SwampShooterDollObject(String texturePath, int multiX, int multiY, int multiWidth, int multiHeight, int[] multiIDs, Rectangle fullCollision)
    {
        super(multiX, multiY, multiWidth, multiHeight, multiIDs, fullCollision, texturePath);
        stackSize = 1;
        rarity = Item.Rarity.EPIC;
        mapColor = new Color(42, 97, 37);
        objectHealth = 400;
        toolType = ToolType.ALL;
        isLightTransparent = true;
        hoverHitbox = new Rectangle(0, 0, 32, 32);
        setItemCategory("objects", "furniture");
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        int anim = (int)(level.getWorldEntity().getTime() / 400L % 4L);
        GameSprite sprite = new GameSprite(texture, anim, 0, 64, 96, 64, 96);
        final DrawOptions options = getMultiTextureDrawOptions(sprite, level, tileX, tileY, camera);
        list.add(new LevelSortedDrawable(this, tileX, tileY)
        {
            public int getSortY() {
                return 16;
            }
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera)
    {
        GameSprite sprite = new GameSprite(texture, 0, 0, 64, 96, 64, 96);
        drawMultiTexturePreview(sprite, tileX, tileY, alpha, camera);
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/decorations/" + this.texturePath);
    }

    public static int[] registerSwampShooterDoll(String texturePath)
    {
        int[] ids = new int[4];
        Rectangle collision = new Rectangle(4, 8, 56, 48);
        ids[0] = ObjectRegistry.registerObject(texturePath, new SwampShooterDollObject(texturePath, 0, 0, 2, 2, ids, collision), 100F, true, false);
        ids[1] = ObjectRegistry.registerObject(texturePath + "2", new SwampShooterDollObject(texturePath, 1, 0, 2, 2, ids, collision), 0F, false);
        ids[2] = ObjectRegistry.registerObject(texturePath + "3", new SwampShooterDollObject(texturePath, 0, 1, 2, 2, ids, collision), 0F, false);
        ids[3] = ObjectRegistry.registerObject(texturePath + "4", new SwampShooterDollObject(texturePath, 1, 1, 2, 2, ids, collision), 0F, false);
        return ids;
    }

    public String getFurnitureType() {
        return null;
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(getHappinessObjectTooltip());
        return tooltips;
    }
}