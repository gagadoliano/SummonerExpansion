package summonerexpansion.codes.registries;

import necesse.engine.registries.WorldPresetRegistry;
import summonerexpansion.biomes.presets.Desert.*;
import summonerexpansion.biomes.presets.Forest.*;
import summonerexpansion.biomes.presets.MiniBiomes.*;
import summonerexpansion.biomes.presets.Plains.*;
import summonerexpansion.biomes.presets.Snow.*;
import summonerexpansion.biomes.presets.Swamp.*;

public class RegistryPresets
{
    public static void registerPresets()
    {
        // Surface

        // Forest
        // Swamp
        WorldPresetRegistry.registerPreset("ancientforestminibiome", new AncientForestMiniBiomeWorldPreset());
        WorldPresetRegistry.registerPreset("druidswamphousealtar", new DruidSwampHouseAltarWorldPreset());
        WorldPresetRegistry.registerPreset("druidswamphousecave", new DruidSwampHouseCaveWorldPreset());
        WorldPresetRegistry.registerPreset("druidswamphouseloot", new DruidSwampHouseLootWorldPreset());
        WorldPresetRegistry.registerPreset("druidswamphouseside", new DruidSwampHouseSideWorldPreset());
        WorldPresetRegistry.registerPreset("druidswamphouseduo", new DruidSwampHouseDuoWorldPreset());
        WorldPresetRegistry.registerPreset("druidswamphouse", new DruidSwampHouseWorldPreset());
        // Snow
        WorldPresetRegistry.registerPreset("christmasshrine", new ChristmasShrineWorldPreset());
        // Plains
        WorldPresetRegistry.registerPreset("trainingplain", new TrainingPlainWorldPreset());
        WorldPresetRegistry.registerPreset("beehivearea", new BeehiveAreaWorldPreset());
        // Desert
        WorldPresetRegistry.registerPreset("vulturesmallarena", new VultureSmallArenaWorldPreset());
        WorldPresetRegistry.registerPreset("oceanmonkshrine", new OceanMonkShrineWorldPreset());
        WorldPresetRegistry.registerPreset("sandlibrary", new SandLibraryWorldPreset());
        WorldPresetRegistry.registerPreset("sandtemple", new SandTempleWorldPreset());

        // Cave

        // Forest
        WorldPresetRegistry.registerPreset("cavebeetgarden", new CaveForestBeetGardenWorldPreset());
        WorldPresetRegistry.registerPreset("lavasharklake", new LavaSharkLakeWorldPreset());
        // Swamp
        WorldPresetRegistry.registerPreset("cavemushroomshrine", new CaveSwampMushroomShrineWorldPreset());
        WorldPresetRegistry.registerPreset("titaniumorenode", new CaveSwampTitaniumNodeWorldPreset());
        // Snow
        // Plains
        // Desert

        // Deep Cave

        // Forest
        WorldPresetRegistry.registerPreset("horrorsmallarena", new HorrorSmallArenaWorldPreset());
        // Swamp
        // Snow
        // Plains
        // Desert
    }
}