package summonerexpansion.objects.summontiles;

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

public class EmpoweredNewSapphire extends TerrainSplatterTile
{
    private final GameRandom drawRandom;

    public EmpoweredNewSapphire()
    {
        super(false, "empowerednewsapphire");
        mapColor = new Color(57, 137, 184);
        canBeMined = true;
        drawRandom = new GameRandom();
        lightLevel = 50;
        tileHealth = 500;
        lightHue = 199F;
    }

    public void tick(Mob mob, Level level, int x, int y)
    {
        if (mob.isPlayer)
        {
            mob.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("sapphirefloorbuff"), mob, 0.5F, mob), true);
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
            return new ModifierValue(BuffModifiers.SPEED, 0.50F);
        }
        else
        {
            return super.getSpeedModifier(mob);
        }
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "empoweredsapphiretip"));
        return tooltips;
    }
}