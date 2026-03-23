package summonerexpansion.codes.registries;

import necesse.level.maps.biomes.forest.ForestBiome;
import necesse.level.maps.biomes.incursions.ForestDeepCaveBiome;
import necesse.level.maps.biomes.plains.PlainsBiome;
import necesse.level.maps.biomes.swamp.SwampBiome;

public class RegistryFishLoot
{
    public static void registerLoot()
    {
        //Forest
        ForestBiome.defaultSurfaceFish.addSaltWater(2, "timeworncoins");
        ForestBiome.defaultSurfaceFish.addSaltWater(5, "inspectorclouseau");
        ForestBiome.defaultSurfaceFish.addSaltWater(5, "tinyorca");

        //Cave
        ForestBiome.defaultCaveFish.addWater(2, "waterzombie");
    }
}