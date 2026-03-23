package summonerexpansion.codes.registries;

import necesse.entity.mobs.HumanTexture;
import necesse.entity.mobs.MobTexture;
import necesse.gfx.gameTexture.GameTexture;

import static necesse.engine.registries.MobRegistry.Textures.fromFiles;

public class RegistryMinionTextures
{
    // Human textures
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
    public static GameTexture butterflyRedMinion;
    public static GameTexture butterflyGreenMinion;
    public static GameTexture butterflyBlueMinion;
    public static GameTexture bookRunicShieldMinion;
    public static GameTexture bookMagmaSlimeMinion;
    public static GameTexture golemAmethystMinion;
    public static GameTexture golemEmeraldMinion;
    public static GameTexture golemRubyMinion;
    public static GameTexture golemTopazMinion;
    public static GameTexture golemSapphireMinion;
    public static GameTexture lampMinionCopper;
    public static GameTexture lampMinionGold;
    public static GameTexture lampMinionDungeon;
    public static GameTexture lampMinionTungsten;
    public static GameTexture lampMinionCastle;
    public static GameTexture woodPickMinion;
    public static GameTexture woodAxeMinion;
    public static GameTexture copperPickMinion;
    public static GameTexture copperAxeMinion;
    public static GameTexture ironPickMinion;
    public static GameTexture ironAxeMinion;
    public static GameTexture goldPickMinion;
    public static GameTexture goldAxeMinion;
    public static GameTexture explosiveSnowmanMinion;
    public static GameTexture spiritGhoulMinion;
    public static GameTexture beeQueenMinion;
    public static GameTexture cactusMinion;
    public static GameTexture bearMinion;
    public static GameTexture bearPolarMinion;
    public static GameTexture ramMinion;
    public static GameTexture fishMackerelMinion;
    public static GameTexture fishHerringMinion;
    public static GameTexture fishSalmonMinion;
    public static GameTexture fishTroutMinion;
    public static GameTexture fishCarpMinion;
    public static GameTexture goblinHeadMinion;
    public static GameTexture goblinChestMinion;
    public static GameTexture goblinLegMinion;
    public static GameTexture horrorCrawlingZombieMinion;
    public static GameTexture horrorBullMinion;
    public static GameTexture horrorWolfMinion;
    public static GameTexture planetMarsMinion;
    public static GameTexture planetNeptuneMinion;
    public static GameTexture planetSaturnMinion;
    public static GameTexture planetVenusMinion;
    public static GameTexture pineWoodMinion;
    public static GameTexture bookMushroomMinion;
    public static GameTexture sandWormHeadMinion;
    public static GameTexture sandWormBodyMinion;
    public static GameTexture fallenDragonHeadMinion;
    public static GameTexture fallenDragonBodyMinion;
    public static GameTexture leafShotSentry;
    public static GameTexture leafShotHeatSentry;
    public static GameTexture leafShotColdSentry;
    public static GameTexture bookMushroomSentry;
    public static GameTexture vampireCoffinSentry;
    public static GameTexture caveglowSentry;
    public static GameTexture xmasTreeSentry;
    public static GameTexture coffebeamSentry;
    public static GameTexture horrorSentry;
    public static GameTexture sunflowerSentry;
    public static GameTexture iceBlossomSentry;
    public static GameTexture firemoneSentry;

    // Armor
    public static GameTexture setCloudMinion;
    public static GameTexture setMouseMinion;
    public static GameTexture setBatMinion;
    public static GameTexture setSpiderBrideMinion;
    public static GameTexture setHorrorBabyMinion;
    public static GameTexture setArcanicPylonSentry;
    public static GameTexture setTitaniumSummonMinion;
    public static GameTexture setTitaniumMagicMinion;
    public static GameTexture setTitaniumRangedMinion;
    public static GameTexture setTitaniumMeleeMinion;
    public static GameTexture setTitaniumMeleeMinion_shadow;

    // Trinket
    public static GameTexture trinketHorrorBatMinion;
    public static GameTexture petTeaPotMinion;

    // Mount
    public static GameTexture cavelingMinecartMount;

    public static void initResources()
    {
        // Human textures
        enchantedBabyZombieArcherMinion = new HumanTexture(GameTexture.fromFile("mobs/minions/enchantedbabyzombiearcherminion"), GameTexture.fromFile("mobs/minions/enchantedbabyzombiearms_left"), GameTexture.fromFile("mobs/minions/enchantedbabyzombiearms_right"));
        enchantedBabyZombieMinion = new HumanTexture(GameTexture.fromFile("mobs/minions/enchantedbabyzombieminion"), GameTexture.fromFile("mobs/minions/enchantedbabyzombiearms_left"), GameTexture.fromFile("mobs/minions/enchantedbabyzombiearms_right"));
        ancestorKnightMinion = new HumanTexture(GameTexture.fromFile("mobs/minions/melee/ancestorknightminion"), GameTexture.fromFile("mobs/minions/melee/ancestorknightminionarms_left"), GameTexture.fromFile("mobs/minions/melee/ancestorknightminionarms_right"));
        mummySummonMinion = new HumanTexture(GameTexture.fromFile("mobs/minions/trinket/mummysummonminion"), GameTexture.fromFile("mobs/minions/trinket/mummysummonminionarms_left"), GameTexture.fromFile("mobs/minions/trinket/mummysummonminionarms_right"));
        mummyMagicMinion = new HumanTexture(GameTexture.fromFile("mobs/minions/trinket/mummymagicminion"), GameTexture.fromFile("mobs/minions/trinket/mummymagicminionarms_left"), GameTexture.fromFile("mobs/minions/trinket/mummymagicminionarms_right"));
        iceWizardMinion = new HumanTexture(GameTexture.fromFile("mobs/minions/icewizardminion"), GameTexture.fromFile("mobs/minions/icewizardminionarms_left"), GameTexture.fromFile("mobs/minions/icewizardminionarms_right"));
        ninjaMinion = new HumanTexture(GameTexture.fromFile("mobs/minions/ranged/yangninjaminion"), GameTexture.fromFile("mobs/minions/ranged/yangninjaminionarms_left"), GameTexture.fromFile("mobs/minions/ranged/yangninjaminionarms_right"));
        vampireMinion = new HumanTexture(GameTexture.fromFile("mobs/minions/vampireminion"), GameTexture.fromFile("mobs/minions/vampireminionarms_left"), GameTexture.fromFile("mobs/minions/vampireminionarms_right"));
        fishianMinion = new HumanTexture(GameTexture.fromFile("mobs/minions/melee/fishianminion"), GameTexture.fromFile("mobs/minions/melee/fishianminionarms_left"), GameTexture.fromFile("mobs/minions/melee/fishianminionarms_right"));

        // Summons
        bookRunicShieldMinion = GameTexture.fromFile("mobs/minions/runicshieldminion");
        bookMagmaSlimeMinion = GameTexture.fromFile("mobs/minions/magmaslimeminion");
        golemAmethystMinion = GameTexture.fromFile("mobs/minions/golemamethystminion");
        golemSapphireMinion = GameTexture.fromFile("mobs/minions/golemsapphireminion");
        golemEmeraldMinion = GameTexture.fromFile("mobs/minions/golememeraldminion");
        golemTopazMinion = GameTexture.fromFile("mobs/minions/golemtopazminion");
        golemRubyMinion = GameTexture.fromFile("mobs/minions/golemrubyminion");
        lampMinionCopper = GameTexture.fromFile("mobs/minions/lampminioncopper");
        lampMinionGold = GameTexture.fromFile("mobs/minions/lampminiongold");
        lampMinionDungeon = GameTexture.fromFile("mobs/minions/lampminiondungeon");
        lampMinionTungsten = GameTexture.fromFile("mobs/minions/lampminiontungsten");
        lampMinionCastle = GameTexture.fromFile("mobs/minions/lampminioncastle");
        woodPickMinion = GameTexture.fromFile("mobs/minions/woodpickminion");
        woodAxeMinion = GameTexture.fromFile("mobs/minions/woodaxeminion");
        copperPickMinion = GameTexture.fromFile("mobs/minions/copperpickminion");
        copperAxeMinion = GameTexture.fromFile("mobs/minions/copperaxeminion");
        ironPickMinion = GameTexture.fromFile("mobs/minions/ironpickminion");
        ironAxeMinion = GameTexture.fromFile("mobs/minions/ironaxeminion");
        goldPickMinion = GameTexture.fromFile("mobs/minions/goldpickminion");
        goldAxeMinion = GameTexture.fromFile("mobs/minions/goldaxeminion");
        explosiveSnowmanMinion = GameTexture.fromFile("mobs/minions/explosivesnowmanminion");
        spiritGhoulMinion = GameTexture.fromFile("mobs/minions/spiritghoulminion");
        butterflyGreenMinion = GameTexture.fromFile("mobs/minions/butterflygreenminion");
        butterflyBlueMinion = GameTexture.fromFile("mobs/minions/butterflyblueminion");
        butterflyRedMinion = GameTexture.fromFile("mobs/minions/butterflyredminion");
        beeQueenMinion = GameTexture.fromFile("mobs/minions/beequeenminion");
        cactusMinion = GameTexture.fromFile("mobs/minions/cactusminion");
        bearPolarMinion = GameTexture.fromFile("mobs/minions/bearpolarminion");
        bearMinion = GameTexture.fromFile("mobs/minions/bearminion");
        bookMushroomMinion = GameTexture.fromFile("mobs/minions/mushroomminion");

        // Melee
        ramMinion = GameTexture.fromFile("mobs/minions/melee/ramminion");
        fishMackerelMinion = GameTexture.fromFile("mobs/minions/melee/fishmackerelminion");
        fishHerringMinion = GameTexture.fromFile("mobs/minions/melee/fishherringminion");
        fishSalmonMinion = GameTexture.fromFile("mobs/minions/melee/fishsalmonminion");
        fishTroutMinion = GameTexture.fromFile("mobs/minions/melee/fishtroutminion");
        fishCarpMinion = GameTexture.fromFile("mobs/minions/melee/fishcarpminion");
        goblinHeadMinion = GameTexture.fromFile("mobs/minions/melee/goblinheadminion");
        goblinChestMinion = GameTexture.fromFile("mobs/minions/melee/goblinchestminion");
        goblinLegMinion = GameTexture.fromFile("mobs/minions/melee/goblinlegminion");
        horrorCrawlingZombieMinion = GameTexture.fromFile("mobs/minions/melee/horrorcrawlingzombieminion");
        horrorBullMinion = GameTexture.fromFile("mobs/minions/melee/horrorbullminion");
        horrorWolfMinion = GameTexture.fromFile("mobs/minions/melee/horrorwolfminion");
        planetMarsMinion = GameTexture.fromFile("mobs/minions/melee/planetmarsproj");
        planetNeptuneMinion = GameTexture.fromFile("mobs/minions/melee/planetneptuneproj");
        planetSaturnMinion = GameTexture.fromFile("mobs/minions/melee/planetsaturnproj");
        planetVenusMinion = GameTexture.fromFile("mobs/minions/melee/planetvenusproj");

        // Magic
        pineWoodMinion = GameTexture.fromFile("mobs/minions/magic/pinewoodminion");

        // Ranged

        // Sentry
        leafShotHeatSentry = GameTexture.fromFile("mobs/minions/sentry/leafshotheatsentry");
        leafShotColdSentry = GameTexture.fromFile("mobs/minions/sentry/leafshotcoldsentry");
        leafShotSentry = GameTexture.fromFile("mobs/minions/sentry/leafshotsentry");
        bookMushroomSentry = GameTexture.fromFile("mobs/minions/sentry/mushroomsentry");
        vampireCoffinSentry = GameTexture.fromFile("mobs/minions/sentry/coffinsentry");
        caveglowSentry = GameTexture.fromFile("mobs/minions/sentry/caveglowsentry");
        xmasTreeSentry = GameTexture.fromFile("mobs/minions/sentry/xmastreesentry");
        coffebeamSentry = GameTexture.fromFile("mobs/minions/sentry/coffebeamsentry");
        sunflowerSentry = GameTexture.fromFile("mobs/minions/sentry/sunflowersentry");
        iceBlossomSentry = GameTexture.fromFile("mobs/minions/sentry/iceblossomsentry");
        firemoneSentry = GameTexture.fromFile("mobs/minions/sentry/firemonesentry");

        horrorSentry = GameTexture.fromFile("mobs/minions/melee/horrorspikesentry");

        // Worm
        sandWormHeadMinion = GameTexture.fromFile("mobs/minions/worm/sandwormminion");
        sandWormBodyMinion = GameTexture.fromFile("mobs/minions/worm/sandwormminion");
        fallenDragonHeadMinion = GameTexture.fromFile("mobs/minions/worm/fallendragonminion");
        fallenDragonBodyMinion = GameTexture.fromFile("mobs/minions/worm/fallendragonminion");

        // Armor
        setArcanicPylonSentry = GameTexture.fromFile("mobs/minions/armor/setarcanicpylonsentry");
        setSpiderBrideMinion = GameTexture.fromFile("mobs/minions/armor/setspiderbrideminion");
        setHorrorBabyMinion = GameTexture.fromFile("mobs/minions/armor/sethorrorbabyminion");
        setCloudMinion = GameTexture.fromFile("mobs/minions/armor/setcloudminion");
        setMouseMinion = GameTexture.fromFile("mobs/minions/armor/setmouseminion");
        setBatMinion = GameTexture.fromFile("mobs/minions/armor/setbatminion");
        setTitaniumSummonMinion = GameTexture.fromFile("mobs/minions/armor/settitaniumsummonminion");
        setTitaniumRangedMinion = GameTexture.fromFile("mobs/minions/armor/settitaniumbowminion");
        setTitaniumMagicMinion = GameTexture.fromFile("mobs/minions/armor/settitaniumstaffminion");
        setTitaniumMeleeMinion = GameTexture.fromFile("mobs/minions/armor/settitaniummeleeminion");
        setTitaniumMeleeMinion_shadow = GameTexture.fromFile("mobs/minions/armor/settitaniummeleeminion_shadow");

        // Trinket
        trinketHorrorBatMinion = GameTexture.fromFile("mobs/minions/trinket/horrorbatminion");
        petTeaPotMinion = GameTexture.fromFile("mobs/minions/trinket/teapotminion");

        // Mount
        cavelingMinecartMount = GameTexture.fromFile("mobs/mount/cavelingminecart");
    }
}