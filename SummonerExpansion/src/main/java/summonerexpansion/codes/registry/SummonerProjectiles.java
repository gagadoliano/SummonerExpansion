package summonerexpansion.codes.registry;

import necesse.engine.registries.ProjectileRegistry;
import necesse.entity.projectile.ZombieArrowProjectile;
import summonerexpansion.allprojs.*;

public class SummonerProjectiles
{
    public static void registerSummonProjs()
    {
        // Weapon Projectiles
        ProjectileRegistry.registerProjectile("mosquitobowproj", MosquitoBowProj.class, "mosquitobowarrow", "nightpiercerarrow_shadow");
        ProjectileRegistry.registerProjectile("iceblossomproj", IceBlossomProj.class, "iceblossomproj", "iceblossomproj_shadow");
        ProjectileRegistry.registerProjectile("sunflowerproj", SunflowerProj.class, "sunflowerproj", "sunflowerproj_shadow");
        ProjectileRegistry.registerProjectile("firemoneproj", FiremoneProj.class, "firemoneproj", "firemoneproj_shadow");
        ProjectileRegistry.registerProjectile("wormproj", WormProj.class, "wormproj", "wormproj_shadow");

        // Summon Minion Projectiles
        ProjectileRegistry.registerProjectile("iceminionjavelinproj", IceMinionJavelinProj.class, "iceminionjavelin", "iceminionjavelin_shadow");
        ProjectileRegistry.registerProjectile("crystalminionproj", GolemRubyProj.class, "crystalminionproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("topazgolemswirl", GolemTopazProj.class, "topazgolemswirl", "topazgolemswirl_shadow");
        ProjectileRegistry.registerProjectile("icewizardproj", IceWizardProj.class, "icewizardproj", "voidapprentice_shadow");
        ProjectileRegistry.registerProjectile("cactusproj", CactusProj.class, "cactusproj", "cactusproj_shadow");
        ProjectileRegistry.registerProjectile("emeraldlaserproj", GolemEmeraldLaserProj.class, null, null);
        ProjectileRegistry.registerProjectile("runeboneproj", RuneBoneProj.class, null, null);
        ProjectileRegistry.registerProjectile("enchantedarrowproj", EnchantedArrowProj.class, "enchantedarrowproj", "arrow_shadow");

        // Sentry Projectiles
        ProjectileRegistry.registerProjectile("caveglowproj", CaveglowProj.class, "caveglowproj", "caveglowproj_shadow");
        ProjectileRegistry.registerProjectile("horrorsentryproj", HorrorSentryProj.class, null, null);
        ProjectileRegistry.registerProjectile("leafcoldballproj", LeafColdBallProj.class, "leafcoldballproj", "bolt_shadow");
        ProjectileRegistry.registerProjectile("leafheatballproj", LeafHeatBallProj.class, "leafheatballproj", "bolt_shadow");
        ProjectileRegistry.registerProjectile("leafballproj", LeafBallProj.class, "leafballproj", "bolt_shadow");

        // Melee Minion Projectiles
        ProjectileRegistry.registerProjectile("clawdemonproj", ClawDemonProj.class, "evilsprotector2", "evilsprotector2_shadow");

        // Ranged Minion Projectiles
        ProjectileRegistry.registerProjectile("ninjastaryinproj", NinjaStarYinProj.class, "ninjastar", "ninjastar_shadow");
        ProjectileRegistry.registerProjectile("ninjastaryangproj", NinjaStarYangProj.class, "ninjastaryang", "ninjastar_shadow");

        // Mount Projectiles
        ProjectileRegistry.registerProjectile("mountvultureproj", MountVultureProj.class, "hatchlingfeather", "hatchlingfeather_shadow");
        ProjectileRegistry.registerProjectile("mountzombiearrowproj", MountZombieArrowProj.class, "stonearrow", "arrow_shadow");
        ProjectileRegistry.registerProjectile("mountbounceboneproj", MountBounceBoneProj.class, "bone", "bone_shadow");
        ProjectileRegistry.registerProjectile("mountcryptboltproj", MountCryptBoltProj.class, "cryptvampirebolt", "cryptvampirebolt_shadow");

        // Trinket Projectiles
        ProjectileRegistry.registerProjectile("crystalamethysttrinketproj", CrystalAmethystTrinketProj.class, "crystalamethysttrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystalsapphiretrinketproj", CrystalSapphireTrinketProj.class, "crystalsapphiretrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystalemeraldtrinketproj", CrystalEmeraldTrinketProj.class, "crystalemeraldtrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystaltopaztrinketproj", CrystalTopazTrinketProj.class, "crystaltopaztrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystalrubytrinketproj", CrystalRubyTrinketProj.class, "crystalrubytrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystaltrinketproj", CrystalTrinketProj.class, "crystaltrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("beetproj", BeetProj.class, "beetproj", null);
        ProjectileRegistry.registerProjectile("teapotproj", TeaPotProj.class, null, null);

        // Armor Set Projectiles
        ProjectileRegistry.registerProjectile("sharpshooterproj", SharpshooterProj.class, null, null);
        ProjectileRegistry.registerProjectile("coppersetproj", CopperSetProj.class, null, null);

        // Mob Projectiles
        ProjectileRegistry.registerProjectile("woodmobproj", WoodMobProj.class, null, null);
        ProjectileRegistry.registerProjectile("horrorwaveproj", HorrorWaveProj.class, "horrorwaveproj", null);
        ProjectileRegistry.registerProjectile("horrorcultboltproj", HorrorCultBoltProj.class, "horrorcultboltproj", "cryptvampirebolt_shadow");
        ProjectileRegistry.registerProjectile("horrorcultarrowproj", HorrorCultArrowProj.class, "horrorcultarrowproj", "arrow_shadow");
        ProjectileRegistry.registerProjectile("horrorcultminionproj", HorrorCultMinionProj.class, null, null);
    }
}