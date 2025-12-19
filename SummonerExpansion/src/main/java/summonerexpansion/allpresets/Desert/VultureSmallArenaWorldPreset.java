package summonerexpansion.allpresets.Desert;

import necesse.engine.gameLoop.tickManager.PerformanceTimerManager;
import necesse.engine.registries.BiomeRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.biomeGenerator.BiomeGeneratorStack;
import necesse.engine.world.worldPresets.LevelPresetsRegion;
import necesse.engine.world.worldPresets.WorldPreset;
import necesse.level.maps.presets.PresetUtils;

import java.awt.*;

public class VultureSmallArenaWorldPreset extends WorldPreset
{
    protected Dimension size = new Dimension(24, 23);

    public boolean shouldAddToRegion(LevelPresetsRegion presetsRegion)
    {
        return (presetsRegion.identifier.equals(LevelIdentifier.SURFACE_IDENTIFIER) && presetsRegion.hasAnyOfBiome(BiomeRegistry.DESERT.getID()));
    }

    public void addToRegion(GameRandom random, LevelPresetsRegion presetsRegion, final BiomeGeneratorStack generatorStack, PerformanceTimerManager performanceTimer)
    {
        int total = getTotalBiomePoints(random, presetsRegion, BiomeRegistry.DESERT, 0.001F);
        for (int i = 0; i < total; i++)
        {
            final Point tile = findRandomBiomePresetTile(random, presetsRegion, generatorStack, BiomeRegistry.DESERT, 20, size, new String[] { "loot" }, (tileX, tileY) -> (!generatorStack.isSurfaceExpensiveWater(tileX, tileY) && generatorStack.getLazyBiomeID(tileX, tileY) == BiomeRegistry.DESERT.getID()));
            if (tile != null)
            {
                presetsRegion.addPreset(this, tile.x, tile.y, size, new String[]{"loot"},
                        (random1, level, timer) ->
                        {
                            WorldPreset.ensureRegionsAreGenerated(level, tile.x, tile.y, size.width, size.height);
                            VultureSmallArenaPreset preset = new VultureSmallArenaPreset();
                            PresetUtils.clearMobsInPreset(preset, level, tile.x, tile.y);
                            preset.applyToLevel(level, tile.x, tile.y);
                        }).setRemoveIfWithinSpawnRegionRange(1);
            }
        }
    }
}