package summonerexpansion.codes.registry;

import necesse.entity.mobs.HumanTexture;
import necesse.entity.mobs.MobTexture;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import summonerexpansion.items.equips.mountminions.CavelingMinecartMount;
import summonerexpansion.mobs.summonminions.*;
import summonerexpansion.mobs.summonminions.magicminions.*;
import summonerexpansion.mobs.summonminions.meleeminions.*;
import summonerexpansion.mobs.summonminions.setminions.*;
import summonerexpansion.mobs.summonminions.trinketminions.*;
import summonerexpansion.mobs.summonminions.wormminions.*;
import summonerexpansion.mobs.summonmobs.*;

import static necesse.gfx.GameResources.particlesTextureGenerator;

public class SummonerTextures
{
    // Human Minion
    public static HumanTexture enchantedBabyZombieArcherMinion;
    public static HumanTexture enchantedBabyZombieMinion;
    public static HumanTexture iceWizardMinion;
    public static HumanTexture vampireMinion;
    public static HumanTexture fishianMinion;
    public static HumanTexture ninjaMinion;
    public static HumanTexture mummySummonMinion;
    public static HumanTexture mummyMagicMinion;
    public static HumanTexture ancestorKnightMinion;

    // Minion
    public static GameTexture pineWoodMinion;
    public static GameTexture titaniumSummonMinion;
    public static GameTexture titaniumMagicMinion;
    public static GameTexture titaniumRangedMinion;
    public static GameTexture titaniumMeleeMinion;
    public static GameTexture butterflyRedMinion;
    public static GameTexture butterflyGreenMinion;
    public static GameTexture butterflyBlueMinion;

    // Sentry
    public static GameTexture coffebeamSentry;

    // Shadows
    public static GameTexture titaniumMeleeMinion_shadow;

    // Particles and effects
    public static GameTextureSection pumpkinExplosionParticles;
    public static GameTextureSection titaniumMeleeParticle;
    public static GameTextureSection HorrorWaveParticles;
    public static GameTextureSection MosquitoBowVisual;
    public static GameTextureSection healGlyphParticles;
    public static GameTexture healGlyphParticle;
    public static GameTexture titaniumLightningGlyph;
    public static GameTexture SpiritGhoulPool;
    public static GameTexture PineWoodSpike;
    public static GameTexture thornSpike;

    public static MobTexture honeyBeeGuard;

    public static void initResources()
    {
        // Human textures
        enchantedBabyZombieArcherMinion = new HumanTexture(GameTexture.fromFile("mobs/enchantedbabyzombiearcherminion"), GameTexture.fromFile("mobs/enchantedbabyzombiearms_left"), GameTexture.fromFile("mobs/enchantedbabyzombiearms_right"));
        enchantedBabyZombieMinion = new HumanTexture(GameTexture.fromFile("mobs/enchantedbabyzombieminion"), GameTexture.fromFile("mobs/enchantedbabyzombiearms_left"), GameTexture.fromFile("mobs/enchantedbabyzombiearms_right"));
        ancestorKnightMinion = new HumanTexture(GameTexture.fromFile("mobs/ancestorknightminion"), GameTexture.fromFile("mobs/ancestorknightminionarms_left"), GameTexture.fromFile("mobs/ancestorknightminionarms_right"));
        mummySummonMinion = new HumanTexture(GameTexture.fromFile("mobs/mummysummonminion"), GameTexture.fromFile("mobs/mummysummonminionarms_left"), GameTexture.fromFile("mobs/mummysummonminionarms_right"));
        mummyMagicMinion = new HumanTexture(GameTexture.fromFile("mobs/mummymagicminion"), GameTexture.fromFile("mobs/mummymagicminionarms_left"), GameTexture.fromFile("mobs/mummymagicminionarms_right"));
        iceWizardMinion = new HumanTexture(GameTexture.fromFile("mobs/icewizardminion"), GameTexture.fromFile("mobs/icewizardminionarms_left"), GameTexture.fromFile("mobs/icewizardminionarms_right"));
        ninjaMinion = new HumanTexture(GameTexture.fromFile("mobs/yangninjaminion"), GameTexture.fromFile("mobs/yangninjaminionarms_left"), GameTexture.fromFile("mobs/yangninjaminionarms_right"));
        vampireMinion = new HumanTexture(GameTexture.fromFile("mobs/vampireminion"), GameTexture.fromFile("mobs/vampireminionarms_left"), GameTexture.fromFile("mobs/vampireminionarms_right"));
        fishianMinion = new HumanTexture(GameTexture.fromFile("mobs/fishianminion"), GameTexture.fromFile("mobs/fishianminionarms_left"), GameTexture.fromFile("mobs/fishianminionarms_right"));

        // Hair

        // Minions
        BookRunicShieldMinion.texture = GameTexture.fromFile("mobs/runicshieldminion");
        BookMagmaSlimeMinion.texture = GameTexture.fromFile("mobs/magmaslimeminion");
        BookMushroomMinion.texture = GameTexture.fromFile("mobs/mushroomminion");

        GolemAmethystMinion.texture = GameTexture.fromFile("mobs/golemamethystminion");
        GolemSapphireMinion.texture = GameTexture.fromFile("mobs/golemsapphireminion");
        GolemEmeraldMinion.texture = GameTexture.fromFile("mobs/golememeraldminion");
        GolemTopazMinion.texture = GameTexture.fromFile("mobs/golemtopazminion");
        GolemRubyMinion.texture = GameTexture.fromFile("mobs/golemrubyminion");

        FishMackerelMinion.texture = GameTexture.fromFile("mobs/fishmackerelminion");
        FishHerringMinion.texture = GameTexture.fromFile("mobs/fishherringminion");
        FishSalmonMinion.texture = GameTexture.fromFile("mobs/fishsalmonminion");
        FishTroutMinion.texture = GameTexture.fromFile("mobs/fishtroutminion");
        FishCarpMinion.texture = GameTexture.fromFile("mobs/fishcarpminion");

        LampMinionCopper.texture = GameTexture.fromFile("mobs/lampminioncopper");
        LampMinionGold.texture = GameTexture.fromFile("mobs/lampminiongold");
        LampMinionDungeon.texture = GameTexture.fromFile("mobs/lampminiondungeon");
        LampMinionTungsten.texture = GameTexture.fromFile("mobs/lampminiontungsten");
        LampMinionCastle.texture = GameTexture.fromFile("mobs/lampminioncastle");

        WoodPickMinion.texture = GameTexture.fromFile("mobs/woodpickminion");
        WoodAxeMinion.texture = GameTexture.fromFile("mobs/woodaxeminion");
        CopperPickMinion.texture = GameTexture.fromFile("mobs/copperpickminion");
        CopperAxeMinion.texture = GameTexture.fromFile("mobs/copperaxeminion");
        IronPickMinion.texture = GameTexture.fromFile("mobs/ironpickminion");
        IronAxeMinion.texture = GameTexture.fromFile("mobs/ironaxeminion");
        GoldPickMinion.texture = GameTexture.fromFile("mobs/goldpickminion");
        GoldAxeMinion.texture = GameTexture.fromFile("mobs/goldaxeminion");

        HorrorCrawlingZombieMinion.texture = GameTexture.fromFile("mobs/horrorcrawlingzombieminion");
        HorrorBullMinion.texture = GameTexture.fromFile("mobs/horrorbullminion");
        HorrorWolfMinion.texture = GameTexture.fromFile("mobs/horrorwolfminion");

        PlanetMarsMinion.texture = GameTexture.fromFile("mobs/planetmarsproj");
        PlanetNeptuneMinion.texture = GameTexture.fromFile("mobs/planetneptuneproj");
        PlanetSaturnMinion.texture = GameTexture.fromFile("mobs/planetsaturnproj");
        PlanetVenusMinion.texture = GameTexture.fromFile("mobs/planetvenusproj");

        ExplosiveSnowmanMinion.texture = GameTexture.fromFile("mobs/explosivesnowmanminion");
        GoblinChestMinion.texture = GameTexture.fromFile("mobs/goblinchestminion");
        SpiritGhoulMinion.texture = GameTexture.fromFile("mobs/spiritghoulminion");
        GoblinHeadMinion.texture = GameTexture.fromFile("mobs/goblinheadminion");
        BearPolarMinion.texture = GameTexture.fromFile("mobs/bearpolarminion");
        GoblinLegMinion.texture = GameTexture.fromFile("mobs/goblinlegminion");
        BeeQueenMinion.texture = GameTexture.fromFile("mobs/beequeenminion");
        CactusMinion.texture = GameTexture.fromFile("mobs/cactusminion");
        BearMinion.texture = GameTexture.fromFile("mobs/bearminion");
        pineWoodMinion = GameTexture.fromFile("mobs/pinewoodminion");
        butterflyRedMinion = GameTexture.fromFile("mobs/butterflyredminion");
        butterflyGreenMinion = GameTexture.fromFile("mobs/butterflygreenminion");
        butterflyBlueMinion = GameTexture.fromFile("mobs/butterflyblueminion");

        // Worm
        SandWormHeadMinion.texture = GameTexture.fromFile("mobs/sandwormminion");
        SandWormBodyMinion.texture = GameTexture.fromFile("mobs/sandwormminion");
        FallenDragonHeadMinion.texture = GameTexture.fromFile("mobs/fallendragonminion");
        FallenDragonBodyMinion.texture = GameTexture.fromFile("mobs/fallendragonminion");

        // Sentry
        LeafShotHeatSentry.texture = GameTexture.fromFile("mobs/leafshotheatsentry");
        LeafShotColdSentry.texture = GameTexture.fromFile("mobs/leafshotcoldsentry");
        BookMushroomSentry.texture = GameTexture.fromFile("mobs/mushroomsentry");
        IceBlossomSentry.texture = GameTexture.fromFile("mobs/iceblossomsentry");
        VampireCoffinSentry.texture = GameTexture.fromFile("mobs/coffinsentry");
        SunflowerSentry.texture = GameTexture.fromFile("mobs/sunflowersentry");
        HorrorSentry.texture = GameTexture.fromFile("mobs/horrorspikesentry");
        FiremoneSentry.texture = GameTexture.fromFile("mobs/firemonesentry");
        CaveglowSentry.texture = GameTexture.fromFile("mobs/caveglowsentry");
        LeafShotSentry.texture = GameTexture.fromFile("mobs/leafshotsentry");
        XmasTreeSentry.texture = GameTexture.fromFile("mobs/xmastreesentry");
        coffebeamSentry = GameTexture.fromFile("mobs/coffebeamsentry");

        // Armor
        SetArcanicPylonSentry.texture = GameTexture.fromFile("mobs/arcanicpylonsentry");
        SetSpiderBrideMinion.texture = GameTexture.fromFile("mobs/spiderbrideminion");
        SetHorrorBabyMinion.texture = GameTexture.fromFile("mobs/horrorbabyminion");
        SetCloudMinion.texture = GameTexture.fromFile("mobs/cloudminion");
        SetMouseMinion.texture = GameTexture.fromFile("mobs/mouseminion");
        SetBatMinion.texture = GameTexture.fromFile("mobs/setbatminion");

        titaniumSummonMinion = GameTexture.fromFile("mobs/settitaniumsummonminion");
        titaniumRangedMinion = GameTexture.fromFile("mobs/settitaniumbowminion");
        titaniumMagicMinion = GameTexture.fromFile("mobs/settitaniumstaffminion");
        titaniumMeleeMinion = GameTexture.fromFile("mobs/settitaniummeleeminion");
        titaniumMeleeMinion_shadow = GameTexture.fromFile("mobs/settitaniummeleeminion_shadow");
        titaniumMeleeParticle = particlesTextureGenerator.addTexture(GameTexture.fromFile("particles/settitaniummeleeminion"));

        // Trinket
        TrinketHorrorBatMinion.texture = GameTexture.fromFile("mobs/horrorbatminion");
        PetTeaPotMinion.texture = GameTexture.fromFile("mobs/teapotminion");

        // Mobs
        HorrorSpiritBossMob.texture = GameTexture.fromFile("mobs/horrorspiritbossmob");
        HorrorSpiritMob.texture = GameTexture.fromFile("mobs/horrorspiritmob");
        WoodMob.texture = GameTexture.fromFile("mobs/woodmob");
        HoneyBeeGuardMob.texture = GameTexture.fromFile("mobs/honeybeeguardmob");

        // Boss
        BossRiftPortalMob.texture = GameTexture.fromFile("mobs/riftportalmob");
        BossRiftPortalMob.icon =  GameTexture.fromFile("mobicons/riftportalicon");

        // Particle
        healGlyphParticles = particlesTextureGenerator.addTexture(GameTexture.fromFile("particles/healglyphparticles"));
        healGlyphParticle = GameTexture.fromFile("particles/healglyph");
        pumpkinExplosionParticles = particlesTextureGenerator.addTexture(GameTexture.fromFile("particles/pumpkinparticles"));
        HorrorWaveParticles = particlesTextureGenerator.addTexture(GameTexture.fromFile("projectiles/horrorwaveproj"));
        titaniumLightningGlyph = GameTexture.fromFile("particles/titaniumlightningglyph");
        GameTexture MosquitoBowTexture = GameTexture.fromFile("particles/mosquitobowpool");
        MosquitoBowVisual = particlesTextureGenerator.addTexture(MosquitoBowTexture);
        SpiritGhoulPool = GameTexture.fromFile("particles/spiritghoulpool");
        PineWoodSpike = GameTexture.fromFile("particles/pinestaffspikes");
        thornSpike = GameTexture.fromFile("particles/thornspikes");

        // Mount
        CavelingMinecartMount.texture = GameTexture.fromFile("mobs/cavelingminecart");

        // Mask
    }
}