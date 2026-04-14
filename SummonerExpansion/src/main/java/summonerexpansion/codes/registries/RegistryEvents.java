package summonerexpansion.codes.registries;

import necesse.engine.registries.LevelEventRegistry;
import summonerexpansion.codes.events.*;

public class RegistryEvents
{
    public static void registerEvents()
    {
        RegistryEvents.registerExplosion();
        RegistryEvents.registerPool();
        RegistryEvents.registerThunder();
        RegistryEvents.registerWeapon();
        RegistryEvents.registerGlyph();
    }

    public static void registerExplosion()
    {
        LevelEventRegistry.registerEvent("snowmanexplosionlevelevent", SnowmanExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("pumpkinexplosionevent", PumpkinExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("snowmansetlevelevent", SnowmanSetLevelEvent.class);
        LevelEventRegistry.registerEvent("cookpothealevent", CookpotHealEvent.class);
        LevelEventRegistry.registerEvent("doomshroomevent", DoomShroomEvent.class);
    }

    public static void registerPool()
    {
        LevelEventRegistry.registerEvent("spiritghoulpoolevent", SpiritGhoulPoolEvent.class);
    }

    public static void registerThunder()
    {
        LevelEventRegistry.registerEvent("titaniumlightninglevelevent", TitaniumLightningLevelEvent.class);
    }

    public static void registerWeapon()
    {
        LevelEventRegistry.registerEvent("druidclawdashlevelevent", DruidClawDashLevelEvent.class);
        LevelEventRegistry.registerEvent("pinewoodstaffevent", PineWoodStaffEvent.class);
        LevelEventRegistry.registerEvent("mosquitobowevent", MosquitoBowEvent.class);
        LevelEventRegistry.registerEvent("showwhipattackevent", ShowWhipAttackEvent.class);
    }

    public static void registerGlyph()
    {
        LevelEventRegistry.registerEvent("healglyphtrapevent", HealGlyphTrapEvent.class);
    }
}