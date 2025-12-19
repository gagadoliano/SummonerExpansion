package summonerexpansion.codes.registry;

import necesse.engine.registries.MobRegistry;
import summonerexpansion.mobs.summonmobs.*;

public class SummonerFoes
{
    public static void registerSummonFoes()
    {
        // Surface
        MobRegistry.registerMob("woodmob", WoodMob.class, true);

        // Cave

        // Deep cave
        MobRegistry.registerMob("horrorspiritmob", HorrorSpiritMob.class, true);

        // Boss
        MobRegistry.registerMob("riftportalmob", BossRiftPortalMob.class, true, true);

        // Boss minions
        MobRegistry.registerMob("horrorspiritcultistmob", HorrorSpiritCultistMob.class, false);
        MobRegistry.registerMob("horrorspiritcultmelee", HorrorSpiritCultistMelee.class, true);
        MobRegistry.registerMob("horrorspiritcultmagic", HorrorSpiritCultistMagic.class, true);
        MobRegistry.registerMob("horrorspiritcultrange", HorrorSpiritCultistRange.class, true);
        MobRegistry.registerMob("horrorspiritbossmob", HorrorSpiritBossMob.class, true);
    }
}