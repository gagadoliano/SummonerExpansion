package summonerexpansion.summonothers;

import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.MobRegistry;
import summonerexpansion.summonminions.*;
import summonerexpansion.summonmobs.*;

public class SummonerMobs
{
    public static void registerSummonMobs()
    {
        // Minions
        MobRegistry.registerMob("enchantedbabyzombiearcherminion", EnchantedBabyZombieArcherMinion.class, false);
        MobRegistry.registerMob("enchantedbabyzombieminion", EnchantedBabyZombieMinion.class, false);
        MobRegistry.registerMob("mummysummonminion", TrinketMummySummonMinion.class, false);
        MobRegistry.registerMob("mummymagicminion", TrinketMummyMagicMinion.class, false);
        MobRegistry.registerMob("golemamethystminion", GolemAmethystMinion.class, false);
        MobRegistry.registerMob("golemsapphireminion", GolemSapphireMinion.class, false);
        MobRegistry.registerMob("golememeraldminion", GolemEmeraldMinion.class, false);
        MobRegistry.registerMob("lampminiontungsten", LampMinionTungsten.class, false);
        MobRegistry.registerMob("lampminiondungeon", LampMinionDungeon.class, false);
        MobRegistry.registerMob("spiritghoulminion", SpiritGhoulMinion.class, false);
        MobRegistry.registerMob("runerangedminion", RuneRangedMinion.class, false);
        MobRegistry.registerMob("lampminioncopper", LampMinionCopper.class, false);
        MobRegistry.registerMob("lampminioncastle", LampMinionCastle.class, false);
        MobRegistry.registerMob("icewizardminion", IceWizardMinion.class, false);
        MobRegistry.registerMob("runemeleeminion", RuneMeleeMinion.class, false);
        MobRegistry.registerMob("golemrubyminion", GolemRubyMinion.class, false);
        MobRegistry.registerMob("bearpolarminion", BearPolarMinion.class, false);
        MobRegistry.registerMob("redspiderminion", RedSpiderMinion.class, false);
        MobRegistry.registerMob("lampminiongold", LampMinionGold.class, false);
        MobRegistry.registerMob("beequeenminion", BeeQueenMinion.class, false);
        MobRegistry.registerMob("vampireminion", VampireMinion.class, false);
        MobRegistry.registerMob("cactusminion", CactusMinion.class, false);
        MobRegistry.registerMob("farmerminion", FarmerMinion.class, false);
        MobRegistry.registerMob("bearminion", BearMinion.class, false);
        MobRegistry.registerMob("golemtopazminion", GolemTopazMinion.class, false);

        // Worm Minions
        MobRegistry.registerMob("sandwormheadminion", SandWormHeadMinion.class, false);
        MobRegistry.registerMob("sandwormbodyminion", SandWormBodyMinion.class, false);

        // Secondary Minions
        MobRegistry.registerMob("explosivesnowmanminion", ExplosiveSnowmanMinion.class, false);
        MobRegistry.registerMob("runicshieldminion", BookRunicShieldMinion.class, false);
        MobRegistry.registerMob("magmaslimeminion", BookMagmaSlimeMinion.class, false);
        MobRegistry.registerMob("magicpickaxeminion", MagicPickaxeMinion.class, false);
        MobRegistry.registerMob("goblinchestminion", GoblinChestMinion.class, false);
        MobRegistry.registerMob("frozendwarfminion", BookFrozenMinion.class, false);
        MobRegistry.registerMob("fishsalmonminion", FishSalmonMinion.class, false);
        MobRegistry.registerMob("goblinheadminion", GoblinHeadMinion.class, false);
        MobRegistry.registerMob("fishtroutminion", FishTroutMinion.class, false);
        MobRegistry.registerMob("goblinlegminion", GoblinLegMinion.class, false);
        MobRegistry.registerMob("magicaxeminion", MagicAxeMinion.class, false);
        MobRegistry.registerMob("beebookminion", BookBeeMinion.class, false);

        // Melee Minions
        MobRegistry.registerMob("horrorcrawlingzombieminion", HorrorCrawlingZombieMinion.class, false);
        MobRegistry.registerMob("fishmackerelminion", FishMackerelMinion.class, false);
        MobRegistry.registerMob("fishherringminion", FishHerringMinion.class, false);
        MobRegistry.registerMob("horrorbullminion", HorrorBullMinion.class, false);
        MobRegistry.registerMob("horrorwolfminion", HorrorWolfMinion.class, false);
        MobRegistry.registerMob("fishcarpminion", FishCarpMinion.class, false);
        MobRegistry.registerMob("fishianminion", FishianMinion.class, false);

        // Range Minions
        MobRegistry.registerMob("mosquitobowminion", BowMosquitoMinion.class, false);

        // Magic Minions

        // Armor Minions
        MobRegistry.registerMob("spiderbrideminion", SetSpiderBrideMinion.class, false);
        MobRegistry.registerMob("horrorbabyminion", SetHorrorBabyMinion.class, false);
        MobRegistry.registerMob("mouseminion", SetMouseMinion.class, false);

        // Trinket Minions
        MobRegistry.registerMob("horrorbatminion", TrinketHorrorBatMinion.class, false);
        MobRegistry.registerMob("jellyfishminion", PetJellyfishMinion.class, false);
        MobRegistry.registerMob("beetminion", PetBeetMinion.class, false);

        // Sentries
        MobRegistry.registerMob("leafshotcoldsentry", LeafShotColdSentry.class, false);
        MobRegistry.registerMob("leafshotheatsentry", LeafShotHeatSentry.class, false);
        MobRegistry.registerMob("mushroomsentry", BookMushroomSentry.class, false);
        MobRegistry.registerMob("coffinsentry", VampireCoffinSentry.class, false);
        MobRegistry.registerMob("horrorspikesentry", HorrorSentry.class, false);
        MobRegistry.registerMob("caveglowsentry", CaveglowSentry.class, false);
        MobRegistry.registerMob("leafshotsentry", LeafShotSentry.class, false);
        MobRegistry.registerMob("xmastreesentry", XmasTreeSentry.class, false);

        // Melee Sentries

        // Ranged Sentries

        // Magic Sentries
        MobRegistry.registerMob("iceblossomsentryt5", IceBlossomSentryT5.class, false);
        MobRegistry.registerMob("iceblossomsentryt1", IceBlossomSentryT1.class, false);
        MobRegistry.registerMob("sunflowersentryt5", SunflowerSentryT5.class, false);
        MobRegistry.registerMob("sunflowersentryt1", SunflowerSentryT1.class, false);
        MobRegistry.registerMob("iceblossomsentry", IceBlossomSentry.class, false);
        MobRegistry.registerMob("firemonesentryt5", FiremoneSentryT5.class, false);
        MobRegistry.registerMob("firemonesentryt1", FiremoneSentryT1.class, false);
        MobRegistry.registerMob("sunflowersentry", SunflowerSentry.class, false);
        MobRegistry.registerMob("firemonesentry", FiremoneSentry.class, false);

        // Minions from minions
        MobRegistry.registerMob("batcryptminion", VampireBatCryptMinion.class, false);
        MobRegistry.registerMob("mushroomminion", BookMushroomMinion.class, false);
        MobRegistry.registerMob("beeminion", BeeMinion.class, false);

        // Mobs
        MobRegistry.registerMob("riftportalmob", RiftPortalMob.class, true, true);
        MobRegistry.registerMob("horrorspiritbossmob", HorrorSpiritBossMob.class, true);
        MobRegistry.registerMob("horrorspiritmob", HorrorSpiritMob.class, true);
        MobRegistry.registerMob("woodmob", WoodMob.class, true);

        // Settlers
        MobRegistry.registerMob("druidhuman", DruidHumanMob.class, true);

        // Others
        MobRegistry.registerMob("tanktrainingdummy", TankTrainingDummyMob.class, false, false, new LocalMessage("object", "trainingdummy"), null);
    }
}