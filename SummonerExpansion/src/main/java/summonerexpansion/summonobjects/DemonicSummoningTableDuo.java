package summonerexpansion.summonobjects;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.sound.SoundSettings;
import necesse.engine.sound.SoundSettingsRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptionsList;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Tech;
import necesse.level.gameObject.ObjectDamagedTextureArray;
import necesse.level.gameObject.ObjectPlaceOption;
import necesse.level.gameObject.container.CraftingStationObject;
import necesse.level.gameObject.container.CraftingStationUpgrade;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.level.maps.multiTile.MultiTile;
import necesse.level.maps.multiTile.SideMultiTile;

import static summonerexpansion.summonothers.SummonerTechs.SUMMONTABLECRAFT;
import static summonerexpansion.summonothers.SummonerTechs.SUMMONTABLECRAFT2;

public class DemonicSummoningTableDuo extends CraftingStationObject
{
    public ObjectDamagedTextureArray texture;
    protected int counterID;

    public DemonicSummoningTableDuo()
    {
        super(new Rectangle(32, 32));
        mapColor = new Color(30, 30, 30);
        toolType = ToolType.ALL;
        isLightTransparent = true;
        hoverHitbox = new Rectangle(0, -16, 32, 48);
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/demonicsummoningtableduo");
    }

    public MultiTile getMultiTile(int rotation)
    {
        return new SideMultiTile(0, 1, 1, 2, rotation, true, this.counterID, this.getID());
    }

    public ArrayList<ObjectPlaceOption> getPlaceOptions(Level level, int levelX, int levelY, PlayerMob playerMob, int playerDir, boolean offsetMultiTile)
    {
        return super.getPlaceOptions(level, levelX, levelY, playerMob, Math.floorMod(playerDir - 1, 4), offsetMultiTile);
    }

    public CraftingStationUpgrade getStationUpgrade()
    {
        return new CraftingStationUpgrade(ObjectRegistry.getObject("tungstensummoningtableduo"), new Ingredient("tungstenbar", 5));
    }

    public Rectangle getCollision(Level level, int x, int y, int rotation)
    {
        if (rotation == 0)
        {
            return new Rectangle(x * 32 + 4, y * 32, 24, 26);
        }
        else if (rotation == 1)
        {
            return new Rectangle(x * 32 + 6, y * 32 + 6, 26, 20);
        }
        else
        {
            return rotation == 2 ? new Rectangle(x * 32 + 4, y * 32 + 4, 24, 28) : new Rectangle(x * 32, y * 32 + 6, 26, 20);
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        int rotation = level.getObjectRotation(tileX, tileY);
        GameTexture texture = this.texture.getDamagedTexture(this, level, tileX, tileY);
        final DrawOptionsList options = new DrawOptionsList();
        if (rotation == 0)
        {
            options.add(texture.initDraw().sprite(0, 2, 32).light(light).pos(drawX, drawY));
        }
        else if (rotation == 1)
        {
            options.add(texture.initDraw().sprite(0, 5, 32).light(light).pos(drawX, drawY - 32));
            options.add(texture.initDraw().sprite(0, 6, 32).light(light).pos(drawX, drawY));
        }
        else if (rotation == 2)
        {
            options.add(texture.initDraw().sprite(1, 0, 32).light(light).pos(drawX, drawY - 32));
            options.add(texture.initDraw().sprite(1, 1, 32).light(light).pos(drawX, drawY));
        }
        else
        {
            options.add(texture.initDraw().sprite(1, 3, 32).light(light).pos(drawX, drawY - 32));
            options.add(texture.initDraw().sprite(1, 4, 32).light(light).pos(drawX, drawY));
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

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera)
    {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        GameTexture texture = this.texture.getDamagedTexture(0.0F);
        if (rotation == 0)
        {
            texture.initDraw().sprite(0, 2, 32).alpha(alpha).draw(drawX, drawY);
            texture.initDraw().sprite(0, 0, 32).alpha(alpha).draw(drawX, drawY - 64);
            texture.initDraw().sprite(0, 1, 32).alpha(alpha).draw(drawX, drawY - 32);
        }
        else if (rotation == 1)
        {
            texture.initDraw().sprite(0, 5, 32).alpha(alpha).draw(drawX, drawY - 32);
            texture.initDraw().sprite(0, 6, 32).alpha(alpha).draw(drawX, drawY);
            texture.initDraw().sprite(1, 5, 32).alpha(alpha).draw(drawX + 32, drawY - 32);
            texture.initDraw().sprite(1, 6, 32).alpha(alpha).draw(drawX + 32, drawY);
        }
        else if (rotation == 2)
        {
            texture.initDraw().sprite(1, 0, 32).alpha(alpha).draw(drawX, drawY - 32);
            texture.initDraw().sprite(1, 1, 32).alpha(alpha).draw(drawX, drawY);
            texture.initDraw().sprite(1, 2, 32).alpha(alpha).draw(drawX, drawY + 32);
        }
        else
        {
            texture.initDraw().sprite(1, 3, 32).alpha(alpha).draw(drawX, drawY - 32);
            texture.initDraw().sprite(1, 4, 32).alpha(alpha).draw(drawX, drawY);
            texture.initDraw().sprite(0, 3, 32).alpha(alpha).draw(drawX - 32, drawY - 32);
            texture.initDraw().sprite(0, 4, 32).alpha(alpha).draw(drawX - 32, drawY);
        }
    }

    public Tech[] getCraftingTechs()
    {
        return new Tech[]{SUMMONTABLECRAFT2, SUMMONTABLECRAFT};
    }

    public int getCraftingCategoryDepth() {return 3;}

    public static int[] registerDemonicSummoningTable()
    {
        DemonicSummoningTableDuo o1 = new DemonicSummoningTableDuo();
        DemonicSummoningTableDuo2 o2 = new DemonicSummoningTableDuo2();
        int i1 = ObjectRegistry.registerObject("demonicsummoningtableduo", o1, 50.0F, true);
        int i2 = ObjectRegistry.registerObject("demonicsummoningtableduo2", o2, 0.0F, false);
        o1.counterID = i2;
        o2.counterID = i1;
        return new int[]{i1, i2};
    }

    protected SoundSettings getInteractSoundOpen() {
        return SoundSettingsRegistry.workstationOpen;
    }
}