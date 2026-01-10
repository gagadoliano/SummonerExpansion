package summonerexpansion.codes.registry;

import necesse.engine.registries.LevelEventRegistry;
import summonerexpansion.codes.events.*;

public class SummonerEvents
{
    public static void registerSummonEvents()
    {
        // Explosions
        LevelEventRegistry.registerEvent("snowmanexplosionlevelevent", SnowmanExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("pumpkinexplosionevent", PumpkinExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("snowmansetlevelevent", SnowmanSetLevelEvent.class);
        LevelEventRegistry.registerEvent("cookpothealevent", CookpotHealEvent.class);
        LevelEventRegistry.registerEvent("doomshroomevent", DoomShroomEvent.class);

        // Pool
        LevelEventRegistry.registerEvent("spiritghoulpoolevent", SpiritGhoulPoolEvent.class);

        // Thunder
        LevelEventRegistry.registerEvent("titaniumlightninglevelevent", TitaniumLightningLevelEvent.class);

        // Weapon
        LevelEventRegistry.registerEvent("druidclawdashlevelevent", DruidClawDashLevelEvent.class);
        LevelEventRegistry.registerEvent("pinewoodstaffevent", PineWoodStaffEvent.class);
        LevelEventRegistry.registerEvent("mosquitobowevent", MosquitoBowEvent.class);

        // Glyph
        LevelEventRegistry.registerEvent("healglyphtrapevent", HealGlyphTrapEvent.class);
    }
}