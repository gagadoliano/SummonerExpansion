package summonerexpansion.codes.registries;

import necesse.engine.registries.MobRegistry;
import summonerexpansion.mobs.minions.base.*;
import summonerexpansion.mobs.minions.magic.*;
import summonerexpansion.mobs.minions.melee.*;
import summonerexpansion.mobs.minions.ranged.*;
import summonerexpansion.mobs.minions.sentry.*;
import summonerexpansion.mobs.minions.summon.*;
import summonerexpansion.mobs.minions.worm.*;

public class RegistryMinions
{
    public static void registerMinions()
    {
        RegistryMinions.registerBase();
        RegistryMinions.registerSummon();
        RegistryMinions.registerMelee();
        RegistryMinions.registerMagic();
        RegistryMinions.registerRanged();
        RegistryMinions.registerSentry();
    }

    public static void registerBase()
    {
        // Base
        MobRegistry.registerMob("summonflyingbase", SummonFlyingBase.class, false, false, false);
        MobRegistry.registerMob("summonfishbase", SummonFishBase.class, false, false, false);
        MobRegistry.registerMob("summonwalkbase", SummonWalkBase.class, false, false, false);
        MobRegistry.registerMob("magictoolbase", MagicToolBase.class, false, false, false);
        MobRegistry.registerMob("summonhumanbase", SummonHumanBase.class, false, false, false);
        MobRegistry.registerMob("magiclampbase", MagicLampBase.class, false, false, false);
        MobRegistry.registerMob("butterflybase", ButterflyBase.class, false, false, false);
        MobRegistry.registerMob("sentrybase", SentryBase.class, false, false, false);
    }

    public static void registerSummon()
    {
        // Melee range
        MobRegistry.registerMob("beequeenminion", BeeQueenMinion.class, false);
        MobRegistry.registerMob("beebookminion", BookBeeMinion.class, false);
        MobRegistry.registerMob("golemamethystminion", GolemAmethystMinion.class, false);
        MobRegistry.registerMob("runemeleeminion", RuneMeleeMinion.class, false);
        MobRegistry.registerMob("redspidermeleeminion", RedSpiderMeleeMinion.class, false);
        MobRegistry.registerMob("enchantedbabyzombieminion", EnchantedBabyZombieMinion.class, false);
        MobRegistry.registerMob("spiritghoulminion", SpiritGhoulMinion.class, false);
        MobRegistry.registerMob("bearpolarminion", BearPolarMinion.class, false);
        MobRegistry.registerMob("bearminion", BearMinion.class, false);
        MobRegistry.registerMob("explosivesnowmanminion", ExplosiveSnowmanMinion.class, false);
        MobRegistry.registerMob("runicshieldminion", BookRunicShieldMinion.class, false);
        MobRegistry.registerMob("magmaslimeminion", BookMagmaSlimeMinion.class, false);
        MobRegistry.registerMob("frozendwarfminion", BookFrozenMinion.class, false);
        MobRegistry.registerMob("woodpickminion", WoodPickMinion.class, false);
        MobRegistry.registerMob("woodaxeminion", WoodAxeMinion.class, false);
        MobRegistry.registerMob("copperpickminion", CopperPickMinion.class, false);
        MobRegistry.registerMob("copperaxeminion", CopperAxeMinion.class, false);
        MobRegistry.registerMob("ironpickminion", IronPickMinion.class, false);
        MobRegistry.registerMob("ironaxeminion", IronAxeMinion.class, false);
        MobRegistry.registerMob("goldpickminion", GoldPickMinion.class, false);
        MobRegistry.registerMob("goldaxeminion", GoldAxeMinion.class, false);
        MobRegistry.registerMob("butterflygreenminion", BookGreenButterflyMinion.class, false);
        MobRegistry.registerMob("butterflyblueminion", BookBlueButterflyMinion.class, false);
        MobRegistry.registerMob("butterflyredminion", BookRedButterflyMinion.class, false);
        // Long range
        MobRegistry.registerMob("runerangedminion", RuneRangedMinion.class, false);
        MobRegistry.registerMob("redspiderrangeminion", RedSpiderRangeMinion.class, false);
        MobRegistry.registerMob("enchantedbabyzombiearcherminion", EnchantedBabyZombieArcherMinion.class, false);
        MobRegistry.registerMob("golemsapphireminion", GolemSapphireMinion.class, false);
        MobRegistry.registerMob("golememeraldminion", GolemEmeraldMinion.class, false);
        MobRegistry.registerMob("golemtopazminion", GolemTopazMinion.class, false);
        MobRegistry.registerMob("golemrubyminion", GolemRubyMinion.class, false);
        MobRegistry.registerMob("vampireminion", VampireMinion.class, false);
        MobRegistry.registerMob("cactusminion", CactusMinion.class, false);
        MobRegistry.registerMob("icewizardminion", IceWizardMinion.class, false);
        // Human
        MobRegistry.registerMob("farmerminion", FarmerMinion.class, false);
        // Worms
        MobRegistry.registerMob("sandwormheadminion", SandWormHeadMinion.class, false);
        MobRegistry.registerMob("sandwormbodyminion", SandWormBodyMinion.class, false);
        // Lamps
        MobRegistry.registerMob("lampminioncastle", LampMinionCastle.class, false);
        MobRegistry.registerMob("lampminiontungsten", LampMinionTungsten.class, false);
        MobRegistry.registerMob("lampminiondungeon", LampMinionDungeon.class, false);
        MobRegistry.registerMob("lampminiongold", LampMinionGold.class, false);
        MobRegistry.registerMob("lampminioncopper", LampMinionCopper.class, false);
        // Sub minions
        MobRegistry.registerMob("beeminion", BeeMinion.class, false);
        MobRegistry.registerMob("batcryptminion", VampireBatCryptMinion.class, false);
        MobRegistry.registerMob("mushroomminion", BookMushroomMinion.class, false);
    }

    public static void registerMelee()
    {
        // Claw
        MobRegistry.registerMob("clawancestorknightminion", ClawAncestorKnightMinion.class, false);
        MobRegistry.registerMob("clawdemonportalminion", ClawDemonPortalMinion.class, false);
        MobRegistry.registerMob("clawdemonportalsentry", ClawDemonPortalSentry.class, false);
        MobRegistry.registerMob("fallendragonheadminion", FallenDragonHeadMinion.class, false);
        MobRegistry.registerMob("fallendragonbodyminion", FallenDragonBodyMinion.class, false);
        // Sword
        MobRegistry.registerMob("horrorcrawlingzombieminion", HorrorCrawlingZombieMinion.class, false);
        MobRegistry.registerMob("ramminion", RamMinion.class, false);
        MobRegistry.registerMob("goblinheadminion", GoblinHeadMinion.class, false);
        MobRegistry.registerMob("goblinchestminion", GoblinChestMinion.class, false);
        MobRegistry.registerMob("goblinlegminion", GoblinLegMinion.class, false);
        // Great Sword
        MobRegistry.registerMob("horrorbullminion", HorrorBullMinion.class, false);
        MobRegistry.registerMob("horrorwolfminion", HorrorWolfMinion.class, false);
        // Spear
        MobRegistry.registerMob("fishianminion", FishianMinion.class, false);
        // Glaive
        MobRegistry.registerMob("horrorspikesentry", HorrorSentry.class, false);
        //Boomerang
        MobRegistry.registerMob("fishmackerelminion", FishMackerelMinion.class, false);
        MobRegistry.registerMob("fishherringminion", FishHerringMinion.class, false);
        MobRegistry.registerMob("fishsalmonminion", FishSalmonMinion.class, false);
        MobRegistry.registerMob("fishtroutminion", FishTroutMinion.class, false);
        MobRegistry.registerMob("fishcarpminion", FishCarpMinion.class, false);
        MobRegistry.registerMob("planetneptuneproj", PlanetNeptuneMinion.class, false);
        MobRegistry.registerMob("planetsaturnproj", PlanetSaturnMinion.class, false);
        MobRegistry.registerMob("planetvenusproj", PlanetVenusMinion.class, false);
        MobRegistry.registerMob("planetmarsproj", PlanetMarsMinion.class, false);
    }

    public static void registerMagic()
    {
        // Minions
        MobRegistry.registerMob("pinewoodminion", PineWoodMinion.class, false);
        // Sentries
        MobRegistry.registerMob("iceblossomsentry", IceBlossomSentry.class, false);
        MobRegistry.registerMob("sunflowersentry", SunflowerSentry.class, false);
        MobRegistry.registerMob("firemonesentry", FiremoneSentry.class, false);
    }

    public static void registerRanged()
    {
        // Bow
        MobRegistry.registerMob("mosquitobowminion", BowMosquitoMinion.class, false);
        // Throw
        MobRegistry.registerMob("yangninjaminion", YangNinjaMinion.class, false);
        MobRegistry.registerMob("yinninjaminion", YinNinjaMinion.class, false);
    }

    public static void registerSentry()
    {
        // Shooter
        MobRegistry.registerMob("leafshotcoldsentry", LeafShotColdSentry.class, false);
        MobRegistry.registerMob("leafshotheatsentry", LeafShotHeatSentry.class, false);
        MobRegistry.registerMob("leafshotsentry", LeafShotSentry.class, false);
        MobRegistry.registerMob("caveglowsentry", CaveglowSentry.class, false);
        MobRegistry.registerMob("coffinsentry", VampireCoffinSentry.class, false);
        // Summoner
        MobRegistry.registerMob("mushroomsentry", BookMushroomSentry.class, false);
        // Buffer
        MobRegistry.registerMob("xmastreesentry", XmasTreeSentry.class, false);
        MobRegistry.registerMob("coffebeamsentry", CoffeBeamSentry.class, false);
    }
}