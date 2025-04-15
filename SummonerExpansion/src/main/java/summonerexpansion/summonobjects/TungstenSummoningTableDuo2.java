package summonerexpansion.summonobjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptionsList;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.*;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.level.maps.multiTile.MultiTile;
import necesse.level.maps.multiTile.SideMultiTile;

import java.awt.*;
import java.util.List;

public class TungstenSummoningTableDuo2 extends CraftingStationObject
{
    public ObjectDamagedTextureArray texture;
    protected int counterID;

    public TungstenSummoningTableDuo2()
    {
        super(new Rectangle(32, 32));
        mapColor = new Color(97, 95, 132);
        toolType = ToolType.ALL;
        isLightTransparent = true;
        hoverHitbox = new Rectangle(0, -16, 32, 48);
    }

    public MultiTile getMultiTile(int rotation)
    {
        return new SideMultiTile(0, 0, 1, 2, rotation, false, this.getID(), this.counterID);
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/tungstensummoningtableduo");
    }

    public Rectangle getCollision(Level level, int x, int y, int rotation)
    {
        if (rotation == 0)
        {
            return new Rectangle(x * 32 + 4, y * 32 + 4, 24, 28);
        }
        else if (rotation == 1)
        {
            return new Rectangle(x * 32, y * 32 + 6, 26, 20);
        }
        else
        {
            return rotation == 2 ? new Rectangle(x * 32 + 4, y * 32, 24, 26) : new Rectangle(x * 32 + 6, y * 32 + 6, 26, 20);
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        int rotation = level.getObjectRotation(tileX, tileY);
        GameTexture texture = this.texture.getDamagedTexture(this, level, tileX, tileY);
        final DrawOptionsList options = new DrawOptionsList();
        if (rotation == 0) {
            options.add(texture.initDraw().sprite(0, 0, 32).light(light).pos(drawX, drawY - 32));
            options.add(texture.initDraw().sprite(0, 1, 32).light(light).pos(drawX, drawY));
        } else if (rotation == 1) {
            options.add(texture.initDraw().sprite(1, 5, 32).light(light).pos(drawX, drawY - 32));
            options.add(texture.initDraw().sprite(1, 6, 32).light(light).pos(drawX, drawY));
            int flameSprite = (int) (level.getWorldEntity().getWorldTime() % 1200L / 300L);
            options.add(texture.initDraw().sprite(flameSprite % 2, 7 + flameSprite / 2, 32).light(light).pos(drawX, drawY));
        } else if (rotation == 2) {
            options.add(texture.initDraw().sprite(1, 2, 32).light(light).pos(drawX, drawY));
        } else {
            options.add(texture.initDraw().sprite(0, 3, 32).light(light).pos(drawX, drawY - 32));
            options.add(texture.initDraw().sprite(0, 4, 32).light(light).pos(drawX, drawY));
        }

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
}