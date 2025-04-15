package summonerexpansion;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;
import necesse.entity.mobs.buffs.staticBuffs.ShownItemCooldownBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.SimpleTrinketBuff;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.inventory.item.Item;
import necesse.inventory.item.miscItem.BannerItem;
import necesse.inventory.item.trinketItem.SimpleTrinketItem;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.recipe.Tech;
import necesse.level.maps.biomes.Biome;
import summonerexpansion.summonarmor.*;
import summonerexpansion.summonbuffs.*;
import summonerexpansion.summondebuffs.*;
import summonerexpansion.summonmaterials.*;
import summonerexpansion.summonminions.*;
import summonerexpansion.summonmobs.*;
import summonerexpansion.summonobjects.*;
import summonerexpansion.summonothers.*;
import summonerexpansion.summonpotions.*;
import summonerexpansion.summonprojs.*;
import summonerexpansion.summonsetbonus.*;
import summonerexpansion.summontrinket.*;
import summonerexpansion.summontrinketbuffs.*;
import summonerexpansion.summonweapons.*;
import summonerexpansion.summonbannerbuffs.*;
import summonerexpansion.summonparticles.*;
import summonerexpansion.summonmounts.*;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static necesse.engine.registries.RecipeTechRegistry.registerTech;

@ModEntry
public class SummonerExpansion
{
    public static Tech SUMMONTABLECRAFT;
    public static Tech SUMMONTABLECRAFT2;
    public static Tech SUMMONTABLECRAFT3;
    public static Tech SUMMONTABLECRAFT4;
    public static Tech SUMMONBOOKCRAFT;
    
    public static GameTextureSection MosquitoBowVisual;

    public void init()
    {
        SummonerBuffs.registerSummonBuffs();

        // Techs
        SUMMONTABLECRAFT = registerTech("summontablecraft", "summontablecraftitem");
        SUMMONTABLECRAFT2 = registerTech("summontablecraft2", "summontablecraftitem2");
        SUMMONTABLECRAFT3 = registerTech("summontablecraft3", "summontablecraftitem3");
        SUMMONTABLECRAFT4 = registerTech("summontablecraft4", "summontablecraftitem4");
        SUMMONBOOKCRAFT = registerTech("summonbookcraft", "summonbookcraftitem");

        // Events
        LevelEventRegistry.registerEvent("snowmanexplosionlevelevent", SnowmanExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("mosquitobowevent", MosquitoBowEvent.class);

        // Objects
        ObjectRegistry.registerObject("summoningbookshelf", new SummoningBookshelf(), 50, true);
        ObjectRegistry.registerObject("summoningtable", new SummoningTable(), 50, true);
        SummoningTableDuo.registerSummoningTable();
        DemonicSummoningTableDuo.registerDemonicSummoningTable();
        TungstenSummoningTableDuo.registerTungstenSummoningTable();
        FallenSummoningTableDuo.registerFallenSummoningTable();

            // --Weapons T1--

        // Minion
        ItemRegistry.registerItem("enchantedbrainonastick", new EnchantedBrainOnAStick(), 50, true);
        ItemRegistry.registerItem("magiccopperlamp", new MagicCopperLamp(), 50, true);
        ItemRegistry.registerItem("royalhive", new RoyalHive(), 50, true);
        ItemRegistry.registerItem("polarhead", new PolarHead(), 50, true);
        ItemRegistry.registerItem("bearhead", new BearHead(), 50, true);
        // Melee
        ItemRegistry.registerItem("wormbucket", new WormBucket(), 50, true);
        // Secondary
        ItemRegistry.registerItem("explosivesnowball", new ExplosiveSnowball(), 50, true);
        ItemRegistry.registerItem("magictools", new MagicTools(), 50, true);
        ItemRegistry.registerItem("bookmagma", new BookMagma(), 50, true);
        ItemRegistry.registerItem("bookbee", new BookBee(), 50, true);
        // Sentry
        ItemRegistry.registerItem("iceblossomstaff", new IceBlossomStaff(), 50, true);
        ItemRegistry.registerItem("sunflowerstaff", new SunflowerStaff(), 50, true);
        ItemRegistry.registerItem("firemonestaff", new FiremoneStaff(), 50, true);
        ItemRegistry.registerItem("bookmushroom", new BookMushroom(), 50, true);

            // --Weapons T2--

        // Minion
        ItemRegistry.registerItem("magicdungeoncandelabra", new MagicDungeonCandelabra(), 100, true);
        ItemRegistry.registerItem("magicgoldlamp", new MagicGoldLamp(), 100, true);
        ItemRegistry.registerItem("runebonestaff", new RuneboneStaff(), 100, true);
        ItemRegistry.registerItem("vampirewings", new VampireWings(), 100, true);
        // Melee
        ItemRegistry.registerItem("goblinsword", new GoblinSword(), 100, true);
        // Secondary
        ItemRegistry.registerItem("bookfrozen", new BookFrozen(), 100, true);
        ItemRegistry.registerItem("bookrunic", new BookRunic(), 100, true);
        // Sentry


            // --Weapons T3--

        // Minion
        ItemRegistry.registerItem("magiccastlecandelabra", new MagicCastleCandelabra(), 200, true);
        ItemRegistry.registerItem("magictungstenlamp", new MagicTungstenLamp(), 200, true);
        // Melee
        ItemRegistry.registerItem("fishianspear", new FishianSpear(), 200, true);
        ItemRegistry.registerItem("horrorscythe", new HorrorScythe(), 200, true);
        ItemRegistry.registerItem("horrorglaive", new HorrorGlaive(), 200, true);
        ItemRegistry.registerItem("horrorsword", new HorrorSword(), 200, true);
        // Ranged
        ItemRegistry.registerItem("mosquitobow", new MosquitoBow(), 200, true);
        // Secondary
        // Sentry

            // --Weapons T4--

        // Minion
        ItemRegistry.registerItem("gemamethystshards", new AmethystShards(), 400, true);
        ItemRegistry.registerItem("gemsapphireshards", new SapphireShards(), 400, true);
        ItemRegistry.registerItem("gememeraldshards", new EmeraldShards(), 400, true);
        ItemRegistry.registerItem("gemrubyshards", new RubyShards(), 400, true);
        // Melee

        // Secondary

        // Sentry
        ItemRegistry.registerItem("caveglowstaff", new CaveglowStaff(), 400, true);
        ItemRegistry.registerItem("vampirecoffin", new VampireCoffin(), 400, true);

            // --Armors--

        // T1
        ItemRegistry.registerItem("bloodplatemask", new BloodplateMask(), 50, true);
        ItemRegistry.registerItem("frostcrown", new FrostCrown(), 50, true);
        BuffRegistry.registerBuff("frostcrownsetbonus", new FrostCrownSetBonus());
        ItemRegistry.registerItem("copperminerhat", new CopperMinerHat(), 50, true);
        BuffRegistry.registerBuff("copperminersetbonus", new CopperMinerSetBonus());
        // T2
        ItemRegistry.registerItem("summonplagueboots", new SummonPlagueBoots(), 100, true);
        ItemRegistry.registerItem("summonplaguerobe", new SummonPlagueRobe(), 100, true);
        ItemRegistry.registerItem("summonplaguemask", new SummonPlagueMask(), 100, true);
        BuffRegistry.registerBuff("summonplaguesetbonus", new SummonPlagueSetBonus());
        // T3
        ItemRegistry.registerItem("shadowhelmet", new ShadowHelmet(), 200, true);
        BuffRegistry.registerBuff("shadowhelmetsetbonus", new ShadowHelmetSetBonus());
        ItemRegistry.registerItem("spiderbridehelmet", new SpiderBrideHelmet(), 200, true);
        ItemRegistry.registerItem("spiderbridechest", new SpiderBrideChest(), 200, true);
        ItemRegistry.registerItem("spiderbrideboots", new SpiderBrideBoots(), 200, true);
        BuffRegistry.registerBuff("spiderbridehelmetsetbonus", new SpiderBrideHelmetSetBonus());
        BuffRegistry.registerBuff("spiderbridesetbonus", new SpiderBrideSetBonus());
        BuffRegistry.registerBuff("spiderbridecooldown", new ShownCooldownBuff());
        ItemRegistry.registerItem("shadowhorrormantle", new ShadowHorrorMantle(), 200, true);
        ItemRegistry.registerItem("shadowhorrorboots", new ShadowHorrorBoots(), 200, true);
        ItemRegistry.registerItem("shadowhorrorhood", new ShadowHorrorHood(), 200, true);
        BuffRegistry.registerBuff("shadowhorrorsetbonus", new ShadowHorrorSetBonus());
        // T4
        //ItemRegistry.registerItem("", new (), 400, true);
        //ItemRegistry.registerItem("", new (), 400, true);
        //ItemRegistry.registerItem("", new (), 400, true);
        //BuffRegistry.registerBuff("", new ());

        // Minions
        MobRegistry.registerMob("enchantedbabyzombiearcherminion", EnchantedBabyZombieArcherMinion.class, false);
        MobRegistry.registerMob("enchantedbabyzombieminion", EnchantedBabyZombieMinion.class, false);
        MobRegistry.registerMob("golemamethystminion", GolemAmethystMinion.class, false);
        MobRegistry.registerMob("golemsapphireminion", GolemSapphireMinion.class, false);
        MobRegistry.registerMob("golememeraldminion", GolemEmeraldMinion.class, false);
        MobRegistry.registerMob("lampminiontungsten", LampMinionTungsten.class, false);
        MobRegistry.registerMob("lampminiondungeon", LampMinionDungeon.class, false);
        MobRegistry.registerMob("runerangedminion", RuneRangedMinion.class, false);
        MobRegistry.registerMob("lampminioncopper", LampMinionCopper.class, false);
        MobRegistry.registerMob("lampminioncastle", LampMinionCastle.class, false);
        MobRegistry.registerMob("runemeleeminion", RuneMeleeMinion.class, false);
        MobRegistry.registerMob("golemrubyminion", GolemRubyMinion.class, false);
        MobRegistry.registerMob("lampminiongold", LampMinionGold.class, false);
        MobRegistry.registerMob("beequeenminion", BeeQueenMinion.class, false);
        MobRegistry.registerMob("vampireminion", VampireMinion.class, false);
        MobRegistry.registerMob("polarminion", PolarMinion.class, false);
        MobRegistry.registerMob("bearminion", BearMinion.class, false);

        // Secondary Minions
        MobRegistry.registerMob("explosivesnowmanminion", ExplosiveSnowmanMinion.class, false);
        MobRegistry.registerMob("magmaslimeminion", BookMagmaSlimeMinion.class, false);
        MobRegistry.registerMob("magicpickaxeminion", MagicPickaxeMinion.class, false);
        MobRegistry.registerMob("runicshieldminion", BookRunicShieldMinion.class, false);
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
        MobRegistry.registerMob("mosquitobowminion", MosquitoBowMinion.class, false);

        // Armor Minions
        MobRegistry.registerMob("spiderbrideminion", SpiderBrideMinion.class, false);
        MobRegistry.registerMob("horrorbabyminion", HorrorBabyMinion.class, false);
        MobRegistry.registerMob("mouseminion", MouseMinion.class, false);

        // Trinket Minions
        MobRegistry.registerMob("horrorbatminion", HorrorBatMinion.class, false);

        // Sentries
        MobRegistry.registerMob("mushroomsentry", BookMushroomSentry.class, false);
        MobRegistry.registerMob("iceblossomsentry", IceBlossomSentry.class, false);
        MobRegistry.registerMob("sunflowersentry", SunflowerSentry.class, false);
        MobRegistry.registerMob("horrorspikesentry", HorrorSentry.class, false);
        MobRegistry.registerMob("firemonesentry", FiremoneSentry.class, false);
        MobRegistry.registerMob("caveglowsentry", CaveglowSentry.class, false);
        MobRegistry.registerMob("coffinsentry", CoffinSentry.class, false);

        // Minions from minions
        MobRegistry.registerMob("mushroomminion", BookMushroomMinion.class, false);
        MobRegistry.registerMob("batcryptminion", BatCryptMinion.class, false);
        MobRegistry.registerMob("beeminion", BeeMinion.class, false);

        // Projectiles
        ProjectileRegistry.registerProjectile("iceminionjavelinproj", IceMinionJavelinProj.class, "iceminionjavelin", "iceminionjavelin_shadow");
        ProjectileRegistry.registerProjectile("crystalminionproj", CrystalMinionProj.class, "crystalminionproj", "crystalminionproj_shadow");
        ProjectileRegistry.registerProjectile("iceblossomproj", IceBlossomProj.class, "iceblossomproj", "iceblossomproj_shadow");
        ProjectileRegistry.registerProjectile("sunflowerproj", SunflowerProj.class, "sunflowerproj", "sunflowerproj_shadow");
        ProjectileRegistry.registerProjectile("firemoneproj", FiremoneProj.class, "firemoneproj", "firemoneproj_shadow");
        ProjectileRegistry.registerProjectile("caveglowproj", CaveglowProj.class, "caveglowproj", "caveglowproj_shadow");
        ProjectileRegistry.registerProjectile("wormproj", WormProj.class, "wormproj", "wormproj_shadow");
        ProjectileRegistry.registerProjectile("horrorsentryproj", HorrorSentryProj.class, null, null);
        ProjectileRegistry.registerProjectile("emeraldlaserproj", EmeraldLaserProj.class, null, null);
        ProjectileRegistry.registerProjectile("runeboneproj", RuneBoneProj.class, null, null);
        ProjectileRegistry.registerProjectile("mosquitobowproj", MosquitoBowProj.class, "mosquitobowarrow", "nightpiercerarrow_shadow");

        // Minion buffs
        BuffRegistry.registerBuff("runicshieldbuff", new RunicShieldBuff());
        BuffRegistry.registerBuff("iceblossombuff", new IceBlossomBuff());
        BuffRegistry.registerBuff("sunflowerbuff", new SunflowerBuff());
        BuffRegistry.registerBuff("firemonebuff", new FiremoneBuff());
        BuffRegistry.registerBuff("mushroombuff", new MushroomBuff());
        BuffRegistry.registerBuff("honeybuff", new HoneyBuff());
        BuffRegistry.registerBuff("woodbuff", new WoodBuff());

        // Minion debuffs
        BuffRegistry.registerBuff("lamptungstendebuff", new LampTungstenDebuff());
        BuffRegistry.registerBuff("lampdungeondebuff", new LampDungeonDebuff());
        BuffRegistry.registerBuff("lampcastledebuff", new LampCastleDebuff());
        BuffRegistry.registerBuff("enchanteddebuff", new EnchantedDebuff());
        BuffRegistry.registerBuff("lampgolddebuff", new LampGoldDebuff());
        BuffRegistry.registerBuff("mushroomdebuff", new MushroomDebuff());
        BuffRegistry.registerBuff("mosquitodebuff", new MosquitoDebuff());
        BuffRegistry.registerBuff("honeydebuff", new HoneyDebuff());

        // Melee buffs
        BuffRegistry.registerBuff("horrorglaivecooldowndebuff", new ShownItemCooldownBuff(1, true, "items/horrorglaive"));
        BuffRegistry.registerBuff("goblincooldowndebuff", new ShownItemCooldownBuff(1, true, "items/goblinsword"));
        BuffRegistry.registerBuff("horrorswordstack", new HorrorSwordStackBuff());

        // --Trinkets T1--
        ItemRegistry.registerItem("mesmercharm", (new SimpleTrinketItem(Item.Rarity.RARE, "mesmercharmbuff", 100)).addDisables("mesmertablet", "zephyrcharm"), 50, true);
        BuffRegistry.registerBuff("mesmercharmbuff", new SimpleTrinketBuff("mesmercharmtip", new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.STAMINA_CAPACITY, 0.80f)));
        // --Trinkets T2--
        BuffRegistry.registerBuff("frozenassassinscowlbuff", new SimpleTrinketBuff("frozenassassinscowltip", new ModifierValue(BuffModifiers.SUMMON_CRIT_CHANCE, 0.15F), new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.20F), new ModifierValue(BuffModifiers.SUMMONS_TARGET_RANGE, 0.20F)));
        ItemRegistry.registerItem("essenceofcompanionship", new SimpleTrinketItem(Item.Rarity.RARE, "essenceofcompanionshipbuff", 200).addDisables("companionlocket", "essenceofperspective", "essenceofprolonging"), 100, true);
        ItemRegistry.registerItem("frozenassassinscowl", (new SimpleTrinketItem(Item.Rarity.RARE, "frozenassassinscowlbuff", 200)).addDisables("assassinscowl", "frozenwave"), 100, true);
        ItemRegistry.registerItem("demonicpolarclaw", (new DemonicPolarClaw()).addDisables("polarclaw", "demonclaw"), 200, true);
        BuffRegistry.registerBuff("essenceofcompanionshipbuff", new EssenceOfCompanionshipBuff());
        BuffRegistry.registerBuff("demonicpolarclawbuff", new DemonicPolarClawBuff());
        // --Trinkets T3--
        BuffRegistry.registerBuff("spelltabletbuff", new SimpleTrinketBuff("spelltablettip", new ModifierValue(BuffModifiers.MAX_MANA, 0.60F), new ModifierValue(BuffModifiers.MAX_SUMMONS, 2), new ModifierValue(BuffModifiers.SUMMON_ATTACK_SPEED, 0.30F), new ModifierValue(BuffModifiers.MAGIC_ATTACK_SPEED, 0.30F)));
        BuffRegistry.registerBuff("inducingsatchelbuff", new SimpleTrinketBuff("inducingsatcheltip", new ModifierValue(BuffModifiers.SUMMONS_SPEED, 0.40F), new ModifierValue(BuffModifiers.SPEED, 0.20F), new ModifierValue(BuffModifiers.EMITS_LIGHT, true)));
        ItemRegistry.registerItem("spelltablet", (new SimpleTrinketItem(Item.Rarity.RARE, "spelltabletbuff", 400)).addDisables("spellstone", "hysteriatablet", "hystericalmirror"), 200, true);
        ItemRegistry.registerItem("hystericalmirror", (new SimpleTrinketItem(Item.Rarity.RARE, "hystericalmirrorbuff", 400)).addDisables("scryingmirror", "hysteriatablet"), 200, true);
        ItemRegistry.registerItem("inducingsatchel", (new SimpleTrinketItem(Item.Rarity.RARE, "inducingsatchelbuff", 400)).addDisables("explorersatchel", "inducingamulet"), 200, true);
        BuffRegistry.registerBuff("hystericalmirrorbuff", new SimpleTrinketBuff("hystericalmirrortip", new ModifierValue(BuffModifiers.MAX_SUMMONS, 3), new ModifierValue(BuffModifiers.SUMMONS_SPEED, 0.45F)));
        BuffRegistry.registerBuff("bonepilebuff", new SimpleTrinketBuff("bonepiletip", new ModifierValue(BuffModifiers.ARMOR_PEN_FLAT, 25), new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.25F)));
        ItemRegistry.registerItem("bonepile", (new SimpleTrinketItem(Item.Rarity.RARE, "bonepilebuff", 400)).addDisables("bonehilt"), 200, true);
        ItemRegistry.registerItem("challengerarmorpiece", (new ChallengerArmorPiece()).addDisables("manica", "claygauntlet", "challengerspauldron"), 200, true);
        ItemRegistry.registerItem("shadowhorrorcape", (new ShadowHorrorCape()).addDisables("vampiresgift"), 200, true);
        ItemRegistry.registerItem("summonergambit", new SummonerGambit(), 200, true);
        BuffRegistry.registerBuff("challengerarmorpiecebuff", new ChallengerArmorPieceBuff());
        BuffRegistry.registerBuff("shadowhorrorcapebuff", new ShadowHorrorCapeBuff());
        BuffRegistry.registerBuff("summonergambitbuff", new SummonerGambitBuff());
        // --Trinkets T4--
        ItemRegistry.registerItem("mesmersatchel", (new SimpleTrinketItem(Item.Rarity.RARE, "mesmersatchelbuff", 800)).addDisables("mesmertablet", "zephyrcharm", "explorersatchel", "inducingamulet", "inducingsatchel", "mesmercharm"), 500, true);
        BuffRegistry.registerBuff("mesmersatchelbuff", new SimpleTrinketBuff("mesmersatcheltip", new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.STAMINA_CAPACITY, 1.00f), new ModifierValue(BuffModifiers.SUMMONS_SPEED, 0.50F), new ModifierValue(BuffModifiers.SPEED, 0.25F), new ModifierValue(BuffModifiers.EMITS_LIGHT, true)));
        ItemRegistry.registerItem("frenzyhorrorcape", (new FrenzyHorrorCape()).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape"), 500, true);
        BuffRegistry.registerBuff("frenzyhorrorcapebuff", new FrenzyHorrorCapeBuff());
        ItemRegistry.registerItem("necroticclaw", (new NecroticClaw()).addDisables("bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw"), 500, true);
        BuffRegistry.registerBuff("necroticclawbuff", new NecroticClawBuff());
        ItemRegistry.registerItem("frenzystonering", (new FrenzystoneRing()).addDisables("bloodstonering", "frenzyorb"), 500, true);
        BuffRegistry.registerBuff("frenzystoneringbuff", new FrenzystoneRingBuff());
        ItemRegistry.registerItem("necromancerarmor", (new NecromancerArmor()).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape", "frenzyhorrorcape", "bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw", "necroticclaw"), 500, true);
        BuffRegistry.registerBuff("necromancerarmorbuff", new NecromancerArmorBuff());

        // Potions
        ItemRegistry.registerItem("minionattackspeedpotion", new MinionAttackSpeedPotion(), 10, true);
        BuffRegistry.registerBuff("minionattackspeedbuff", new MinionAttackSpeedBuff());
        ItemRegistry.registerItem("minioncloserangepotion", new MinionCloseRangePotion(), 10, true);
        BuffRegistry.registerBuff("minioncloserangebuff", new MinionCloseRangeBuff());
        ItemRegistry.registerItem("minioncritchancepotion", new MinionCritChancePotion(), 10, true);
        BuffRegistry.registerBuff("minioncritchancebuff", new MinionCritChanceBuff());
        ItemRegistry.registerItem("minionrangepotion", new MinionRangePotion(), 10, true);
        BuffRegistry.registerBuff("minionrangebuff", new MinionRangeBuff());
        ItemRegistry.registerItem("minionspeedpotion", new MinionSpeedPotion(), 10, true);
        BuffRegistry.registerBuff("minionspeedbuff", new MinionSpeedBuff());
        ItemRegistry.registerItem("minioncritpotion", new MinionCritPotion(), 10, true);
        BuffRegistry.registerBuff("minioncritbuff", new MinionCritBuff());
        ItemRegistry.registerItem("minionfarmpotion", new MinionFarmPotion(), 10, true);
        BuffRegistry.registerBuff("minionfarmbuff", new MinionFarmBuff());

        // Banners
        ItemRegistry.registerItem("bannerofresilience", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.RESILIENCEBOOST), 200.0F, true);
        BuffRegistry.registerBuff("resiliencebannerbuff", new ResilienceBannerBuff());
        ItemRegistry.registerItem("bannerofbouncing", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.BOUNCEBOOST), 200.0F, true);
        BuffRegistry.registerBuff("bouncingbannerbuff", new BouncingBannerBuff());
        ItemRegistry.registerItem("bannerofessence", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.ESSENCEBOOST), 200.0F, true);
        BuffRegistry.registerBuff("essencebannerbuff", new EssenceBannerBuff());
        ItemRegistry.registerItem("bannerofstamina", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.STAMINABOOST), 200.0F, true);
        BuffRegistry.registerBuff("staminabannerbuff", new StaminaBannerBuff());
        ItemRegistry.registerItem("bannerofpicking", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.PICKUPBOOST), 200.0F, true);
        BuffRegistry.registerBuff("pickingbannerbuff", new PickingBannerBuff());
        ItemRegistry.registerItem("bannerofdashing", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.DASHBOOST), 200.0F, true);
        BuffRegistry.registerBuff("dashingbannerbuff", new DashingBannerBuff());
        ItemRegistry.registerItem("bannerofmana", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.MANABOOST), 200.0F, true);
        BuffRegistry.registerBuff("manabannerbuff", new ManaBannerBuff());

        // Materials & Others
        ItemRegistry.registerItem("shadowhorrorportal", new ShadowHorrorPortal(), 50, true);
        ItemRegistry.registerItem("purehorror", new PureHorror(), 50, true);

        // Mounts
        ItemRegistry.registerItem("chieftainhat", new ChieftainHat(), 50, true);
        MobRegistry.registerMob("chiefsummonmount", ChiefSummonMount.class, false);
        BuffRegistry.registerBuff("chiefbuff", new ChiefBuff());

        ItemRegistry.registerItem("magiccheese", new MagicCheese(), 50, true);
        MobRegistry.registerMob("mousesummonmount", MouseSummonMount.class, false);

        // Mobs
        MobRegistry.registerMob("horrorspiritbossmob", HorrorSpiritBossMob.class, true);
        MobRegistry.registerMob("horrorspiritmob", HorrorSpiritMob.class, true);
        MobRegistry.registerMob("riftportalmob", RiftPortalMob.class, true);
        MobRegistry.registerMob("woodmob", WoodMob.class, true);
    }

    public void initResources()
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
        MouseMinion.texture = GameTexture.fromFile("mobs/mouseminion");

        // Sentry
        BookMushroomSentry.texture = GameTexture.fromFile("mobs/mushroomsentry");
        IceBlossomSentry.texture = GameTexture.fromFile("mobs/iceblossomsentry");
        SunflowerSentry.texture = GameTexture.fromFile("mobs/sunflowersentry");
        HorrorSentry.texture = GameTexture.fromFile("mobs/horrorspikesentry");
        FiremoneSentry.texture = GameTexture.fromFile("mobs/firemonesentry");
        CaveglowSentry.texture = GameTexture.fromFile("mobs/caveglowsentry");
        CoffinSentry.texture = GameTexture.fromFile("mobs/coffinsentry");

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

    public void postInit()
    {
        Recipes.registerModRecipe(new Recipe(
                "magictools",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("woodpickaxe", 1),
                        new Ingredient("woodaxe", 1),
                        new Ingredient("anysapling", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "copperminerhat",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("copperbar", 15),
                        new Ingredient("torch", 25)
                }
        ).showBefore("copperhelmet"));

        Recipes.registerModRecipe(new Recipe(
                "magiccopperlamp",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("copperbar", 2),
                        new Ingredient("oillantern", 1),
                        new Ingredient("torch", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "magicgoldlamp",
                1,
                RecipeTechRegistry.DEMONIC_ANVIL,
                new Ingredient[]{
                        new Ingredient("goldbar", 6),
                        new Ingredient("oillantern", 1),
                        new Ingredient("torch", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "magictungstenlamp",
                1,
                RecipeTechRegistry.TUNGSTEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("tungstenbar", 10),
                        new Ingredient("oillantern", 1),
                        new Ingredient("torch", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "magicdungeoncandelabra",
                1,
                RecipeTechRegistry.DEMONIC_ANVIL,
                new Ingredient[]{
                        new Ingredient("dungeoncandelabra", 1),
                        new Ingredient("magicstilts", 1),
                        new Ingredient("voidshard", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "magiccastlecandelabra",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("spidercastlecandelabra", 1),
                        new Ingredient("spideritearrow", 50)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summoningtableduo",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("anylog", 35),
                        new Ingredient("clay", 15),
                        new Ingredient("healthpotion", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summoningtableduo",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("clay", 10),
                        new Ingredient("healthpotion", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summoningbookshelf",
                1,
                RecipeTechRegistry.CARPENTER,
                new Ingredient[]{
                        new Ingredient("anylog", 40),
                        new Ingredient("craftingguide", 1),
                        new Ingredient("book", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bannerofstamina",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("mackerel", 10)

                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofmana",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("manapotion", 10)

                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofdashing",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("zephyrcharm", 1)

                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofbouncing",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("swampgrassseed", 10)
                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofessence",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("essenceofperspective", 1)
                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofpicking",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("honey", 10)
                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofresilience",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("friedegg", 10)
                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bookmushroom",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("mushroom", 25),
                        new Ingredient("spoiledfood", 10),
                        new Ingredient("book", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bookbee",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("honeybee", 6),
                        new Ingredient("apiaryframe", 4),
                        new Ingredient("book", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bookmagma",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("fireresistancepotion", 5),
                        new Ingredient("torch", 100),
                        new Ingredient("book", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bookfrozen",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("icejavelin", 100),
                        new Ingredient("frostpiercer", 1),
                        new Ingredient("book", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bookrunic",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("runestone", 25),
                        new Ingredient("book", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorportal",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 5),
                        new Ingredient("mysteriousportal", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "royalhive",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("queenbee", 1),
                        new Ingredient("honeybee", 3),
                        new Ingredient("honey", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bearhead",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("honey", 5),
                        new Ingredient("furfish", 15),
                        new Ingredient("wool", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "polarhead",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("polarclaw", 1),
                        new Ingredient("icefish", 10),
                        new Ingredient("wool", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "enchantedbrainonastick",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("brainonastick", 1),
                        new Ingredient("spoiledfood", 10),
                        new Ingredient("voidshard", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "explosivesnowball",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("dynamitestick", 1),
                        new Ingredient("snowmantrainingdummy", 1),
                        new Ingredient("snowball", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sunflowerstaff",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("woodstaff", 1),
                        new Ingredient("sunflower", 10),
                        new Ingredient("fertilizer", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "iceblossomstaff",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("woodstaff", 1),
                        new Ingredient("iceblossom", 10),
                        new Ingredient("fertilizer", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "firemonestaff",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("woodstaff", 1),
                        new Ingredient("firemone", 10),
                        new Ingredient("fertilizer", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "wormbucket",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("bucket", 1),
                        new Ingredient("wormbait", 15),
                        new Ingredient("mackerel", 5),
                        new Ingredient("herring", 5),
                        new Ingredient("salmon", 5),
                        new Ingredient("trout", 5),
                        new Ingredient("carp", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "mesmercharm",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("mesmertablet", 1),
                        new Ingredient("zephyrcharm", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bloodplatemask",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("bloodplatecowl", 1),
                        new Ingredient("batwing", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bloodplatechestplate",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("demonicbar", 5),
                        new Ingredient("batwing", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bloodplateboots",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("demonicbar", 5),
                        new Ingredient("batwing", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frostcrown",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("frostshard", 5),
                        new Ingredient("goldcrown", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frostchestplate",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("frostshard", 6),
                        new Ingredient("goldchestplate", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frostboots",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("frostshard", 4),
                        new Ingredient("goldboots", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "vampirewings",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("batwing", 10),
                        new Ingredient("healthregenpotion", 1),
                        new Ingredient("demonicbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "runebonestaff",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("runeboundscepter", 1),
                        new Ingredient("runestone", 10),
                        new Ingredient("clothscraps", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summonplaguemask",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("wool", 10),
                        new Ingredient("ivybar", 2),
                        new Ingredient("voidshard", 1),
                        new Ingredient("glassbottle", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summonplaguerobe",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("wool", 10),
                        new Ingredient("ivybar", 2),
                        new Ingredient("voidshard", 1),
                        new Ingredient("glassbottle", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summonplagueboots",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("wool", 10),
                        new Ingredient("ivybar", 2),
                        new Ingredient("voidshard", 1),
                        new Ingredient("glassbottle", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "goblinsword",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("brokenirontool", 5),
                        new Ingredient("ironhelmet", 1),
                        new Ingredient("ironchestplate", 1),
                        new Ingredient("ironboots", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "demonicpolarclaw",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("polarclaw", 1),
                        new Ingredient("demonclaw", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frozenassassinscowl",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("assassinscowl", 1),
                        new Ingredient("frozenwave", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "essenceofcompanionship",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("companionlocket", 1),
                        new Ingredient("essenceofprolonging", 1),
                        new Ingredient("essenceofperspective", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "inducingsatchel",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("explorersatchel", 1),
                        new Ingredient("inducingamulet", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "magiccheese",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("cheese", 1),
                        new Ingredient("luckycape", 1),
                        new Ingredient("speedpotion", 5),
                        new Ingredient("voidshard", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "chieftainhat",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("brutesbattleaxe", 1),
                        new Ingredient("boneoffering", 1),
                        new Ingredient("runeboundbackbones", 1),
                        new Ingredient("resistancepotion", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "horrorscythe",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 25),
                        new Ingredient("farmingscythe", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "horrorglaive",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 25),
                        new Ingredient("frostaxe", 1),
                        new Ingredient("glacialaxe", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "horrorsword",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 12),
                        new Ingredient("woodsword", 1),
                        new Ingredient("coppersword", 1),
                        new Ingredient("ironsword", 1),
                        new Ingredient("goldsword", 1),
                        new Ingredient("frostsword", 1),
                        new Ingredient("demonicsword", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "fishianspear",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("bamboo", 25),
                        new Ingredient("seashell", 5),
                        new Ingredient("seasnail", 5),
                        new Ingredient("seastar", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "mosquitobow",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("spoiledfood", 30),
                        new Ingredient("myceliumore", 12),
                        new Ingredient("deepswampstone", 35),
                        new Ingredient("reeds", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "caveglowstaff",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("caveglow", 5),
                        new Ingredient("tungstenore", 15),
                        new Ingredient("fertilizer", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhelmet",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ectoplasm", 12),
                        new Ingredient("obsidian", 10),
                        new Ingredient("bone", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowmantle",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ectoplasm", 8),
                        new Ingredient("bone", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ectoplasm", 10),
                        new Ingredient("bone", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorhood",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("wool", 12),
                        new Ingredient("leather", 10),
                        new Ingredient("purehorror", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhorrormantle",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("wool", 12),
                        new Ingredient("leather", 10),
                        new Ingredient("purehorror", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("wool", 12),
                        new Ingredient("leather", 10),
                        new Ingredient("purehorror", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spiderbridehelmet",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("silk", 10),
                        new Ingredient("myceliumbar", 10),
                        new Ingredient("cavespidergland", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spiderbridechest",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("silk", 15),
                        new Ingredient("myceliumbar", 10),
                        new Ingredient("cavespidergland", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spiderbrideboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("silk", 10),
                        new Ingredient("myceliumbar", 10),
                        new Ingredient("cavespidergland", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorcape",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("vampiresgift", 1),
                        new Ingredient("purehorror", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bonepile",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("bonehilt", 1),
                        new Ingredient("skull", 1),
                        new Ingredient("ectoplasm", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "hystericalmirror",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("scryingmirror", 1),
                        new Ingredient("hysteriatablet", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spelltablet",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("spellstone", 1),
                        new Ingredient("hysteriatablet", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "challengerarmorpiece",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("manica", 1),
                        new Ingredient("claygauntlet", 1),
                        new Ingredient("challengerspauldron", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summonergambit",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("summonfoci", 1),
                        new Ingredient("foolsgambit", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "vampirecoffin",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("cryptstone", 125),
                        new Ingredient("vampirewings", 1),
                        new Ingredient("bloodvolley", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "gemrubyshards",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ruby", 20),
                        new Ingredient("pearlescentdiamond", 5),
                        new Ingredient("omnicrystal", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "gemsapphireshards",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("sapphire", 20),
                        new Ingredient("pearlescentdiamond", 5),
                        new Ingredient("omnicrystal", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "gememeraldshards",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("emerald", 20),
                        new Ingredient("pearlescentdiamond", 5),
                        new Ingredient("omnicrystal", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "gemamethystshards",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("amethyst", 20),
                        new Ingredient("pearlescentdiamond", 5),
                        new Ingredient("omnicrystal", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "mesmersatchel",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("mesmercharm", 1),
                        new Ingredient("inducingsatchel", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frenzystonering",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("bloodstonering", 1),
                        new Ingredient("frenzyorb", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frenzyhorrorcape",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("frenzystonering", 1),
                        new Ingredient("shadowhorrorcape", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "necroticclaw",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("demonicpolarclaw", 1),
                        new Ingredient("bonepile", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "necromancerarmor",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("frozenassassinscowl", 1),
                        new Ingredient("frenzyhorrorcape", 1),
                        new Ingredient("necroticclaw", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minioncritchancepotion",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("blueberry", 5),
                        new Ingredient("thorns", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minioncritpotion",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("carp", 1),
                        new Ingredient("thorns", 6)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minionspeedpotion",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("sugar", 2),
                        new Ingredient("batwing", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minioncloserangepotion",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("copperbar", 1),
                        new Ingredient("halffish", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minionrangepotion",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("carrot", 1),
                        new Ingredient("sunflower", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minionfarmpotion",
                1,
                RecipeTechRegistry.CAVEGLOW_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("caveglow", 1),
                        new Ingredient("wheat", 5),
                        new Ingredient("cavespidergland", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minionattackspeedpotion",
                1,
                RecipeTechRegistry.CAVEGLOW_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("ectoplasm", 2),
                        new Ingredient("blackcoffee", 1)
                }
        ));

        Biome.defaultDeepCaveMobs
                .add(10, "horrorspiritmob");

        LootTablePresets.basicCaveChest.items.add(
                ChanceLootItem.between(0.02f, "magiccopperlamp", 1, 1)
        );

        LootTablePresets.alchemistChest.items.addAll(
                new LootItemList(
                        ChanceLootItem.between(0.08f, "minioncritpotion", 1, 3),
                        ChanceLootItem.between(0.01f, "minionfarmpotion", 1, 3),
                        ChanceLootItem.between(0.05f, "minionrangepotion", 1, 3),
                        ChanceLootItem.between(0.05f, "minionspeedpotion", 1, 3),
                        ChanceLootItem.between(0.01f, "minioncloserangepotion", 1, 3),
                        ChanceLootItem.between(0.08f, "minioncritchancepotion", 1, 3),
                        ChanceLootItem.between(0.05f, "minionattackspeedpotion", 1, 3)
                )
        );
    }
}
