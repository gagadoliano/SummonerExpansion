package summonerexpansion.summonothers;

import necesse.engine.registries.ProjectileRegistry;
import summonerexpansion.summonprojs.*;

public class SummonerProjectiles
{
    public static void registerSummonProjs()
    {
        // Weapon Projectiles
        ProjectileRegistry.registerProjectile("mosquitobowproj", MosquitoBowProj.class, "mosquitobowarrow", "nightpiercerarrow_shadow");
        ProjectileRegistry.registerProjectile("wormproj", WormProj.class, "wormproj", "wormproj_shadow");

        // Minion Projectiles
        ProjectileRegistry.registerProjectile("iceminionjavelinproj", IceMinionJavelinProj.class, "iceminionjavelin", "iceminionjavelin_shadow");
        ProjectileRegistry.registerProjectile("crystalminionproj", CrystalMinionProj.class, "crystalminionproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("emeraldlaserproj", EmeraldLaserProj.class, null, null);
        ProjectileRegistry.registerProjectile("runeboneproj", RuneBoneProj.class, null, null);

        // Sentry Projectiles
        ProjectileRegistry.registerProjectile("iceblossomproj", IceBlossomProj.class, "iceblossomproj", "iceblossomproj_shadow");
        ProjectileRegistry.registerProjectile("sunflowerproj", SunflowerProj.class, "sunflowerproj", "sunflowerproj_shadow");
        ProjectileRegistry.registerProjectile("firemoneproj", FiremoneProj.class, "firemoneproj", "firemoneproj_shadow");
        ProjectileRegistry.registerProjectile("caveglowproj", CaveglowProj.class, "caveglowproj", "caveglowproj_shadow");
        ProjectileRegistry.registerProjectile("horrorsentryproj", HorrorSentryProj.class, null, null);
    }
}