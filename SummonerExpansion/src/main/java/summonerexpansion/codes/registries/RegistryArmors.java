package summonerexpansion.codes.registries;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;
import necesse.inventory.item.Item;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import summonerexpansion.items.armors.*;
import summonerexpansion.items.armors.armorsets.*;
import summonerexpansion.items.armors.buffs.*;
import summonerexpansion.items.armors.debuffs.*;
import summonerexpansion.items.armors.minions.*;

public class RegistryArmors
{
    public static void registerItems()
    {
        RegistryArmors.registerTier1();
        RegistryArmors.registerTier2();
        RegistryArmors.registerTier3();
        RegistryArmors.registerTier4();
        RegistryArmors.registerMinions();
    }

    public static class registerArmorSets
    {
        // T1
        public static Buff LEATHERSET;
        public static Buff COPPERSET;
        public static Buff COPPERCOOLDOWN;
        public static Buff COPPERSET_CONSECUTIVE;
        public static Buff COPPERSETFIRE;
        public static Buff BLOODPLATESET;
        public static Buff FROSTSET;
        public static Buff REDSPIDERSET;
        public static Buff REDSPIDERDOT;
        public static Buff RAINSET;
        public static Buff CLOUDSPEEDBUFF;
        public static Buff CLOUDWATERWEAK;
        // T2
        public static Buff PLAGUESET;
        public static Buff TITANIUMSET;
        public static Buff TITANIUMMELEESET;
        public static Buff TITANIUMMAGICSET;
        public static Buff TITANIUMSUMMONSET;
        public static Buff TITANIUMRANGEDSET;
        public static Buff PHARAOHSET;
        // T3
        public static Buff LAVASHARKSET;
        public static Buff LAVASHARKFRENZY;
        public static Buff LAVASHARKBLEED;
        public static Buff SHADOWSET;
        public static Buff HORRORSET;
        public static Buff AGEDSET;
        public static Buff SPIDERBRIDESET;
        public static Buff SPIDERBRIDECOOLDOWN;
        public static Buff SHARPSHOOTERSET;
        // T4
        public static Buff ARCANICSET;
        public static Buff ARCANICSETCOOLDOWN;
        public static Buff RAVENLORDSET;
        public static Buff CHEFSET;
        public static Buff SLIMESET;
        public static Buff GHOSTSET;
        public static Buff GHOSTSETCOOLDOWN;
        public static Buff SAILORSET;

        public registerArmorSets(){}
    }

    public static void registerTier1()
    {
        ItemRegistry.registerItem("leathersummonerhood", new LeatherSummonerHood(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("leathersummonersetbonus", registerArmorSets.LEATHERSET = new LeatherSummonerSetBonus());
        ItemRegistry.registerItem("copperminerhat", new CopperMinerHat(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("copperminersetbonus", registerArmorSets.COPPERSET = new CopperMinerSetBonus());
        BuffRegistry.registerBuff("coppersetcooldown", registerArmorSets.COPPERCOOLDOWN = new ShownCooldownBuff());
        BuffRegistry.registerBuff("coppersetfiredebuff", registerArmorSets.COPPERSETFIRE = new CopperSetFireDebuff());
        BuffRegistry.registerBuff("coppersetconsecutive", registerArmorSets.COPPERSET_CONSECUTIVE = new CopperSetConsecutiveBuff());
        ItemRegistry.registerItem("bloodplatemask", new BloodplateMask(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("bloodplatemasksetbonus", registerArmorSets.BLOODPLATESET = new BloodplateMaskSetBonus());
        ItemRegistry.registerItem("frostcrown", new FrostCrown(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("frostcrownsetbonus", registerArmorSets.FROSTSET = new FrostCrownSetBonus());
        ItemRegistry.registerItem("redspiderhelmet", new RedSpiderHelmet(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("redspiderchestplate", new RedSpiderChestplate(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("redspiderboots", new BaseSummonShoes((new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.40F),"redspiderboots",4,100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("redspidersetbonus", registerArmorSets.REDSPIDERSET = new RedSpiderSetBonus());
        BuffRegistry.registerBuff("redspiderdebuff", registerArmorSets.REDSPIDERDOT = new RedSpiderSetDebuff());
        ItemRegistry.registerItem("rainsummonhat", new RainSummonHat(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("rainsummoncoat", new RainSummonCoat(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("rainsummonboots", new BaseSummonShoes((new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.40F),"rainsummonboots",2,100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("rainsummonsetbonus", registerArmorSets.RAINSET = new RainSummonSetBonus());
        BuffRegistry.registerBuff("cloudspeedbuff", registerArmorSets.CLOUDSPEEDBUFF = new CloudSpeedBuff());
        BuffRegistry.registerBuff("waterweakdebuff", registerArmorSets.CLOUDWATERWEAK = new WaterWeakDebuff());
    }

    public static void registerTier2()
    {
        ItemRegistry.registerItem("summonplaguemask", new PlagueSummonerMask(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("summonplaguerobe", new PlagueSummonerRobe(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("summonplagueboots", new BaseSummonShoes((new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1, 0.22F).setUpgradedValue(10, 0.40F),"summonplagueboots",15,200, Item.Rarity.COMMON), 100, true);
        BuffRegistry.registerBuff("summonplaguesetbonus", registerArmorSets.PLAGUESET = new SummonPlagueSetBonus());
        ItemRegistry.registerItem("titaniummagichelmet", new TitaniumMagicHelmet(200, Item.Rarity.COMMON), 100, true);
        ItemRegistry.registerItem("titaniummeleehelmet", new TitaniumMeleeHelmet(200, Item.Rarity.COMMON), 100, true);
        ItemRegistry.registerItem("titaniumrangedhelmet", new TitaniumRangedHelmet(200, Item.Rarity.COMMON), 100, true);
        ItemRegistry.registerItem("titaniumsummonhelmet", new TitaniumSummonHelmet(200, Item.Rarity.COMMON), 100, true);
        ItemRegistry.registerItem("titaniumchestplate", new TitaniumChestplate(200, Item.Rarity.COMMON), 100, true);
        ItemRegistry.registerItem("titaniumboots", new BaseSummonShoes((new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.40F),"titaniumboots",4,200, Item.Rarity.COMMON), 100, true);
        BuffRegistry.registerBuff("titaniumsetbonus", registerArmorSets.TITANIUMSET = new TitaniumSetBonus());
        BuffRegistry.registerBuff("titaniummeleesetbonus", registerArmorSets.TITANIUMMELEESET = new TitaniumMeleeSetBonus());
        BuffRegistry.registerBuff("titaniummagicsetbonus", registerArmorSets.TITANIUMMAGICSET = new TitaniumMagicSetBonus());
        BuffRegistry.registerBuff("titaniumsummonsetbonus", registerArmorSets.TITANIUMSUMMONSET = new TitaniumSummonSetBonus());
        BuffRegistry.registerBuff("titaniumrangedsetbonus", registerArmorSets.TITANIUMRANGEDSET = new TitaniumRangedSetBonus());
        ItemRegistry.registerItem("pharaohsmask", new PharaohsMask(200, Item.Rarity.UNCOMMON), 100, true);
        BuffRegistry.registerBuff("pharaohsmasksetbonus", registerArmorSets.PHARAOHSET = new PharaohsMaskSetBonus());
    }

    public static void registerTier3()
    {
        ItemRegistry.registerItem("sharklavahelmet", new SharkLavaHelmet(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("sharklavachestplate", new SharkLavaChestplate(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("sharklavaboots", new BaseSummonShoes((new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.40F),"sharklavaboots",4,400, Item.Rarity.COMMON), 200, true);
        BuffRegistry.registerBuff("sharklavasetbonus", registerArmorSets.LAVASHARKSET = new SharkLavaSetBonus());
        BuffRegistry.registerBuff("sharklavafrenzybuff", registerArmorSets.LAVASHARKFRENZY = new SharkLavaFrenzyBuff());
        BuffRegistry.registerBuff("sharklavableeddebuff", registerArmorSets.LAVASHARKBLEED = new SharkLavaBleedDebuff());
        ItemRegistry.registerItem("shadowhelmet", new ShadowHelmet(400, Item.Rarity.RARE), 200, true);
        BuffRegistry.registerBuff("shadowhelmetsetbonus", registerArmorSets.SHADOWSET = new ShadowHelmetSetBonus());
        ItemRegistry.registerItem("shadowhorrorhood", new ShadowHorrorHood(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("shadowhorrormantle", new ShadowHorrorMantle(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("shadowhorrorboots", new BaseSummonShoes((new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.40F),"shadowhorrorboots",15,400, Item.Rarity.COMMON), 200, true);
        BuffRegistry.registerBuff("shadowhorrorsetbonus", registerArmorSets.HORRORSET = new ShadowHorrorSetBonus());
        ItemRegistry.registerItem("agedsummonerhelmet", new AgedSummonerHelmet(400, Item.Rarity.RARE), 200, true);
        BuffRegistry.registerBuff("agedsummonersetbonus", registerArmorSets.AGEDSET = new AgedSummonerSetBonus());
        ItemRegistry.registerItem("spiderbridehelmet", new SpiderBrideHelmet(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("spiderbridechest", new SpiderBrideChest(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("spiderbrideboots", new BaseSummonShoes((new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.40F),"spiderbrideboots",17,400, Item.Rarity.COMMON), 200, true);
        BuffRegistry.registerBuff("spiderbridesetbonus", registerArmorSets.SPIDERBRIDESET = new SpiderBrideSetBonus());
        BuffRegistry.registerBuff("spiderbridecooldown", registerArmorSets.SPIDERBRIDECOOLDOWN = new ShownCooldownBuff());
        ItemRegistry.registerItem("sharpshootersummonhat", new SharpshooterSummonHat(400, Item.Rarity.RARE), 200, true);
        BuffRegistry.registerBuff("sharpshootersummonsetbonus", registerArmorSets.SHARPSHOOTERSET = new SharpshooterSummonSetBonus());
    }

    public static void registerTier4()
    {
        ItemRegistry.registerItem("arcanicsummonhelmet", new ArcanicSummonerHelmet(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("arcanicsummonsetbonus", registerArmorSets.ARCANICSET = new ArcanicSummonerSetBonus());
        BuffRegistry.registerBuff("arcanicsummoncooldown", registerArmorSets.ARCANICSETCOOLDOWN = new ShownCooldownBuff());
        ItemRegistry.registerItem("ravenlordsummonmask", new RavenlordSummonerMask(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("ravenlordsummonsetbonus", registerArmorSets.RAVENLORDSET = new RavenlordSummonerSetBonus());
        ItemRegistry.registerItem("chefsummonerhat", new ChefSummonerHat(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("chefsummonerhatsetbonus", registerArmorSets.CHEFSET = new ChefSummonerHatSetBonus());
        ItemRegistry.registerItem("slimehood", new SlimeHood(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("slimehoodsetbonus", registerArmorSets.SLIMESET = new SlimeHoodSetBonus());
        ItemRegistry.registerItem("ghostcaptainshat", new GhostCaptainHat(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("ghostcaptainsshirt", new GhostCaptainShirt(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("ghostcaptainsboots", new BaseSummonShoes((new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1, 0.25F).setUpgradedValue(10, 0.40F),"ghostcaptainsboots",16,800, Item.Rarity.COMMON), 400, true);
        BuffRegistry.registerBuff("ghostcaptainssetbonus", registerArmorSets.GHOSTSET = new GhostCaptainSetBonus());
        BuffRegistry.registerBuff("ghostcaptainscooldown", registerArmorSets.GHOSTSETCOOLDOWN = new ShownCooldownBuff());
        ItemRegistry.registerItem("sailorsummonhat", new SailorSummonHat(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("sailorsummonshirt", new SailorSummonShirt(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("sailorsummonshoes", new BaseSummonShoes((new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1, 0.25F).setUpgradedValue(10, 0.40F),"sailorsummonshoes",20,800, Item.Rarity.COMMON), 400, true);
        BuffRegistry.registerBuff("sailorsummonsetbonus", registerArmorSets.SAILORSET = new SailorSummonSetBonus());
    }

    public static void registerMinions()
    {
        // T1
        MobRegistry.registerMob("setbatminion", SetBatMinion.class, false);
        MobRegistry.registerMob("setcloudminion", SetCloudMinion.class, false);
        BuffRegistry.registerBuff("summonedcloudminionbuff", new SummonedCloudMinionBuff());
        // T2
        MobRegistry.registerMob("setmouseminion", SetMouseMinion.class, false);
        MobRegistry.registerMob("settitaniumsummonminion", SetTitaniumSummonMinion.class, false);
        MobRegistry.registerMob("settitaniumrangedminion", SetTitaniumRangedMinion.class, false);
        MobRegistry.registerMob("settitaniummagicminion", SetTitaniumMagicMinion.class, false);
        MobRegistry.registerMob("settitaniummeleeminion", SetTitaniumMeleeMinion.class, false);
        BuffRegistry.registerBuff("summonedtitaniumminionbuff", new SummonedTitaniumMinionBuff());
        MobRegistry.registerMob("setlocustminion", SetLocustMinion.class, false);
        // T3
        MobRegistry.registerMob("sethorrorbabyminion", SetHorrorBabyMinion.class, false);
        MobRegistry.registerMob("setspiderbrideminion", SetSpiderBrideMinion.class, false);
        MobRegistry.registerMob("setagedchampionminion", SetAgedChampionMinion.class, false);
        BuffRegistry.registerBuff("summonedagedchampionminionbuff", new SummonedAgedChampionMinionBuff());
        MobRegistry.registerMob("setsharpshooterminion", SetSharpshooterMinion.class, false);
        BuffRegistry.registerBuff("summonedsharpshooterbuff", new SummonedSharpshooterMinionBuff());
        // T4
        MobRegistry.registerMob("setghostcaptainsminion", SetGhostCaptainMinion.class, false);
        MobRegistry.registerMob("setsailorminion", SetSailorMinion.class, false);
        MobRegistry.registerMob("setslimewarriorminion", SetSlimeWarriorMinion.class, false);
        MobRegistry.registerMob("setslimemageminion", SetSlimeMageMinion.class, false);
        MobRegistry.registerMob("setravenlordminion", SetRavenlordMinion.class, false);
        MobRegistry.registerMob("setchefminion", SetChefMinion.class, false);
        MobRegistry.registerMob("setarcanicpylonsentry", SetArcanicPylonSentry.class, false);
    }
}