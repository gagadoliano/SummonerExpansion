package summonerexpansion.summonothers;

import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import summonerexpansion.summonminions.*;
import summonerexpansion.summonmobs.HorrorSpiritBossMob;
import summonerexpansion.summonmobs.HorrorSpiritMob;
import summonerexpansion.summonmobs.RiftPortalMob;
import summonerexpansion.summonmobs.WoodMob;
import summonerexpansion.summonmounts.ChiefSummonMount;
import summonerexpansion.summonmounts.MouseSummonMount;

public class SummonerTextures
{
    public static GameTextureSection MosquitoBowVisual;

    public static void initResources()
    {
        // Minions
        EnchantedBabyZombieArcherMinion.texture = GameTexture.fromFile("mobs/enchantedbabyzombiearcherminion");
        HorrorCrawlingZombieMinion.texture = GameTexture.fromFile("mobs/horrorcrawlingzombieminion");
        EnchantedBabyZombieMinion.texture = GameTexture.fromFile("mobs/enchantedbabyzombieminion");
        ExplosiveSnowmanMinion.texture = GameTexture.fromFile("mobs/explosivesnowmanminion");
        BookRunicShieldMinion.texture = GameTexture.fromFile("mobs/runicshieldminion");
        GolemAmethystMinion.texture = GameTexture.fromFile("mobs/golemamethystminion");
        GolemSapphireMinion.texture = GameTexture.fromFile("mobs/golemsapphireminion");
        BookFrozenMinion.texture = GameTexture.fromFile("mobs/frozendwarfminionhair");
        GolemEmeraldMinion.texture = GameTexture.fromFile("mobs/golememeraldminion");
        BookMagmaSlimeMinion.texture = GameTexture.fromFile("mobs/magmaslimeminion");
        LampMinionTungsten.texture = GameTexture.fromFile("mobs/lampminiontungsten");
        MagicPickaxeMinion.texture = GameTexture.fromFile("mobs/magicpickaxeminion");
        FishMackerelMinion.texture = GameTexture.fromFile("mobs/fishmackerelminion");
        LampMinionDungeon.texture = GameTexture.fromFile("mobs/lampminiondungeon");
        FishHerringMinion.texture = GameTexture.fromFile("mobs/fishherringminion");
        GoblinChestMinion.texture = GameTexture.fromFile("mobs/goblinchestminion");
        SpiderBrideMinion.texture = GameTexture.fromFile("mobs/spiderbrideminion");
        HorrorBullMinion.texture = GameTexture.fromFile("mobs/horrorbullminion");
        HorrorWolfMinion.texture = GameTexture.fromFile("mobs/horrorwolfminion");
        HorrorBabyMinion.texture = GameTexture.fromFile("mobs/horrorbabyminion");
        LampMinionCopper.texture = GameTexture.fromFile("mobs/lampminioncopper");
        FishSalmonMinion.texture = GameTexture.fromFile("mobs/fishsalmonminion");
        GoblinHeadMinion.texture = GameTexture.fromFile("mobs/goblinheadminion");
        BookMushroomMinion.texture = GameTexture.fromFile("mobs/mushroomminion");
        LampMinionCastle.texture = GameTexture.fromFile("mobs/lampminioncastle");
        GoblinLegMinion.texture = GameTexture.fromFile("mobs/goblinlegminion");
        FishTroutMinion.texture = GameTexture.fromFile("mobs/fishtroutminion");
        HorrorBatMinion.texture = GameTexture.fromFile("mobs/horrorbatminion");
        GolemRubyMinion.texture = GameTexture.fromFile("mobs/golemrubyminion");
        MagicAxeMinion.texture = GameTexture.fromFile("mobs/magicaxeminion");
        LampMinionGold.texture = GameTexture.fromFile("mobs/lampminiongold");
        BeeQueenMinion.texture = GameTexture.fromFile("mobs/beequeenminion");
        FishCarpMinion.texture = GameTexture.fromFile("mobs/fishcarpminion");
        VampireMinion.texture = GameTexture.fromFile("mobs/vampireminion");
        FishianMinion.texture = GameTexture.fromFile("mobs/fishianminion");
        CactusMinion.texture = GameTexture.fromFile("mobs/cactusminion");
        MouseMinion.texture = GameTexture.fromFile("mobs/mouseminion");

        // Worm
        SandWormHeadMinion.texture = GameTexture.fromFile("mobs/sandwormminion");
        SandWormBodyMinion.texture = GameTexture.fromFile("mobs/sandwormminion");

        // Sentry
        BookMushroomSentry.texture = GameTexture.fromFile("mobs/mushroomsentry");
        IceBlossomSentry.texture = GameTexture.fromFile("mobs/iceblossomsentry");
        SunflowerSentry.texture = GameTexture.fromFile("mobs/sunflowersentry");
        HorrorSentry.texture = GameTexture.fromFile("mobs/horrorspikesentry");
        FiremoneSentry.texture = GameTexture.fromFile("mobs/firemonesentry");
        CaveglowSentry.texture = GameTexture.fromFile("mobs/caveglowsentry");
        CoffinSentry.texture = GameTexture.fromFile("mobs/coffinsentry");
        LeafShotSentry.texture = GameTexture.fromFile("mobs/leafshotsentry");
        LeafShotHeatSentry.texture = GameTexture.fromFile("mobs/leafshotheatsentry");
        LeafShotColdSentry.texture = GameTexture.fromFile("mobs/leafshotcoldsentry");

        // Mobs
        HorrorSpiritBossMob.texture = GameTexture.fromFile("mobs/horrorspiritbossmob");
        HorrorSpiritMob.texture = GameTexture.fromFile("mobs/horrorspiritmob");
        WoodMob.texture = GameTexture.fromFile("mobs/woodmob");

        // Boss
        RiftPortalMob.texture = GameTexture.fromFile("mobs/riftportalmob");
        RiftPortalMob.icon =  GameTexture.fromFile("mobicons/riftportalicon");

        // Mounts
        ChiefSummonMount.texture =  GameTexture.fromFile("mobs/chiefsummonmount");
        ChiefSummonMount.texture_mask =  GameTexture.fromFile("mobs/chiefsummonmount_mask");
        MouseSummonMount.texture_mask =  GameTexture.fromFile("mobs/chiefsummonmount_mask");

        // Particles
        GameTexture MosquitoBowTexture = GameTexture.fromFile("particles/mosquitobowpool");
        MosquitoBowVisual = GameResources.particlesTextureGenerator.addTexture(MosquitoBowTexture);
    }
}