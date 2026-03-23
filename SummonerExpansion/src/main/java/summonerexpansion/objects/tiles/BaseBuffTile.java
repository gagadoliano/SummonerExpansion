package summonerexpansion.objects.tiles;

import necesse.engine.localization.Localization;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;

import java.awt.*;

public class BaseBuffTile extends TerrainSplatterTile
{
    private final GameRandom drawRandom;
    private String tileBuff;
    private String tileTooltip;
    private Float tileSpeed;

    public BaseBuffTile(Boolean isFloor, Float tileSpeed, Color tileColor, Float lightColor, String texture, String tileBuff, String tileTooltip)
    {
        super(isFloor, texture);
        mapColor = tileColor;
        canBeMined = true;
        drawRandom = new GameRandom();
        lightLevel = 50;
        tileHealth = 500;
        lightHue = lightColor;
        this.tileBuff = tileBuff;
        this.tileSpeed = tileSpeed;
        this.tileTooltip = tileTooltip;
    }

    public void tick(Mob mob, Level level, int x, int y)
    {
        if (mob.isPlayer)
        {
            mob.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff(tileBuff), mob, 0.2F, mob), true);
        }
    }

    public Point getTerrainSprite(GameTextureSection terrainTexture, Level level, int tileX, int tileY)
    {
        int tile;
        synchronized(drawRandom)
        {
            tile = drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
        }
        return new Point(0, tile);
    }

    public int getTerrainPriority() {
        return 0;
    }

    public ModifierValue<Float> getSpeedModifier(Mob mob)
    {
        if (mob.isPlayer || mob.isHuman)
        {
            return new ModifierValue<>(BuffModifiers.SPEED, tileSpeed);
        }
        else
        {
            return super.getSpeedModifier(mob);
        }
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", tileTooltip));
        return tooltips;
    }
}