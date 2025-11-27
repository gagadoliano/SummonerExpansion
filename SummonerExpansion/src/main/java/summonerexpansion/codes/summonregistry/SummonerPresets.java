package summonerexpansion.codes.summonregistry;

import necesse.engine.registries.WorldPresetRegistry;
import summonerexpansion.summonpreset.*;

public class SummonerPresets
{
    public static void registerSummonPresets()
    {
        WorldPresetRegistry.registerPreset("druidswamphouse", new DruidSwampHouseWorldPreset());
        WorldPresetRegistry.registerPreset("trainingplain", new TrainingPlainWorldPreset());
        WorldPresetRegistry.registerPreset("christmasshrine", new ChristmasShrineWorldPreset());
        WorldPresetRegistry.registerPreset("horrorsmallarena", new HorrorSmallArenaWorldPreset());
    }
}