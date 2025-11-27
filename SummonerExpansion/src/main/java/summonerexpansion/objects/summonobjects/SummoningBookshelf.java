package summonerexpansion.objects.summonobjects;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import necesse.engine.Settings;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundSettings;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.recipe.Tech;
import necesse.level.gameObject.ObjectHoverHitbox;
import necesse.level.gameObject.container.CraftingStationObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import static summonerexpansion.codes.summonregistry.SummonerTechs.*;

public class SummoningBookshelf extends CraftingStationObject
{
    public GameTexture texture;

    public SummoningBookshelf()
    {
        super(new Rectangle(32, 32));
        hoverHitbox = new Rectangle(0, -32, 32, 64);
        mapColor = new Color(110, 83, 62);
        drawDamage = false;
        objectHealth = 50;
        isLightTransparent = true;
        toolType = ToolType.ALL;
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/summoningbookshelf");
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        byte rotation = level.getObjectRotation(tileX, tileY);
        float alpha = 1.0F;
        if (perspective != null && !Settings.hideUI && !Settings.hideCursor)
        {
            Rectangle alphaRec = new Rectangle(tileX * 32 - 16, tileY * 32 - 32 - 16, 64, 64);
            if (perspective.getCollision().intersects(alphaRec))
            {
                alpha = 0.5F;
            }
            else if (alphaRec.contains(camera.getMouseLevelPosX(), camera.getMouseLevelPosY()))
            {
                alpha = 0.5F;
            }
        }

        final TextureDrawOptions options = this.texture.initDraw().sprite(rotation % 4, 0, 32, this.texture.getHeight()).addObjectDamageOverlay(this, level, tileX, tileY).alpha(alpha).light(light).pos(drawX, drawY - this.texture.getHeight() + 64);
        list.add(new LevelSortedDrawable(this, tileX, tileY)
        {
            public int getSortY() {
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
        this.texture.initDraw().sprite(rotation % 4, 0, 32, this.texture.getHeight()).addObjectDamageOverlay(this, level, tileX, tileY).alpha(alpha).draw(drawX, drawY - this.texture.getHeight() + 64);
    }

    public Rectangle getCollision(Level level, int x, int y, int rotation)
    {
        if (rotation == 0)
        {
            return new Rectangle(x * 32, y * 32 + 22, 32, 10);
        }
        else if (rotation == 1)
        {
            return new Rectangle(x * 32, y * 32, 12, 32);
        }
        else
        {
            return rotation == 2 ? new Rectangle(x * 32, y * 32, 32, 10) : new Rectangle(x * 32 + 20, y * 32, 12, 32);
        }
    }

    public List<ObjectHoverHitbox> getHoverHitboxes(Level level, int layerID, int tileX, int tileY)
    {
        int rotation = level.getObjectRotation(layerID, tileX, tileY);
        LinkedList<ObjectHoverHitbox> list = new LinkedList<>();
        if (rotation == 0)
        {
            list.add(new ObjectHoverHitbox(layerID, tileX, tileY, 0, -24, 32, 56, 24));
        }
        else if (rotation == 1)
        {
            list.add(new ObjectHoverHitbox(layerID, tileX, tileY, 0, 0, 32, 32, 16));
            list.add(new ObjectHoverHitbox(layerID, tileX, tileY, 0, -44, 12, 44, 16));
        }
        else if (rotation == 2)
        {
            list.add(new ObjectHoverHitbox(layerID, tileX, tileY, 0, -46, 32, 78, 8));
        }
        else
        {
            list.add(new ObjectHoverHitbox(layerID, tileX, tileY, 0, 0, 32, 32, 16));
            list.add(new ObjectHoverHitbox(layerID, tileX, tileY, 20, -44, 12, 44, 16));
        }
        return list;
    }

    protected SoundSettings getInteractSoundOpen()
    {
        return (new SoundSettings(GameResources.bookShuffle)).volume(0.25F);
    }

    public Tech[] getCraftingTechs()
    {
        return new Tech[]{SUMMONBOOKCRAFT};
    }
}