package summonerexpansion.codes.summonregistry;

import necesse.engine.registries.LevelEventRegistry;
import summonerexpansion.codes.summonevents.*;

public class SummonerEvents
{
    public static void registerSummonEvents()
    {
        // Explosions
        LevelEventRegistry.registerEvent("snowmanexplosionlevelevent", SnowmanExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("snowmansetlevelevent", SnowmanSetLevelEvent.class);
        LevelEventRegistry.registerEvent("mosquitobowevent", MosquitoBowEvent.class);
        LevelEventRegistry.registerEvent("doomshroomevent", DoomShroomEvent.class);
        LevelEventRegistry.registerEvent("spiritghoulpoolevent", SpiritGhoulPoolEvent.class);
    }
}