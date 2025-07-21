package summonerexpansion.summonothers;

import necesse.entity.mobs.HumanTexture;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import summonerexpansion.summonminions.*;
import summonerexpansion.summonmobs.*;
import summonerexpansion.summonmounts.*;

public class SummonerTextures
{
    public static HumanTexture enchantedBabyZombieArcherMinion;
    public static HumanTexture enchantedBabyZombieMinion;
    public static HumanTexture iceWizardMinion;
    public static HumanTexture vampireMinion;
    public static HumanTexture fishianMinion;

    public static GameTextureSection MosquitoBowVisual;

    public static GameTexture[] cavelingminecart_mask;

    public static void initResources()
    {
        // Human textures
        enchantedBabyZombieArcherMinion = new HumanTexture(GameTexture.fromFile("mobs/enchantedbabyzombiearcherminion"), GameTexture.fromFile("mobs/enchantedbabyzombiearms_left"), GameTexture.fromFile("mobs/enchantedbabyzombiearms_right"));
        enchantedBabyZombieMinion = new HumanTexture(GameTexture.fromFile("mobs/enchantedbabyzombieminion"), GameTexture.fromFile("mobs/enchantedbabyzombiearms_left"), GameTexture.fromFile("mobs/enchantedbabyzombiearms_right"));
        iceWizardMinion = new HumanTexture(GameTexture.fromFile("mobs/icewizardminion"), GameTexture.fromFile("mobs/icewizardminionarms_left"), GameTexture.fromFile("mobs/icewizardminionarms_right"));
        vampireMinion = new HumanTexture(GameTexture.fromFile("mobs/vampireminion"), GameTexture.fromFile("mobs/vampireminionarms_left"), GameTexture.fromFile("mobs/vampireminionarms_right"));
        fishianMinion = new HumanTexture(GameTexture.fromFile("mobs/fishianminion"), GameTexture.fromFile("mobs/fishianminionarms_left"), GameTexture.fromFile("mobs/fishianminionarms_right"));

        // Hair
        BookFrozenMinion.texture1 = GameTexture.fromFile("mobs/frozendwarfminionhair1");
        BookFrozenMinion.texture2 = GameTexture.fromFile("mobs/frozendwarfminionhair2");
        BookFrozenMinion.texture3 = GameTexture.fromFile("mobs/frozendwarfminionhair3");

        // Minions
        HorrorCrawlingZombieMinion.texture = GameTexture.fromFile("mobs/horrorcrawlingzombieminion");
        ExplosiveSnowmanMinion.texture = GameTexture.fromFile("mobs/explosivesnowmanminion");
        BookRunicShieldMinion.texture = GameTexture.fromFile("mobs/runicshieldminion");
        GolemAmethystMinion.texture = GameTexture.fromFile("mobs/golemamethystminion");
        GolemSapphireMinion.texture = GameTexture.fromFile("mobs/golemsapphireminion");
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
        BearPolarMinion.texture = GameTexture.fromFile("mobs/bearpolarminion");
        GoblinLegMinion.texture = GameTexture.fromFile("mobs/goblinlegminion");
        FishTroutMinion.texture = GameTexture.fromFile("mobs/fishtroutminion");
        HorrorBatMinion.texture = GameTexture.fromFile("mobs/horrorbatminion");
        GolemRubyMinion.texture = GameTexture.fromFile("mobs/golemrubyminion");
        MagicAxeMinion.texture = GameTexture.fromFile("mobs/magicaxeminion");
        LampMinionGold.texture = GameTexture.fromFile("mobs/lampminiongold");
        BeeQueenMinion.texture = GameTexture.fromFile("mobs/beequeenminion");
        FishCarpMinion.texture = GameTexture.fromFile("mobs/fishcarpminion");
        CactusMinion.texture = GameTexture.fromFile("mobs/cactusminion");
        MouseMinion.texture = GameTexture.fromFile("mobs/mouseminion");
        BearMinion.texture = GameTexture.fromFile("mobs/bearminion");

        // Worm
        SandWormHeadMinion.texture = GameTexture.fromFile("mobs/sandwormminion");
        SandWormBodyMinion.texture = GameTexture.fromFile("mobs/sandwormminion");

        // Sentry
        LeafShotHeatSentry.texture = GameTexture.fromFile("mobs/leafshotheatsentry");
        LeafShotColdSentry.texture = GameTexture.fromFile("mobs/leafshotcoldsentry");
        BookMushroomSentry.texture = GameTexture.fromFile("mobs/mushroomsentry");
        IceBlossomSentry.texture = GameTexture.fromFile("mobs/iceblossomsentry");
        SunflowerSentry.texture = GameTexture.fromFile("mobs/sunflowersentry");
        HorrorSentry.texture = GameTexture.fromFile("mobs/horrorspikesentry");
        FiremoneSentry.texture = GameTexture.fromFile("mobs/firemonesentry");
        CaveglowSentry.texture = GameTexture.fromFile("mobs/caveglowsentry");
        LeafShotSentry.texture = GameTexture.fromFile("mobs/leafshotsentry");
        XmasTreeSentry.texture = GameTexture.fromFile("mobs/xmastreesentry");
        CoffinSentry.texture = GameTexture.fromFile("mobs/coffinsentry");

        // Mobs
        HorrorSpiritBossMob.texture = GameTexture.fromFile("mobs/horrorspiritbossmob");
        HorrorSpiritMob.texture = GameTexture.fromFile("mobs/horrorspiritmob");
        WoodMob.texture = GameTexture.fromFile("mobs/woodmob");

        // Boss
        RiftPortalMob.texture = GameTexture.fromFile("mobs/riftportalmob");
        RiftPortalMob.icon =  GameTexture.fromFile("mobicons/riftportalicon");

        // Mounts
        CavelingMinecartMount.texture =  GameTexture.fromFile("mobs/cavelingminecart");
        CavelingMinecart.texture =  GameTexture.fromFile("mobs/cavelingminecart");
        ChiefSummonMount.texture =  GameTexture.fromFile("mobs/chiefsummonmount");

        // Particles
        GameTexture MosquitoBowTexture = GameTexture.fromFile("particles/mosquitobowpool");
        MosquitoBowVisual = GameResources.particlesTextureGenerator.addTexture(MosquitoBowTexture);
        
        // Masks
        MouseSummonMount.texture_mask =  GameTexture.fromFile("mobs/chiefsummonmount_mask");
        ChiefSummonMount.texture_mask =  GameTexture.fromFile("mobs/chiefsummonmount_mask");
    }
}