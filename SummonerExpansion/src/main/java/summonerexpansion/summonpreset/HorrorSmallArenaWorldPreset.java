package summonerexpansion.summonpreset;

import necesse.engine.gameLoop.tickManager.PerformanceTimerManager;
import necesse.engine.registries.BiomeRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.biomeGenerator.BiomeGeneratorStack;
import necesse.engine.world.worldPresets.LevelPresetsRegion;
import necesse.engine.world.worldPresets.WorldPreset;
import necesse.level.maps.presets.PresetUtils;

import java.awt.*;

public class HorrorSmallArenaWorldPreset extends WorldPreset
{
    protected Dimension size = new Dimension(14, 14);

    public boolean shouldAddToRegion(LevelPresetsRegion presetsRegion)
    {
        return (presetsRegion.identifier.equals(LevelIdentifier.DEEP_CAVE_IDENTIFIER) && presetsRegion.hasAnyOfBiome(BiomeRegistry.FOREST.getID()));
    }

    public void addToRegion(GameRandom random, LevelPresetsRegion presetsRegion, final BiomeGeneratorStack generatorStack, PerformanceTimerManager performanceTimer)
    {
        int total = getTotalBiomePoints(random, presetsRegion, BiomeRegistry.FOREST, 0.001F);
        for (int i = 0; i < total; i++)
        {
            final Point tile = findRandomBiomePresetTile(random, presetsRegion, generatorStack, BiomeRegistry.FOREST, 20, size, new String[]{"loot", "villages"}, (tileX, tileY) -> (!generatorStack.isCaveRiverOrLava(tileX, tileY) && generatorStack.getLazyBiomeID(tileX, tileY) == BiomeRegistry.FOREST.getID()));
            if (tile != null)
            {
                presetsRegion.addPreset(this, tile.x, tile.y, size, new String[]{"loot", "villages"},
                        (random1, level, timer) ->
                        {
                            WorldPreset.ensureRegionsAreGenerated(level, tile.x, tile.y, size.width, size.height);
                            HorrorSmallArenaPreset preset = new HorrorSmallArenaPreset(random1);
                            PresetUtils.clearMobsInPreset(preset, level, tile.x, tile.y);
                            preset.applyToLevel(level, tile.x, tile.y);
                        }).setRemoveIfWithinSpawnRegionRange(1);
            }
        }
    }
}