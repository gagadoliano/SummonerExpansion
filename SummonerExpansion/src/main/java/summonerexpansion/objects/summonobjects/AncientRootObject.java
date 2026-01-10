package summonerexpansion.objects.summonobjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.StaticMultiObject;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObject;

import java.awt.*;
import java.util.List;

public class AncientRootObject extends StaticMultiObject
{
    private final GameRandom drawRandom;

    protected AncientRootObject(int multiX, int multiY, int multiWidth, int multiHeight, int[] multiIDs, Rectangle fullCollision)
    {
        super(multiX, multiY, multiWidth, multiHeight, multiIDs, fullCollision, "ancientwoodroot");
        this.mapColor = new Color(73, 28, 45);
        this.objectHealth = 150;
        this.toolType = ToolType.AXE;
        this.displayMapTooltip = true;
        this.isLightTransparent = true;
        this.drawRandom = new GameRandom();
        this.setItemCategory("objects", "landscaping", "plants");
    }

    public LootTable getLootTable(Level level, int layerID, int tileX, int tileY)
    {
        return level.objectLayer.isPlayerPlaced(tileX, tileY) ? super.getLootTable(level, layerID, tileX, tileY) : new LootTable(LootItem.between("ancientlog", 1, 2).splitItems(2));
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        LevelObject masterObject = this.getMultiTile(level.getObjectRotation(tileX, tileY)).getMasterLevelObject(level, 0, tileX, tileY).orElse(null);
        long tileSeed = masterObject == null ? getTileSeed(tileX, tileY) : getTileSeed(masterObject.tileX, masterObject.tileY);
        int spriteX;
        synchronized(this.drawRandom)
        {
            spriteX = this.drawRandom.seeded(tileSeed).nextInt(4);
        }
        GameSprite sprite = new GameSprite(this.texture, spriteX, 0, 64);
        DrawOptions options = this.getMultiTextureDrawOptions(sprite, level, tileX, tileY, camera);
        tileList.add((tm) -> options.draw());
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera)
    {
        Point masterTile = this.getMultiTile(level.getObjectRotation(tileX, tileY)).getMasterTilePos(tileX, tileY).orElse(null);
        long tileSeed = masterTile == null ? getTileSeed(tileX, tileY) : getTileSeed(masterTile.x, masterTile.y);
        int spriteX;
        synchronized(this.drawRandom)
        {
            spriteX = this.drawRandom.seeded(tileSeed).nextInt(4);
        }
        GameSprite sprite = new GameSprite(this.texture, spriteX, 0, 64);
        this.drawMultiTexturePreview(sprite, tileX, tileY, alpha, camera);
    }

    public static int[] registerAncientRoot()
    {
        int[] ids = new int[4];
        Rectangle collision = new Rectangle(0, 0, 0, 0);
        ids[0] = ObjectRegistry.registerObject("ancientwoodroot", new AncientRootObject(0, 0, 2, 2, ids, collision), 0.0F, true);
        ids[1] = ObjectRegistry.registerObject("ancientwoodroot2", new AncientRootObject(1, 0, 2, 2, ids, collision), 0.0F, false);
        ids[2] = ObjectRegistry.registerObject("ancientwoodroot3", new AncientRootObject(0, 1, 2, 2, ids, collision), 0.0F, false);
        ids[3] = ObjectRegistry.registerObject("ancientwoodroot4", new AncientRootObject(1, 1, 2, 2, ids, collision), 0.0F, false);
        return ids;
    }
}