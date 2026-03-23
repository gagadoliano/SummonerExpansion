package summonerexpansion.codes.registries;

import necesse.engine.registries.MobRegistry;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.swamp.SwampBiome;

public class RegistrySpawn
{
    public static void registerSpawn()
    {
        Biome.defaultDeepCaveMobs
                .add(10, "horrorspiritmob");

        SwampBiome.surfaceMobs
                .addLimited(1, "woodmob", 1, 50*50);

        Biome.defaultDeepCaveMobs
                .add(10, (level, client, spawnTile) -> MobRegistry.getMob("lavacavesharkmob", level));
    }
}