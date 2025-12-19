package summonerexpansion.codes.registry;

import necesse.engine.registries.LevelEventRegistry;
import summonerexpansion.codes.events.*;

public class SummonerEvents
{
    public static void registerSummonEvents()
    {
        // Explosions
        LevelEventRegistry.registerEvent("snowmanexplosionlevelevent", SnowmanExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("druidclawdashlevelevent", DruidClawDashLevelEvent.class);
        LevelEventRegistry.registerEvent("spiritghoulpoolevent", SpiritGhoulPoolEvent.class);
        LevelEventRegistry.registerEvent("snowmansetlevelevent", SnowmanSetLevelEvent.class);
        LevelEventRegistry.registerEvent("mosquitobowevent", MosquitoBowEvent.class);
        LevelEventRegistry.registerEvent("doomshroomevent", DoomShroomEvent.class);
    }
}