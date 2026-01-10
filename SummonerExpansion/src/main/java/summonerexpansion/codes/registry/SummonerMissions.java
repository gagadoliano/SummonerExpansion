package summonerexpansion.codes.registry;

import necesse.engine.expeditions.MiningTripExpedition;
import necesse.engine.expeditions.TypesFishingTripExpedition;
import necesse.engine.registries.ExpeditionMissionRegistry;

public class SummonerMissions
{
    public static void registerSummonMissions()
    {
        ExpeditionMissionRegistry.registerMiningTrip("titaniumminingtrip", new MiningTripExpedition("swampguardian", 400, 100, 400, "swampstone", new MiningTripExpedition.OreConfig("titaniumore", 400), new MiningTripExpedition.OreConfig("sapphire", 0.05F, 200)));
        ExpeditionMissionRegistry.registerFishingTrip("sunkenchesttrip", new TypesFishingTripExpedition("swampguardian", 400, 100, 400, "sunkenchest", "swampfish"));
    }
}