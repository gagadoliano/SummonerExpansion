package summonerexpansion.codes.registries;

import necesse.engine.registries.MobRegistry;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.dungeon.DungeonBiome;
import necesse.level.maps.biomes.swamp.SwampBiome;

public class RegistrySpawn
{
    public static void registerSpawn()
    {
        //Forest
        Biome.defaultCaveMobs
                .add(1, "vampireminibossmob");

        Biome.defaultDeepCaveMobs
                .add(10, "horrorspiritmob");

        Biome.defaultDeepCaveMobs
                .add(10, (level, client, spawnTile) -> MobRegistry.getMob("lavacavesharkmob", level));

        //Swamp
        SwampBiome.defaultCaveMobs
                .add(1, "poisonswampslimemob");

        SwampBiome.deepSwampCaveMobs
                .add(1, "poisonswampslimewormmob");

        //Dungeon
        DungeonBiome.defaultDungeonMobs
                .add(1, "voidswampzombiemob");
    }
}