package summonerexpansion.codes.registries;

import necesse.engine.expeditions.*;
import necesse.engine.registries.ExpeditionMissionRegistry;

public class RegistryMissions
{
    public static void registerMissions()
    {
        ExpeditionMissionRegistry.registerMiningTrip("titaniumminingtrip", new MiningTripExpedition("defeatswampguardian",false, 400, 100, 400, "swampstone", new MiningTripExpedition.OreConfig("titaniumore", 400), new MiningTripExpedition.OreConfig("ivyore", 0.05F, 200)));
        ExpeditionMissionRegistry.registerFishingTrip("sunkenchesttrip", new TypesFishingTripExpedition("defeatswampguardian", 400, 100, 400, "sunkenchest", "swampfish"));
    }
}