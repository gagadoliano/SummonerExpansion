package summonerexpansion.codes.registry;

import necesse.engine.registries.ProjectileRegistry;
import necesse.entity.projectile.WaterboltProjectile;
import summonerexpansion.allprojs.*;
import summonerexpansion.allprojs.armorprojs.*;
import summonerexpansion.allprojs.foeprojs.*;
import summonerexpansion.allprojs.magicprojs.*;
import summonerexpansion.allprojs.meleeprojs.*;
import summonerexpansion.allprojs.mountprojs.*;
import summonerexpansion.allprojs.rangedprojs.*;
import summonerexpansion.allprojs.trinketprojs.*;

public class SummonerProjectiles
{
    public static void registerSummonProjs()
    {
        // Summon Minion Projectiles
        ProjectileRegistry.registerProjectile("iceminionjavelinproj", IceMinionJavelinProj.class, "iceminionjavelin", "iceminionjavelin_shadow");
        ProjectileRegistry.registerProjectile("topazgolemswirl", GolemTopazProj.class, "topazgolemswirl", "topazgolemswirl_shadow");
        ProjectileRegistry.registerProjectile("enchantedarrowproj", EnchantedArrowProj.class, "enchantedarrowproj", "arrow_shadow");
        ProjectileRegistry.registerProjectile("rubygolemproj", GolemRubyProj.class, "rubygolemproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("icewizardproj", IceWizardProj.class, "icewizardproj", "voidapprentice_shadow");
        ProjectileRegistry.registerProjectile("cactusproj", CactusProj.class, "cactusproj", "cactusproj_shadow");
        ProjectileRegistry.registerProjectile("emeraldlaserproj", GolemEmeraldLaserProj.class, null, null);
        ProjectileRegistry.registerProjectile("runeboneproj", RuneBoneProj.class, null, null);

        // Sentry Projectiles
        ProjectileRegistry.registerProjectile("leafcoldballproj", LeafColdBallProj.class, "leafcoldballproj", "bolt_shadow");
        ProjectileRegistry.registerProjectile("leafheatballproj", LeafHeatBallProj.class, "leafheatballproj", "bolt_shadow");
        ProjectileRegistry.registerProjectile("caveglowproj", CaveglowProj.class, "caveglowproj", "caveglowproj_shadow");
        ProjectileRegistry.registerProjectile("leafballproj", LeafBallProj.class, "leafballproj", "bolt_shadow");
        ProjectileRegistry.registerProjectile("horrorsentryproj", HorrorSentryProj.class, null, null);

        // Melee Minion Projectiles
        ProjectileRegistry.registerProjectile("clawdemonproj", ClawDemonProj.class, "evilsprotector2", "evilsprotector2_shadow");
        ProjectileRegistry.registerProjectile("globeboomerangproj", GlobeBoomerangProj.class, "globeboomerangproj", null);
        ProjectileRegistry.registerProjectile("wormproj", WormProj.class, "wormproj", "wormproj_shadow");

        // Ranged Minion Projectiles
        ProjectileRegistry.registerProjectile("mosquitobowproj", MosquitoBowProj.class, "mosquitobowarrow", "nightpiercerarrow_shadow");
        ProjectileRegistry.registerProjectile("ninjastaryangproj", NinjaStarYangProj.class, "ninjastaryang", "ninjastar_shadow");
        ProjectileRegistry.registerProjectile("ninjastaryinproj", NinjaStarYinProj.class, "ninjastar", "ninjastar_shadow");

        // Magic Minion Projectiles
        ProjectileRegistry.registerProjectile("iceblossomproj", IceBlossomProj.class, "iceblossomproj", "iceblossomproj_shadow");
        ProjectileRegistry.registerProjectile("sunflowerproj", SunflowerProj.class, "sunflowerproj", "sunflowerproj_shadow");
        ProjectileRegistry.registerProjectile("firemoneproj", FiremoneProj.class, "firemoneproj", "firemoneproj_shadow");
        ProjectileRegistry.registerProjectile("applewalkproj", AppleWalkProj.class, "applewalkproj", null);

        // Mount Projectiles
        ProjectileRegistry.registerProjectile("mountcryptboltproj", MountCryptBoltProj.class, "cryptvampirebolt", "cryptvampirebolt_shadow");
        ProjectileRegistry.registerProjectile("mountvultureproj", MountVultureProj.class, "hatchlingfeather", "hatchlingfeather_shadow");
        ProjectileRegistry.registerProjectile("mountzombiearrowproj", MountZombieArrowProj.class, "stonearrow", "arrow_shadow");
        ProjectileRegistry.registerProjectile("mountbounceboneproj", MountBounceBoneProj.class, "bone", "bone_shadow");

        // Trinket Projectiles
        ProjectileRegistry.registerProjectile("crystalamethysttrinketproj", CrystalAmethystTrinketProj.class, "crystalamethysttrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystalsapphiretrinketproj", CrystalSapphireTrinketProj.class, "crystalsapphiretrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystalemeraldtrinketproj", CrystalEmeraldTrinketProj.class, "crystalemeraldtrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystaltopaztrinketproj", CrystalTopazTrinketProj.class, "crystaltopaztrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystalrubytrinketproj", CrystalRubyTrinketProj.class, "crystalrubytrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("crystaltrinketproj", CrystalTrinketProj.class, "crystaltrinketproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("beetproj", PetBeetProj.class, "beetproj", null);
        ProjectileRegistry.registerProjectile("carrotproj", PetCarrotProj.class, "carrotproj", null);
        ProjectileRegistry.registerProjectile("potatoproj", PetPotatoProj.class, "potatoproj", "swampboulder_shadow");
        ProjectileRegistry.registerProjectile("onionproj", PetOnionProj.class, "onionproj", null);
        ProjectileRegistry.registerProjectile("pumpkinproj", PetPumpkinProj.class, "pumpkinproj", null);
        ProjectileRegistry.registerProjectile("teapotproj", TeaPotProj.class, null, null);

        // Armor Set Projectiles
        ProjectileRegistry.registerProjectile("setmageslimeboltproj", SetMageSlimeBoltProj.class, "mageslimebolt", "mageslimebolt_shadow");
        ProjectileRegistry.registerProjectile("cloudwaterproj", CloudWaterProj.class, "waterbolt", "waterbolt_shadow");
        ProjectileRegistry.registerProjectile("titaniumarrowproj", TitaniumArrowProj.class, "titaniumarrowproj", null);
        ProjectileRegistry.registerProjectile("titaniumlightningproj", TitaniumLightningProj.class, null, null);
        ProjectileRegistry.registerProjectile("sharpshooterproj", SharpshooterProj.class, null, null);
        ProjectileRegistry.registerProjectile("coppersetproj", CopperSetProj.class, null, null);

        // Mob Projectiles
        ProjectileRegistry.registerProjectile("horrorcultboltproj", HorrorCultBoltProj.class, "horrorcultboltproj", "cryptvampirebolt_shadow");
        ProjectileRegistry.registerProjectile("horrorcultarrowproj", HorrorCultArrowProj.class, "horrorcultarrowproj", "arrow_shadow");
        ProjectileRegistry.registerProjectile("horrorcultminionproj", HorrorCultMinionProj.class, null, null);
        ProjectileRegistry.registerProjectile("horrorwaveproj", HorrorWaveProj.class, "horrorwaveproj", null);
        ProjectileRegistry.registerProjectile("woodmobproj", WoodMobProj.class, null, null);
    }
}