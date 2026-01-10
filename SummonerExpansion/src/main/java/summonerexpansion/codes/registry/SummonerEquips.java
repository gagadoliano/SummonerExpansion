package summonerexpansion.codes.registry;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.SimpleTrinketBuff;
import necesse.inventory.item.Item;
import necesse.inventory.item.trinketItem.ShieldTrinketItem;
import necesse.inventory.item.trinketItem.SimpleTrinketItem;
import necesse.inventory.lootTable.presets.TrinketsLootTable;
import summonerexpansion.items.equips.armor.*;
import summonerexpansion.items.equips.mountbuffs.MountChiefBuff;
import summonerexpansion.items.equips.mountminions.*;
import summonerexpansion.items.equips.mountitems.*;
import summonerexpansion.items.equips.armorsetbonus.*;
import summonerexpansion.items.equips.trinket.*;
import summonerexpansion.items.equips.trinketbuffs.*;
import summonerexpansion.mobs.summonminions.trinketminions.*;

public class SummonerEquips
{
    public static class SummonerArmorBuffs
    {
        public static Buff COPPERSET_COOLDOWN;
        public static Buff TITANIUMRANGEDBUFF;

        public SummonerArmorBuffs() {
        }
    }

    public static void registerSummonEquips()
    {
        SummonerEquips.registerSummonArmors();
        SummonerEquips.registerSummonTrinkets();
        SummonerEquips.registerSummonMounts();
    }

    public static void registerSummonArmors()
    {
        // Armor Heads
        // T1
        ItemRegistry.registerItem("bloodplatemask", new BloodplateMask(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("bloodplatemasksetbonus", new BloodplateMaskSetBonus());
        ItemRegistry.registerItem("frostcrown", new FrostCrown(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("frostcrownsetbonus", new FrostCrownSetBonus());
        ItemRegistry.registerItem("copperminerhat", new CopperMinerHat(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("copperminersetbonus", new CopperMinerSetBonus());
        BuffRegistry.registerBuff("coppersetcooldown", SummonerArmorBuffs.COPPERSET_COOLDOWN = new ShownCooldownBuff());
        ItemRegistry.registerItem("leathersummonerhood", new LeatherSummonerHood(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("leathersummonersetbonus", new LeatherSummonerSetBonus());
        // T2
        ItemRegistry.registerItem("pharaohsmask", new PharaohsMask(200, Item.Rarity.UNCOMMON), 100, true);
        BuffRegistry.registerBuff("pharaohsmasksetbonus", new PharaohsMaskSetBonus());
        // T3
        ItemRegistry.registerItem("shadowhelmet", new ShadowHelmet(400, Item.Rarity.RARE), 200, true);
        BuffRegistry.registerBuff("shadowhelmetsetbonus", new ShadowHelmetSetBonus());
        ItemRegistry.registerItem("agedsummonerhelmet", new AgedSummonerHelmet(400, Item.Rarity.RARE), 200, true);
        BuffRegistry.registerBuff("agedsummonersetbonus", new AgedSummonerSetBonus());
        ItemRegistry.registerItem("sharpshootersummonhat", new SharpshooterSummonHat(400, Item.Rarity.RARE), 200, true);
        BuffRegistry.registerBuff("sharpshootersummonsetbonus", new SharpshooterSummonSetBonus());
        // T4
        ItemRegistry.registerItem("arcanicsummonhelmet", new ArcanicSummonerHelmet(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("arcanicsummonsetbonus", new ArcanicSummonerSetBonus());
        BuffRegistry.registerBuff("arcanicsummoncooldown", new ShownCooldownBuff());
        ItemRegistry.registerItem("ravenlordsummonmask", new RavenlordSummonerMask(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("ravenlordsummonsetbonus", new RavenlordSummonerSetBonus());
        ItemRegistry.registerItem("chefsummonerhat", new ChefSummonerHat(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("chefsummonerhatsetbonus", new ChefSummonerHatSetBonus());
        ItemRegistry.registerItem("slimehood", new SlimeHood(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("slimehoodsetbonus", new SlimeHoodSetBonus());

        // Armor Sets
        // T1
        ItemRegistry.registerItem("redspiderhelmet", new RedSpiderHelmet(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("redspiderchestplate", new RedSpiderChestplate(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("redspiderboots", new RedSpiderBoots(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("redspidersetbonus", new RedSpiderSetBonus());
        ItemRegistry.registerItem("rainsummonhat", new RainSummonHat(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("rainsummoncoat", new RainSummonCoat(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("rainsummonboots", new RainsummonBoots(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("rainsummonsetbonus", new RainSummonSetBonus());
        // T2
        ItemRegistry.registerItem("summonplaguemask", new PlagueSummonerMask(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("summonplaguerobe", new PlagueSummonerRobe(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("summonplagueboots", new PlagueSummonerBoots(200, Item.Rarity.UNCOMMON), 100, true);
        BuffRegistry.registerBuff("summonplaguesetbonus", new SummonPlagueSetBonus());
        ItemRegistry.registerItem("titaniummagichelmet", new TitaniumMagicHelmet(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("titaniummeleehelmet", new TitaniumMeleeHelmet(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("titaniumrangedhelmet", new TitaniumRangedHelmet(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("titaniumsummonhelmet", new TitaniumSummonHelmet(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("titaniumchestplate", new TitaniumChestplate(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("titaniumboots", new TitaniumBoots(100, Item.Rarity.COMMON), 50, true);
        BuffRegistry.registerBuff("titaniumsetbonus", new TitaniumSetBonus());
        BuffRegistry.registerBuff("titaniummeleesetbonus", new TitaniumMeleeSetBonus());
        BuffRegistry.registerBuff("titaniummagicsetbonus", new TitaniumMagicSetBonus());
        BuffRegistry.registerBuff("titaniumrangedsetbonus", SummonerArmorBuffs.TITANIUMRANGEDBUFF = new TitaniumRangedSetBonus());
        BuffRegistry.registerBuff("titaniumsummonsetbonus", new TitaniumSummonSetBonus());
        // T3
        ItemRegistry.registerItem("spiderbridehelmet", new SpiderBrideHelmet(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("spiderbridechest", new SpiderBrideChest(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("spiderbrideboots", new SpiderBrideBoots(400, Item.Rarity.RARE), 200, true);
        BuffRegistry.registerBuff("spiderbridesetbonus", new SpiderBrideSetBonus());
        BuffRegistry.registerBuff("spiderbridecooldown", new ShownCooldownBuff());
        ItemRegistry.registerItem("shadowhorrorhood", new ShadowHorrorHood(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("shadowhorrormantle", new ShadowHorrorMantle(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("shadowhorrorboots", new ShadowHorrorBoots(400, Item.Rarity.RARE), 200, true);
        BuffRegistry.registerBuff("shadowhorrorsetbonus", new ShadowHorrorSetBonus());
        // T4
        ItemRegistry.registerItem("ghostcaptainshat", new GhostCaptainHat(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("ghostcaptainsshirt", new GhostCaptainShirt(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("ghostcaptainsboots", new GhostCaptainBoots(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("ghostcaptainssetbonus", new GhostCaptainSetBonus());
        BuffRegistry.registerBuff("ghostcaptainscooldown", new ShownCooldownBuff());
        ItemRegistry.registerItem("sailorsummonhat", new SailorSummonHat(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("sailorsummonshirt", new SailorSummonShirt(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("sailorsummonshoes", new SailorSummonShoes(800, Item.Rarity.EPIC), 400, true);
        BuffRegistry.registerBuff("sailorsummonsetbonus", new SailorSummonSetBonus());
    }

    public static void registerSummonTrinkets()
    {
        // T1
        ItemRegistry.registerItem("mesmercharm", new SimpleTrinketItem(Item.Rarity.COMMON, "mesmercharmbuff", 100, TrinketsLootTable.trinkets).addDisables("mesmertablet", "zephyrcharm"), 100, true);
        BuffRegistry.registerBuff("mesmercharmbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue<>(BuffModifiers.STAMINA_CAPACITY, 0.80f)));
        ItemRegistry.registerItem("cactusemblem", new SimpleTrinketItem(Item.Rarity.COMMON, "cactusemblembuff", 100, TrinketsLootTable.trinkets), 100, true);
        BuffRegistry.registerBuff("cactusemblembuff", new CactusEmblemBuff());
        ItemRegistry.registerItem("silvergoblet", new SimpleTrinketItem(Item.Rarity.COMMON, "silvergobletaurabuff", 100, TrinketsLootTable.trinkets), 100, true);
        BuffRegistry.registerBuff("silvergobletaurabuff", new SilverGobletAuraBuff());
        BuffRegistry.registerBuff("silvergobletbuff", new SilverGobletBuff());
        // T2
        ItemRegistry.registerItem("frozenassassinscowl", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "frozenassassinscowlbuff", 200, TrinketsLootTable.trinkets).addDisables("assassinscowl", "frozenwave"), 200, true);
        BuffRegistry.registerBuff("frozenassassinscowlbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, 0.10F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.25F), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 0.50F)));
        ItemRegistry.registerItem("demonicpolarclaw", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "demonicpolarclawbuff", 200, TrinketsLootTable.trinkets).addDisables("polarclaw", "demonclaw"), 200, true);
        BuffRegistry.registerBuff("demonicpolarclawbuff", new DemonicPolarClawBuff());
        ItemRegistry.registerItem("cowskull", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "cowskullbuff", 200, TrinketsLootTable.trinkets), 200, true);
        BuffRegistry.registerBuff("cowskullbuff", new SimpleTrinketBuff(new ModifierValue<>(SummonerModifiers.TRANSFORMATION_SPEED, 0.40F)));
        ItemRegistry.registerItem("duelistdolls", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "duelistdollsbuff", 200, TrinketsLootTable.trinkets), 200, true);
        BuffRegistry.registerBuff("duelistdollsbuff", new SimpleTrinketBuff(new ModifierValue<>(SummonerModifiers.SENTRY_ATTACK_SPEED, 0.20F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.20F), new ModifierValue<>(BuffModifiers.TARGET_RANGE, 0.40F)));
        // T3
        ItemRegistry.registerItem("essenceofcompanionship", new SimpleTrinketItem(Item.Rarity.RARE, "essenceofcompanionshipbuff", 400, TrinketsLootTable.trinkets).addDisables("companionlocket", "essenceofperspective", "essenceofprolonging"), 400, true);
        BuffRegistry.registerBuff("essenceofcompanionshipbuff", new EssenceOfCompanionshipBuff());
        ItemRegistry.registerItem("spelltablet", new SimpleTrinketItem(Item.Rarity.RARE, "spelltabletbuff", 400, TrinketsLootTable.trinkets).addDisables("spellstone", "hysteriatablet", "inducingamulet", "mesmertablet"), 400, true);
        BuffRegistry.registerBuff("spelltabletbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.MAX_MANA, 0.50F), new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 2), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.30F), new ModifierValue<>(BuffModifiers.MAGIC_ATTACK_SPEED, 0.30F)));
        ItemRegistry.registerItem("inducingsatchel", new SimpleTrinketItem(Item.Rarity.RARE, "inducingsatchelbuff", 400, TrinketsLootTable.trinkets).addDisables("explorersatchel", "inducingamulet"), 400, true);
        BuffRegistry.registerBuff("inducingsatchelbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.10F), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.30F), new ModifierValue<>(BuffModifiers.SPEED, 0.10F), new ModifierValue<>(BuffModifiers.EMITS_LIGHT, true)));
        ItemRegistry.registerItem("hystericalmirror", new SimpleTrinketItem(Item.Rarity.RARE, "hystericalmirrorbuff", 400, TrinketsLootTable.trinkets).addDisables("scryingmirror", "hysteriatablet", "inducingamulet", "mesmertablet"), 400, true);
        BuffRegistry.registerBuff("hystericalmirrorbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 3), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.30F)));
        ItemRegistry.registerItem("challengerarmorpiece", new SimpleTrinketItem(Item.Rarity.RARE, "challengerarmorpiecebuff", 400, TrinketsLootTable.trinkets).addDisables("manica", "claygauntlet", "challengerspauldron"), 400, true);
        BuffRegistry.registerBuff("challengerarmorpiecebuff", new ChallengerArmorPieceBuff());
        ItemRegistry.registerItem("bonepile", new SimpleTrinketItem(Item.Rarity.RARE, "bonepilebuff", 400, TrinketsLootTable.trinkets).addDisables("bonehilt"), 400, true);
        BuffRegistry.registerBuff("bonepilebuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.ARMOR_PEN_FLAT, 20), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.05F)));
        ItemRegistry.registerItem("shadowhorrorcape", new SimpleTrinketItem(Item.Rarity.RARE, "shadowhorrorcapebuff", 400, TrinketsLootTable.trinkets).addDisables("vampiresgift"), 400, true);
        BuffRegistry.registerBuff("shadowhorrorcapebuff", new ShadowHorrorCapeBuff());
        ItemRegistry.registerItem("doomshroomshield", new SimpleTrinketItem(Item.Rarity.RARE, "doomshroomshieldbuff", 400, TrinketsLootTable.trinkets), 400, true);
        BuffRegistry.registerBuff("doomshroomshieldcooldown", new ShownCooldownBuff());
        BuffRegistry.registerBuff("doomshroomshieldbuff", new DoomShroomShieldBuff());
        ItemRegistry.registerItem("strangecookpot", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "strangecookpotbuff", 400, TrinketsLootTable.trinkets), 400, true);
        BuffRegistry.registerBuff("strangecookpotbuff", new StrangeCookPotBuff());
        ItemRegistry.registerItem("summonergambit", new SummonerGambit(400, Item.Rarity.RARE), 400, true);
        BuffRegistry.registerBuff("summonergambitbuff", new SummonerGambitBuff());
        // T4
        ItemRegistry.registerItem("mesmersatchel", new SimpleTrinketItem(Item.Rarity.EPIC, "mesmersatchelbuff", 800, TrinketsLootTable.trinkets).addDisables("mesmertablet", "zephyrcharm", "explorersatchel", "inducingamulet", "inducingsatchel", "mesmercharm"), 800, true);
        BuffRegistry.registerBuff("mesmersatchelbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue<>(BuffModifiers.STAMINA_CAPACITY, 1.00f), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.10F), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.30F), new ModifierValue<>(BuffModifiers.SPEED, 0.10F), new ModifierValue<>(SummonerModifiers.EMITS_SUMMON_LIGHT, true)));
        ItemRegistry.registerItem("companionsatchels", new SimpleTrinketItem(Item.Rarity.EPIC, "companionsatchelsbuff", 800, TrinketsLootTable.trinkets).addDisables("mesmersatchel", "essenceofcompanionship", "companionlocket", "essenceofperspective", "essenceofprolonging"), 800, true);
        BuffRegistry.registerBuff("companionsatchelsbuff", new CompanionSatchelsBuff());
        ItemRegistry.registerItem("scryingmagicianscard", new SimpleTrinketItem(Item.Rarity.EPIC, "scryingmagicianscardbuff", 800, TrinketsLootTable.trinkets).addDisables("hystericalmirror", "spelltablet", "forbiddenspellbook", "scryingcards", "prophecyslab", "magicmanual", "spellstone", "hysteriatablet", "inducingamulet", "mesmertablet"), 800, true);
        BuffRegistry.registerBuff("scryingmagicianscardbuff", new ScryingMagiciansCardBuff());
        ItemRegistry.registerItem("necromancerarmor", new SimpleTrinketItem(Item.Rarity.EPIC, "necromancerarmorbuff", 800, TrinketsLootTable.trinkets).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape", "frenzyhorrorcape", "bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw", "necroticclaw"), 800, true);
        BuffRegistry.registerBuff("necromancerarmorbuff", new NecromancerArmorBuff());
        ItemRegistry.registerItem("magicianboard", new SimpleTrinketItem(Item.Rarity.EPIC, "magicianboardbuff", 800, TrinketsLootTable.trinkets).addDisables("scryingmagicianscard", "balancedsummonerboard", "balancedfrostfirefoci", "spiritboard", "summonersbestiary"), 800, true);
        BuffRegistry.registerBuff("magicianboardbuff", new MagicianBoardBuff());
        ItemRegistry.registerItem("transplantedheart", new SimpleTrinketItem(Item.Rarity.EPIC, "transplantedheartbuff", 800, TrinketsLootTable.trinkets).addDisables("lifependant", "clockworkheart", "frozensoul", "lifeline", "frozenheart"), 800, true);
        BuffRegistry.registerBuff("transplantedheartbuff", new TransplantedHeartBuff());
        ItemRegistry.registerItem("balancedsummonerboard", new SimpleTrinketItem(Item.Rarity.EPIC, "balancedsummonerboardbuff", 800, TrinketsLootTable.trinkets).addDisables("balancedfrostfirefoci", "spiritboard", "summonersbestiary"), 800, true);
        BuffRegistry.registerBuff("balancedsummonerboardbuff", new BalancedSummonerBoardBuff());
        ItemRegistry.registerItem("frenzyhorrorcape", new SimpleTrinketItem(Item.Rarity.EPIC, "frenzyhorrorcapebuff", 800, TrinketsLootTable.trinkets).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape"), 800, true);
        BuffRegistry.registerBuff("frenzyhorrorcapebuff", new FrenzyHorrorCapeBuff());
        ItemRegistry.registerItem("necroticclaw", new SimpleTrinketItem(Item.Rarity.EPIC, "necroticclawbuff", 800, TrinketsLootTable.trinkets).addDisables("bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw"), 800, true);
        BuffRegistry.registerBuff("necroticclawbuff", new NecroticClawBuff());
        ItemRegistry.registerItem("frenzystonering", new SimpleTrinketItem(Item.Rarity.EPIC, "frenzystoneringbuff", 800, TrinketsLootTable.trinkets).addDisables("bloodstonering", "frenzyorb"), 800, true);
        BuffRegistry.registerBuff("frenzystoneringbuff", new FrenzystoneRingBuff());
        ItemRegistry.registerItem("crystalamalgamation", new SimpleTrinketItem(Item.Rarity.EPIC, "crystalamalgamationbuff", 800, TrinketsLootTable.trinkets), 800, true);
        BuffRegistry.registerBuff("crystalamalgamationbuff", new CrystalamAlgamationBuff());
        ItemRegistry.registerItem("calmminerslantern", new CalmMinerLantern(800, Item.Rarity.EPIC), 800, true);
        BuffRegistry.registerBuff("calmminerslanternbuff", new CalmMinersLanternBuff());
        ItemRegistry.registerItem("minerslantern", new MinerLantern(800, Item.Rarity.EPIC), 800, true);
        BuffRegistry.registerBuff("minerslanternbuff", new MinersLanternBuff());
        ItemRegistry.registerItem("bagofbullets", new BagOfBullets(800, Item.Rarity.EPIC), 800, true);
        BuffRegistry.registerBuff("bagofbulletsbuff", new BagOfBulletsBuff());
        ItemRegistry.registerItem("bagofarrows", new BagOfArrows(800, Item.Rarity.EPIC), 800, true);
        BuffRegistry.registerBuff("bagofarrowsbuff", new BagOfArrowsBuff());
        ItemRegistry.registerItem("littleangel", new LittleAngel(800, Item.Rarity.EPIC), 800, true);
        BuffRegistry.registerBuff("littleangelbuff", new LittleAngelBuff());
        ItemRegistry.registerItem("littlemecha", new LittleMecha(800, Item.Rarity.EPIC), 800, true);
        BuffRegistry.registerBuff("littlemechabuff", new LittleMechaBuff());
        // Challenge
        ItemRegistry.registerItem("jellyfishbowl", new SimpleTrinketItem(Item.Rarity.RARE, "jellyfishbowlbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("jellyfishbowlbuff", new JellyfishBowlBuff());
        MobRegistry.registerMob("jellyfishminion", PetJellyfishMinion.class, false);
        ItemRegistry.registerItem("giantpumpkin", new SimpleTrinketItem(Item.Rarity.RARE, "giantpumpkinbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantpumpkinbuff", new GiantPumpkinBuff());
        MobRegistry.registerMob("pumpkinminion", PetPumpkinMinion.class, false);
        ItemRegistry.registerItem("giantcarrot", new SimpleTrinketItem(Item.Rarity.RARE, "giantcarrotbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantcarrotbuff", new GiantCarrotBuff());
        MobRegistry.registerMob("carrotminion", PetCarrotMinion.class, false);
        ItemRegistry.registerItem("giantpotato", new SimpleTrinketItem(Item.Rarity.RARE, "giantpotatobuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantpotatobuff", new GiantPotatoBuff());
        MobRegistry.registerMob("potatominion", PetPotatoMinion.class, false);
        ItemRegistry.registerItem("magicteapot", new SimpleTrinketItem(Item.Rarity.RARE, "magicteapotbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("magicteapotbuff", new MagicTeaPotBuff());
        MobRegistry.registerMob("teapotminion", PetTeaPotMinion.class, false);
        ItemRegistry.registerItem("giantonion", new SimpleTrinketItem(Item.Rarity.RARE, "giantonionbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantonionbuff", new GiantOnionBuff());
        MobRegistry.registerMob("onionminion", PetOnionMinion.class, false);
        ItemRegistry.registerItem("giantbeet", new SimpleTrinketItem(Item.Rarity.RARE, "giantbeetbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantbeetbuff", new GiantBeetBuff());
        MobRegistry.registerMob("beetminion", PetBeetMinion.class, false);
        // Mob
        ItemRegistry.registerItem("horrorshield", new ShieldTrinketItem(Item.Rarity.RARE, 10, 0.5F, 2000, 100.0F, 0, 360.0F, 400, TrinketsLootTable.trinkets), 400, false);
    }

    public static void registerSummonMounts()
    {
        // T1
        ItemRegistry.registerItem("zombiearrow", new ZombieArrow(Item.Rarity.UNCOMMON), 50, true);
        MobRegistry.registerMob("zombiearchersummonmount", ZombieArcherSummonMount.class, false);

        // T2
        ItemRegistry.registerItem("cavelingminecart", new CavelingMinecartItem(Item.Rarity.RARE), 100, true);
        MobRegistry.registerMob("cavelingminecartmount", CavelingMinecartMount.class, false);
        ItemRegistry.registerItem("minivultureegg", new MiniVultureEgg(Item.Rarity.RARE), 100, true);
        MobRegistry.registerMob("vulturesummonmount", VultureSummonMount.class, false);
        ItemRegistry.registerItem("chieftainhat", new ChieftainHat(Item.Rarity.RARE), 100, true);
        MobRegistry.registerMob("chiefsummonmount", ChiefSummonMount.class, false);
        BuffRegistry.registerBuff("chiefbuff", new MountChiefBuff());
        ItemRegistry.registerItem("magiccheese", new MagicCheese(Item.Rarity.RARE), 100, true);
        MobRegistry.registerMob("mousesummonmount", MouseSummonMount.class, false);

        // T3
        ItemRegistry.registerItem("bouncingbone", new BouncingBone(Item.Rarity.EPIC), 200, true);
        MobRegistry.registerMob("skeletonthrowersummonmount", SkeletonThrowerSummonMount.class, false);

        // T4
        ItemRegistry.registerItem("cryptfangs", new CryptFangs(Item.Rarity.LEGENDARY), 400, true);
        MobRegistry.registerMob("cryptvampiresummonmount", CryptVampireSummonMount.class, false);
    }
}