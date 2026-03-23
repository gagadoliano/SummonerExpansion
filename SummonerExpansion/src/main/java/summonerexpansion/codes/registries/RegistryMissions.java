package summonerexpansion.codes.registries;

import necesse.engine.expeditions.*;
import necesse.engine.registries.ExpeditionMissionRegistry;

public class RegistryMissions
{
    public static void registerMissions()
    {
        ExpeditionMissionRegistry.registerMiningTrip("titaniumminingtrip", new MiningTripExpedition("swampguardian", 400, 100, 400, "swampstone", new MiningTripExpedition.OreConfig("titaniumore", 400), new MiningTripExpedition.OreConfig("sapphire", 0.05F, 200)));
        ExpeditionMissionRegistry.registerFishingTrip("sunkenchesttrip", new TypesFishingTripExpedition("swampguardian", 400, 100, 400, "sunkenchest", "swampfish"));
    }
}