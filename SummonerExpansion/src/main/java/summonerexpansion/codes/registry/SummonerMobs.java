package summonerexpansion.codes.registry;

import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.MobRegistry;
import summonerexpansion.mobs.summonminions.*;
import summonerexpansion.mobs.summonminions.baseminions.*;
import summonerexpansion.mobs.summonminions.magicminions.*;
import summonerexpansion.mobs.summonminions.meleeminions.*;
import summonerexpansion.mobs.summonminions.rangedminions.*;
import summonerexpansion.mobs.summonminions.setminions.*;
import summonerexpansion.mobs.summonminions.trinketminions.*;
import summonerexpansion.mobs.summonminions.wormminions.*;
import summonerexpansion.mobs.summonmobs.*;

public class SummonerMobs
{
    public static void registerSummonMobs()
    {
        SummonerMobs.registerSummonWeaponMinions();
        SummonerMobs.registerSummonArmorMinions();
        SummonerMobs.registerSummonTrinketMinions();
        SummonerMobs.registerSummonBase();
        SummonerMobs.registerSummonSettlers();
        SummonerMobs.registerSummonEntity();
    }

    public static void registerSummonWeaponMinions()
    {
        // Minions
        MobRegistry.registerMob("enchantedbabyzombiearcherminion", EnchantedBabyZombieArcherMinion.class, false);
        MobRegistry.registerMob("enchantedbabyzombieminion", EnchantedBabyZombieMinion.class, false);
        MobRegistry.registerMob("butterflygreenminion", BookGreenButterflyMinion.class, false);
        MobRegistry.registerMob("butterflyblueminion", BookBlueButterflyMinion.class, false);
        MobRegistry.registerMob("butterflyredminion", BookRedButterflyMinion.class, false);
        MobRegistry.registerMob("redspidermeleeminion", RedSpiderMeleeMinion.class, false);
        MobRegistry.registerMob("redspiderrangeminion", RedSpiderRangeMinion.class, false);
        MobRegistry.registerMob("golemamethystminion", GolemAmethystMinion.class, false);
        MobRegistry.registerMob("golemsapphireminion", GolemSapphireMinion.class, false);
        MobRegistry.registerMob("golememeraldminion", GolemEmeraldMinion.class, false);
        MobRegistry.registerMob("lampminiontungsten", LampMinionTungsten.class, false);
        MobRegistry.registerMob("lampminiondungeon", LampMinionDungeon.class, false);
        MobRegistry.registerMob("spiritghoulminion", SpiritGhoulMinion.class, false);
        MobRegistry.registerMob("golemtopazminion", GolemTopazMinion.class, false);
        MobRegistry.registerMob("runerangedminion", RuneRangedMinion.class, false);
        MobRegistry.registerMob("lampminioncopper", LampMinionCopper.class, false);
        MobRegistry.registerMob("lampminioncastle", LampMinionCastle.class, false);
        MobRegistry.registerMob("icewizardminion", IceWizardMinion.class, false);
        MobRegistry.registerMob("runemeleeminion", RuneMeleeMinion.class, false);
        MobRegistry.registerMob("golemrubyminion", GolemRubyMinion.class, false);
        MobRegistry.registerMob("bearpolarminion", BearPolarMinion.class, false);
        MobRegistry.registerMob("lampminiongold", LampMinionGold.class, false);
        MobRegistry.registerMob("beequeenminion", BeeQueenMinion.class, false);
        MobRegistry.registerMob("vampireminion", VampireMinion.class, false);
        MobRegistry.registerMob("cactusminion", CactusMinion.class, false);
        MobRegistry.registerMob("farmerminion", FarmerMinion.class, false);
        MobRegistry.registerMob("bearminion", BearMinion.class, false);

        // Worm Minions
        MobRegistry.registerMob("fallendragonheadminion", FallenDragonHeadMinion.class, false);
        MobRegistry.registerMob("fallendragonbodyminion", FallenDragonBodyMinion.class, false);
        MobRegistry.registerMob("sandwormheadminion", SandWormHeadMinion.class, false);
        MobRegistry.registerMob("sandwormbodyminion", SandWormBodyMinion.class, false);

        // Secondary Minions
        MobRegistry.registerMob("explosivesnowmanminion", ExplosiveSnowmanMinion.class, false);
        MobRegistry.registerMob("runicshieldminion", BookRunicShieldMinion.class, false);
        MobRegistry.registerMob("magmaslimeminion", BookMagmaSlimeMinion.class, false);
        MobRegistry.registerMob("frozendwarfminion", BookFrozenMinion.class, false);
        MobRegistry.registerMob("copperpickminion", CopperPickMinion.class, false);
        MobRegistry.registerMob("copperaxeminion", CopperAxeMinion.class, false);
        MobRegistry.registerMob("woodpickminion", WoodPickMinion.class, false);
        MobRegistry.registerMob("ironpickminion", IronPickMinion.class, false);
        MobRegistry.registerMob("goldpickminion", GoldPickMinion.class, false);
        MobRegistry.registerMob("woodaxeminion", WoodAxeMinion.class, false);
        MobRegistry.registerMob("ironaxeminion", IronAxeMinion.class, false);
        MobRegistry.registerMob("goldaxeminion", GoldAxeMinion.class, false);
        MobRegistry.registerMob("beebookminion", BookBeeMinion.class, false);

        // Melee Minions
        MobRegistry.registerMob("horrorcrawlingzombieminion", HorrorCrawlingZombieMinion.class, false);
        MobRegistry.registerMob("clawancestorknightminion", ClawAncestorKnightMinion.class, false);
        MobRegistry.registerMob("clawdemonportalminion", ClawDemonPortalMinion.class, false);
        MobRegistry.registerMob("fishmackerelminion", FishMackerelMinion.class, false);
        MobRegistry.registerMob("fishherringminion", FishHerringMinion.class, false);
        MobRegistry.registerMob("goblinchestminion", GoblinChestMinion.class, false);
        MobRegistry.registerMob("goblinheadminion", GoblinHeadMinion.class, false);
        MobRegistry.registerMob("horrorbullminion", HorrorBullMinion.class, false);
        MobRegistry.registerMob("fishsalmonminion", FishSalmonMinion.class, false);
        MobRegistry.registerMob("horrorwolfminion", HorrorWolfMinion.class, false);
        MobRegistry.registerMob("goblinlegminion", GoblinLegMinion.class, false);
        MobRegistry.registerMob("fishtroutminion", FishTroutMinion.class, false);
        MobRegistry.registerMob("fishcarpminion", FishCarpMinion.class, false);
        MobRegistry.registerMob("fishianminion", FishianMinion.class, false);
        MobRegistry.registerMob("planetmarsproj", PlanetMarsMinion.class, false);
        MobRegistry.registerMob("planetneptuneproj", PlanetNeptuneMinion.class, false);
        MobRegistry.registerMob("planetsaturnproj", PlanetSaturnMinion.class, false);
        MobRegistry.registerMob("planetvenusproj", PlanetVenusMinion.class, false);

        // Range Minions
        MobRegistry.registerMob("mosquitobowminion", BowMosquitoMinion.class, false);
        MobRegistry.registerMob("yangninjaminion", YangNinjaMinion.class, false);
        MobRegistry.registerMob("yinninjaminion", YinNinjaMinion.class, false);

        // Magic Minions
        MobRegistry.registerMob("pinewoodminion", PineWoodMinion.class, false);

        // Sentries
        MobRegistry.registerMob("clawdemonportalsentry", ClawDemonPortalSentry.class, false);
        MobRegistry.registerMob("mushroomsentry", BookMushroomSentry.class, false);
        MobRegistry.registerMob("coffinsentry", VampireCoffinSentry.class, false);
        MobRegistry.registerMob("caveglowsentry", CaveglowSentry.class, false);
        MobRegistry.registerMob("xmastreesentry", XmasTreeSentry.class, false);
        MobRegistry.registerMob("coffebeamsentry", CoffeBeamSentry.class, false);

        // Melee Sentries
        MobRegistry.registerMob("horrorspikesentry", HorrorSentry.class, false);

        // Ranged Sentries

        // Magic Sentries
        MobRegistry.registerMob("iceblossomsentry", IceBlossomSentry.class, false);
        MobRegistry.registerMob("sunflowersentry", SunflowerSentry.class, false);
        MobRegistry.registerMob("firemonesentry", FiremoneSentry.class, false);

        // Consumable sentries
        MobRegistry.registerMob("leafshotcoldsentry", LeafShotColdSentry.class, false);
        MobRegistry.registerMob("leafshotheatsentry", LeafShotHeatSentry.class, false);
        MobRegistry.registerMob("leafshotsentry", LeafShotSentry.class, false);

        // Minions from minions
        MobRegistry.registerMob("batcryptminion", VampireBatCryptMinion.class, false);
        MobRegistry.registerMob("mushroomminion", BookMushroomMinion.class, false);
        MobRegistry.registerMob("beeminion", BeeMinion.class, false);
    }

    public static void registerSummonArmorMinions()
    {
        // Armor Minions
        MobRegistry.registerMob("settitaniumsummonminion", SetTitaniumSummonMinion.class, false);
        MobRegistry.registerMob("settitaniumrangedminion", SetTitaniumRangedMinion.class, false);
        MobRegistry.registerMob("settitaniummagicminion", SetTitaniumMagicMinion.class, false);
        MobRegistry.registerMob("settitaniummeleeminion", SetTitaniumMeleeMinion.class, false);

        MobRegistry.registerMob("ghostcaptainsminion", SetGhostCaptainMinion.class, false);
        MobRegistry.registerMob("sharpshooterminion", SetSharpshooterMinion.class, false);
        MobRegistry.registerMob("agedchampionminion", SetAgedChampionMinion.class, false);
        MobRegistry.registerMob("slimewarriorminion", SetSlimeWarriorMinion.class, false);
        MobRegistry.registerMob("spiderbrideminion", SetSpiderBrideMinion.class, false);
        MobRegistry.registerMob("horrorbabyminion", SetHorrorBabyMinion.class, false);
        MobRegistry.registerMob("slimemageminion", SetSlimeMageMinion.class, false);
        MobRegistry.registerMob("ravenlordminion", SetRavenlordMinion.class, false);
        MobRegistry.registerMob("setcloudminion", SetCloudMinion.class, false);
        MobRegistry.registerMob("locustminion", SetLocustMinion.class, false);
        MobRegistry.registerMob("sailorminion", SetSailorMinion.class, false);
        MobRegistry.registerMob("mouseminion", SetMouseMinion.class, false);
        MobRegistry.registerMob("batsetminion", SetBatMinion.class, false);
        MobRegistry.registerMob("chefminion", SetChefMinion.class, false);

        // Armor Sentry
        MobRegistry.registerMob("arcanicpylonsentry", SetArcanicPylonSentry.class, false);
    }

    public static void registerSummonTrinketMinions()
    {
        // T1
        // T2
        // T3
        MobRegistry.registerMob("mummysummonminion", TrinketMummySummonMinion.class, false);
        MobRegistry.registerMob("mummymagicminion", TrinketMummyMagicMinion.class, false);
        MobRegistry.registerMob("horrorbatminion", TrinketHorrorBatMinion.class, false);
        // T4
    }

    public static void registerSummonBase()
    {
        // Base
        MobRegistry.registerMob("magictoolbase", MagicToolBase.class, false, false, false);
        MobRegistry.registerMob("humantoolbase", HumanToolBase.class, false, false, false);
        MobRegistry.registerMob("magiclampbase", MagicLampBase.class, false, false, false);
        MobRegistry.registerMob("butterflybase", ButterflyBase.class, false, false, false);
        MobRegistry.registerMob("sentrybase", SentryBase.class, false, false, false);
    }

    public static void registerSummonSettlers()
    {
        // Swamp
        MobRegistry.registerMob("druidhuman", DruidHumanMob.class, true);
    }

    public static void registerSummonEntity()
    {
        // Dummy
        MobRegistry.registerMob("tanktrainingdummy", TankTrainingDummyMob.class, false, false, new LocalMessage("object", "trainingdummy"), null);
    }
}