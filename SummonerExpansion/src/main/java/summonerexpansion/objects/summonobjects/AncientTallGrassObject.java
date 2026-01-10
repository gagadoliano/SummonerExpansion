package summonerexpansion.objects.summonobjects;

import necesse.engine.gameLoop.tickManager.Performance;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.packet.PacketHitObject;
import necesse.engine.registries.ObjectLayerRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.GrassObject;
import necesse.level.gameObject.GrassSpreadOptions;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.level.maps.regionSystem.SimulatePriorityList;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class AncientTallGrassObject extends GrassObject
{
    public static double spreadChance = GameMath.getAverageSuccessRuns(300.0F);

    public AncientTallGrassObject()
    {
        super("ancienttallgrass", -1);
        canPlaceOnShore = true;
        mapColor = new Color(47, 73, 44);
        displayMapTooltip = true;
        weaveAmount = 0.05F;
        extraWeaveSpace = 32;
        randomYOffset = 3.0F;
        randomXOffset = 10.0F;
    }

    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "ancienttallgrass");
    }

    public LootTable getLootTable(Level level, int layerID, int tileX, int tileY)
    {
        if (level.objectLayer.isPlayerPlaced(tileX, tileY))
        {
            return super.getLootTable(level, layerID, tileX, tileY);
        }
        else
        {
            float baitChance = 50.0F;
            if (level.weatherLayer.isRaining())
            {
                baitChance = 40.0F;
            }
            if (level.isCave)
            {
                baitChance = 20.0F;
            }
            return new LootTable(new ChanceLootItem(1.0F / baitChance, "swamplarva"));
        }
    }

    public boolean canPlaceOn(Level level, int layerID, int x, int y, GameObject other)
    {
        return other.getID() == 0 || !other.getValidObjectLayers().contains(ObjectLayerRegistry.TILE_LAYER);
    }

    public String canPlace(Level level, int layerID, int x, int y, int rotation, boolean byPlayer, boolean ignoreOtherLayers)
    {
        String error = super.canPlace(level, layerID, x, y, rotation, byPlayer, ignoreOtherLayers);
        if (error != null)
        {
            return error;
        }
        else if (level.getObjectID(ObjectLayerRegistry.TILE_LAYER, x, y) != 0)
        {
            return "occupied";
        }
        else if (byPlayer && level.getTile(x, y).isOrganic)
        {
            return null;
        }
        else
        {
            return level.getTileID(x, y) != TileRegistry.getTileID("ancientgrasstile") ? "notswamprock" : null;
        }
    }

    public boolean isValid(Level level, int layerID, int x, int y)
    {
        if (!super.isValid(level, layerID, x, y))
        {
            return false;
        }
        else if (level.getObjectID(ObjectLayerRegistry.TILE_LAYER, x, y) != 0)
        {
            return false;
        }
        else if (level.objectLayer.isPlayerPlaced(layerID, x, y) && level.getTile(x, y).isOrganic)
        {
            return true;
        }
        else
        {
            return level.getTileID(x, y) == TileRegistry.getTileID("ancientgrasstile");
        }
    }

    public int getLightLevelMod(Level level, int x, int y) {
        return 30;
    }

    public GrassSpreadOptions getSpreadOptions(Level level)
    {
        return GrassSpreadOptions.init(this, level).maxSpread(2, 8, 1);
    }

    public void tick(Level level, int x, int y)
    {
        super.tick(level, x, y);
        if (level.isServer() && GameRandom.globalRandom.getChance(spreadChance))
        {
            getSpreadOptions(level).tickSpread(x, y, true);
        }
    }

    public void addSimulateLogic(Level level, int x, int y, long ticks, SimulatePriorityList list, boolean sendChanges)
    {
        super.addSimulateLogic(level, x, y, ticks, list, sendChanges);
        getSpreadOptions(level).addSimulateSpread(x, y, spreadChance, ticks, list, sendChanges);
    }

    public void attackThrough(Level level, int x, int y, GameDamage damage, Attacker attacker)
    {
        level.getServer().network.sendToClientsWithTile(new PacketHitObject(level, x, y, this, damage), level, x, y);
    }

    public void attackThrough(Level level, int x, int y, GameDamage damage)
    {
        level.makeGrassWeave(x, y, weaveTime, false);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        Performance.record(tickManager, "thornsSetup", () ->
        {
            Integer[] adj = level.getAdjacentObjectsInt(tileX, tileY);
            int objs = 0;
            for(Integer id : adj)
            {
                if (id == getID())
                {
                    ++objs;
                }
            }
            int minTextureIndex;
            int maxTextureIndex;
            if (objs < 4)
            {
                minTextureIndex = 4;
                maxTextureIndex = 7;
            }
            else
            {
                minTextureIndex = 0;
                maxTextureIndex = 3;
            }
            int drawX = camera.getTileDrawX(tileX);
            int drawY = camera.getTileDrawY(tileY);
            GameLight light = level.getLightLevel(tileX, tileY);
            addGrassDrawable(list, tileList, level, tileX, tileY, drawX, drawY, light, 6, -5, 0, minTextureIndex, maxTextureIndex);
            addGrassDrawable(list, tileList, level, tileX, tileY, drawX, drawY, light, 12, -5, 1);
            addGrassDrawable(list, tileList, level, tileX, tileY, drawX, drawY, light, 26, -5, 2, minTextureIndex, maxTextureIndex);
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera)
    {
        Integer[] adj = level.getAdjacentObjectsInt(tileX, tileY);
        int objs = 0;
        for(Integer id : adj)
        {
            if (id == getID())
            {
                ++objs;
            }
        }
        int minTextureIndex;
        int maxTextureIndex;
        if (objs < 4)
        {
            minTextureIndex = 4;
            maxTextureIndex = 7;
        }
        else
        {
            minTextureIndex = 0;
            maxTextureIndex = 3;
        }
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        LinkedList<LevelSortedDrawable> list = new LinkedList<>();
        OrderableDrawables tileList = new OrderableDrawables(new TreeMap<>());
        addGrassDrawable(list, tileList, level, tileX, tileY, drawX, drawY, null, 6, -5, 0, minTextureIndex, maxTextureIndex, 0.5F);
        addGrassDrawable(list, tileList, level, tileX, tileY, drawX, drawY, null, 12, -5, 1, 0.5F);
        addGrassDrawable(list, tileList, level, tileX, tileY, drawX, drawY, null, 26, -5, 2, minTextureIndex, maxTextureIndex, 0.5F);
        tileList.forEach((d) -> d.draw(level.tickManager()));
        list.forEach((d) -> d.draw(level.tickManager()));
    }

    public boolean shouldSnapSmartMining(Level level, int x, int y) {
        return true;
    }
}