package summonerexpansion.codes.registries;

import necesse.engine.registries.MobRegistry;
import summonerexpansion.mobs.bosses.*;
import summonerexpansion.mobs.cavemobs.*;
import summonerexpansion.mobs.deepcavemobs.*;
import summonerexpansion.mobs.surfacemobs.*;

public class RegistryMobs
{
    public static void registerMobs()
    {
        RegistryMobs.registerCritters();
        RegistryMobs.registerFoes();
        RegistryMobs.registerBoss();
    }

    public static void registerCritters()
    {
        // Surface
        MobRegistry.registerMob("sandscorpionmob", SandScorpionMob.class, true);
    }

    public static void registerFoes()
    {
        // Surface
        MobRegistry.registerMob("woodmob", WoodMob.class, true);
        MobRegistry.registerMob("honeybeeguardmob", HoneyBeeGuardMob.class, true);
        MobRegistry.registerMob("sandgiantscorpionmob", SandGiantScorpionMob.class, true);
        MobRegistry.registerMob("dollmakermob", DollMakerMob.class, true);

        // Cave
        MobRegistry.registerMob("vampireminibossmob", VampireMiniBossMob.class, true);
        MobRegistry.registerMob("poisonswampslimemob", PoisonSwampSlimeMob.class, true);
        MobRegistry.registerMob("voidswampzombiemob", VoidSwampZombieMob.class, true);

        // Deep cave
        MobRegistry.registerMob("lavacavesharkmob", LavaCaveSharkMob.class, true);
        MobRegistry.registerMob("horrorspiritmob", HorrorSpiritMob.class, true);
        MobRegistry.registerMob("poisonswampslimewormmob", PoisonSwampSlimeWormHead.class, true);
        MobRegistry.registerMob("poisonswampslimewormbody", PoisonSwampSlimeWormBody.class, false);
    }

    public static void registerBoss()
    {
        // Boss
        MobRegistry.registerMob("riftportalmob", BossRiftPortalMob.class, true, true);
        MobRegistry.registerMob("horrorspiritbossmob", HorrorSpiritBossMob.class, true);

        // Boss minions
        MobRegistry.registerMob("horrorspiritcultistmob", HorrorSpiritCultistMob.class, false);
        MobRegistry.registerMob("horrorspiritcultmelee", HorrorSpiritCultistMelee.class, true);
        MobRegistry.registerMob("horrorspiritcultmagic", HorrorSpiritCultistMagic.class, true);
        MobRegistry.registerMob("horrorspiritcultrange", HorrorSpiritCultistRange.class, true);
    }
}