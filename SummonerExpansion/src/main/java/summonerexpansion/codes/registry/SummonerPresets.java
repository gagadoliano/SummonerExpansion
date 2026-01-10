package summonerexpansion.codes.registry;

import necesse.engine.registries.WorldPresetRegistry;
import summonerexpansion.allpresets.Desert.*;
import summonerexpansion.allpresets.Forest.*;
import summonerexpansion.allpresets.MiniBiomes.AncientForestMiniBiomeWorldPreset;
import summonerexpansion.allpresets.Snow.*;
import summonerexpansion.allpresets.Swamp.*;
import summonerexpansion.allpresets.Plains.*;

public class SummonerPresets
{
    public static void registerSummonPresets()
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


        // Cave

        // Forest
        WorldPresetRegistry.registerPreset("cavebeetgarden", new CaveForestBeetGardenWorldPreset());
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