package summonerexpansion.codes.registries;

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
import necesse.inventory.lootTable.presets.TrinketsLootTable;
import summonerexpansion.items.trinkets.*;
import summonerexpansion.items.trinkets.minions.*;
import summonerexpansion.items.trinkets.trinketsbuffs.*;
import summonerexpansion.items.trinkets.summonedbuffs.*;

import static summonerexpansion.codes.registries.RegistrySummonModifiers.*;

public class RegistryTrinkets
{
    public static void registerItems()
    {
        RegistryTrinkets.registerTier1();
        RegistryTrinkets.registerTier2();
        RegistryTrinkets.registerTier3();
        RegistryTrinkets.registerTier4();
        RegistryTrinkets.registerChallenge();
        RegistryTrinkets.registerFoeTrinkets();
    }

    public static class TrinketBuffs
    {
        // T1
        public static Buff ADAPTIVEALIENSTACKS;
        // T2
        public static Buff GENIECRITSTACKS;
        public static Buff MONKSTACKS;
        // T3
        public static Buff TRINKETMUMMYSUMMON;
        public static Buff TRINKETMUMMYMAGIC;
        // T4

        public TrinketBuffs() {
        }
    }

    public static void registerTier1()
    {
        ItemRegistry.registerItem("mesmercharm", new BaseTrinketItem(Item.Rarity.COMMON, "mesmercharmbuff", 100, TrinketsLootTable.trinkets).addDisables("mesmertablet", "zephyrcharm"), 100, true);
        BuffRegistry.registerBuff("mesmercharmbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue<>(BuffModifiers.STAMINA_CAPACITY, 0.80f)));
        ItemRegistry.registerItem("lightningamulet", new BaseTrinketItem(Item.Rarity.COMMON, "lightningamuletbuff", 100, TrinketsLootTable.trinkets), 100, true);
        BuffRegistry.registerBuff("lightningamuletbuff", new LightningAmuletBuff());
        ItemRegistry.registerItem("timeworncoins", new BaseTrinketItem(Item.Rarity.COMMON, "timeworncoinsbuff", 100, TrinketsLootTable.trinkets), 100, true);
        BuffRegistry.registerBuff("timeworncoinsbuff", new TimewornCoinsBuff());
        ItemRegistry.registerItem("alienparasite", new BaseTrinketItem(Item.Rarity.COMMON, "alienparasitebuff", 100, TrinketsLootTable.trinkets), 100, true);
        BuffRegistry.registerBuff("alienparasitebuff", new AlienParasiteBuff());
        BuffRegistry.registerBuff("adaptivealienbuff", TrinketBuffs.ADAPTIVEALIENSTACKS = new AdaptiveAlienBuff());
        ItemRegistry.registerItem("flowerbrooch", new BaseTrinketItem(Item.Rarity.COMMON, "flowerbroochbuff", 100, TrinketsLootTable.trinkets), 100, true);
        BuffRegistry.registerBuff("flowerbroochbuff", new FlowerBroochBuff());
        ItemRegistry.registerItem("cactusemblem", new BaseTrinketItem(Item.Rarity.COMMON, "cactusemblembuff", 100, TrinketsLootTable.trinkets), 100, true);
        BuffRegistry.registerBuff("cactusemblembuff", new CactusEmblemBuff());
    }

    public static void registerTier2()
    {
        ItemRegistry.registerItem("frozenassassinscowl", new BaseTrinketItem(Item.Rarity.UNCOMMON, "frozenassassinscowlbuff", 200, TrinketsLootTable.trinkets).addDisables("assassinscowl", "frozenwave"), 200, true);
        BuffRegistry.registerBuff("frozenassassinscowlbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, 0.10F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.25F), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 0.50F)));
        ItemRegistry.registerItem("inducingsatchel", new BaseTrinketItem(Item.Rarity.UNCOMMON, "inducingsatchelbuff", 200, TrinketsLootTable.trinkets).addDisables("explorersatchel", "inducingamulet"), 200, true);
        BuffRegistry.registerBuff("inducingsatchelbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.10F), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.30F), new ModifierValue<>(BuffModifiers.SPEED, 0.10F), new ModifierValue<>(BuffModifiers.EMITS_LIGHT, true)));
        ItemRegistry.registerItem("demonicpolarclaw", new BaseTrinketItem(Item.Rarity.UNCOMMON, "demonicpolarclawbuff", 200, TrinketsLootTable.trinkets).addDisables("polarclaw", "demonclaw"), 200, true);
        BuffRegistry.registerBuff("demonicpolarclawbuff", new DemonicPolarClawBuff());
        ItemRegistry.registerItem("duelistdolls", new BaseTrinketItem(Item.Rarity.UNCOMMON, "duelistdollsbuff", 200, TrinketsLootTable.trinkets), 200, true);
        BuffRegistry.registerBuff("duelistdollsbuff", new SimpleTrinketBuff(new ModifierValue<>(SENTRY_ATTACK_SPEED, 0.20F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.20F), new ModifierValue<>(BuffModifiers.TARGET_RANGE, 0.40F)));
        ItemRegistry.registerItem("cowskull", new BaseTrinketItem(Item.Rarity.UNCOMMON, "cowskullbuff", 200, TrinketsLootTable.trinkets), 200, true);
        BuffRegistry.registerBuff("cowskullbuff", new SimpleTrinketBuff(new ModifierValue<>(TRANSFORMATION_SPEED, 0.40F)));
        ItemRegistry.registerItem("geniewish", new BaseTrinketItem(Item.Rarity.UNCOMMON, "geniewishbuff", 200, TrinketsLootTable.trinkets), 200, true);
        BuffRegistry.registerBuff("geniewishbuff", new GenieWishBuff());
        BuffRegistry.registerBuff("geniecritbuff", TrinketBuffs.GENIECRITSTACKS = new GenieCritBuff());
        ItemRegistry.registerItem("overchargedvoidshards", new BaseTrinketItem(Item.Rarity.UNCOMMON, "overchargedvoidshardsbuff", 200, TrinketsLootTable.trinkets), 200, true);
        BuffRegistry.registerBuff("overchargedvoidshardsbuff", new OverchargedVoidShardsBuff());
        ItemRegistry.registerItem("frostsentrycore", new BaseTrinketItem(Item.Rarity.UNCOMMON, "frostsentrycorebuff", 200, TrinketsLootTable.trinkets), 200, true);
        BuffRegistry.registerBuff("frostsentrycorebuff", new FrostSentryCoreBuff());
        ItemRegistry.registerItem("sandtemplemonkhead", new BaseTrinketItem(Item.Rarity.UNCOMMON, "sandtemplemonkheadbuff", 200, TrinketsLootTable.trinkets), 200, true);
        BuffRegistry.registerBuff("sandtemplemonkheadbuff", new SandTempleMonkHeadBuff());
        BuffRegistry.registerBuff("sandtemplemonkheadcooldown", new ShownCooldownBuff());
        BuffRegistry.registerBuff("monkstacksbuff", TrinketBuffs.MONKSTACKS = new MonkStacksBuff());
    }

    public static void registerTier3()
    {
        ItemRegistry.registerItem("essenceofcompanionship", new BaseTrinketItem(Item.Rarity.RARE, "essenceofcompanionshipbuff", 400, TrinketsLootTable.trinkets).addDisables("companionlocket", "essenceofperspective", "essenceofprolonging"), 400, true);
        BuffRegistry.registerBuff("essenceofcompanionshipbuff", new EssenceOfCompanionshipBuff());
        ItemRegistry.registerItem("hystericalmirror", new BaseTrinketItem(Item.Rarity.RARE, "hystericalmirrorbuff", 400, TrinketsLootTable.trinkets).addDisables("scryingmirror", "hysteriatablet", "inducingamulet", "mesmertablet"), 400, true);
        BuffRegistry.registerBuff("hystericalmirrorbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 3), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.30F)));
        ItemRegistry.registerItem("challengerarmorpiece", new BaseTrinketItem(Item.Rarity.RARE, "challengerarmorpiecebuff", 400, TrinketsLootTable.trinkets).addDisables("manica", "claygauntlet", "challengerspauldron"), 400, true);
        BuffRegistry.registerBuff("challengerarmorpiecebuff", new ChallengerArmorPieceBuff());
        ItemRegistry.registerItem("spelltablet", new BaseTrinketItem(Item.Rarity.RARE, "spelltabletbuff", 400, TrinketsLootTable.trinkets).addDisables("spellstone", "hysteriatablet", "inducingamulet", "mesmertablet"), 400, true);
        BuffRegistry.registerBuff("spelltabletbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.MAX_MANA, 0.50F), new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 2), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.30F), new ModifierValue<>(BuffModifiers.MAGIC_ATTACK_SPEED, 0.30F)));
        ItemRegistry.registerItem("shadowhorrorcape", new BaseTrinketItem(Item.Rarity.RARE, "shadowhorrorcapebuff", 400, TrinketsLootTable.trinkets).addDisables("vampiresgift"), 400, true);
        BuffRegistry.registerBuff("shadowhorrorcapebuff", new ShadowHorrorCapeBuff());
        MobRegistry.registerMob("horrorbatminion", TrinketHorrorBatMinion.class, false);
        ItemRegistry.registerItem("galvanicamulet", new BaseTrinketItem(Item.Rarity.RARE, "galvanicamuletbuff", 400, TrinketsLootTable.trinkets).addDisables("lightningamulet"), 400, true);
        BuffRegistry.registerBuff("galvanicamuletbuff", new GalvanicAmuletBuff());
        ItemRegistry.registerItem("bonepile", new BaseTrinketItem(Item.Rarity.RARE, "bonepilebuff", 400, TrinketsLootTable.trinkets).addDisables("bonehilt"), 400, true);
        BuffRegistry.registerBuff("bonepilebuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.ARMOR_PEN_FLAT, 20), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.05F)));
        ItemRegistry.registerItem("strangecookpot", new BaseTrinketItem(Item.Rarity.RARE, "strangecookpotbuff", 400, TrinketsLootTable.trinkets), 400, true);
        BuffRegistry.registerBuff("strangecookpotbuff", new StrangeCookPotBuff());
        ItemRegistry.registerItem("fishianeggs", new BaseTrinketItem(Item.Rarity.RARE, "fishianeggsbuff", 400, TrinketsLootTable.trinkets), 400, true);
        BuffRegistry.registerBuff("fishianeggsbuff", new FishianEggsBuff());
        ItemRegistry.registerItem("summonergambit", new SummonerGambit(400, Item.Rarity.RARE), 400, true);
        BuffRegistry.registerBuff("summonergambitbuff", new SummonerGambitBuff());
    }

    public static void registerTier4()
    {
        ItemRegistry.registerItem("mesmersatchel", new BaseTrinketItem(Item.Rarity.EPIC, "mesmersatchelbuff", 800, TrinketsLootTable.trinkets).addDisables("mesmertablet", "zephyrcharm", "explorersatchel", "inducingamulet", "inducingsatchel", "mesmercharm"), 800, true);
        BuffRegistry.registerBuff("mesmersatchelbuff", new SimpleTrinketBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue<>(BuffModifiers.STAMINA_CAPACITY, 1.00f), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.10F), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.30F), new ModifierValue<>(BuffModifiers.SPEED, 0.10F), new ModifierValue<>(EMITS_SUMMON_LIGHT, true)));
        ItemRegistry.registerItem("companionsatchels", new BaseTrinketItem(Item.Rarity.EPIC, "companionsatchelsbuff", 800, TrinketsLootTable.trinkets).addDisables("mesmersatchel", "essenceofcompanionship", "companionlocket", "essenceofperspective", "essenceofprolonging"), 800, true);
        BuffRegistry.registerBuff("companionsatchelsbuff", new CompanionSatchelsBuff());
        ItemRegistry.registerItem("scryingmagicianscard", new BaseTrinketItem(Item.Rarity.EPIC, "scryingmagicianscardbuff", 800, TrinketsLootTable.trinkets).addDisables("hystericalmirror", "spelltablet", "forbiddenspellbook", "scryingcards", "prophecyslab", "magicmanual", "spellstone", "hysteriatablet", "inducingamulet", "mesmertablet"), 800, true);
        BuffRegistry.registerBuff("scryingmagicianscardbuff", new ScryingMagiciansCardBuff());
        BuffRegistry.registerBuff("mummysummondebuff", TrinketBuffs.TRINKETMUMMYSUMMON = new MummySummonDebuff());
        MobRegistry.registerMob("mummysummonminion", TrinketMummySummonMinion.class, false);
        BuffRegistry.registerBuff("mummymagicdebuff", TrinketBuffs.TRINKETMUMMYMAGIC = new MummyMagicDebuff());
        MobRegistry.registerMob("mummymagicminion", TrinketMummyMagicMinion.class, false);
        ItemRegistry.registerItem("necromancerarmor", new BaseTrinketItem(Item.Rarity.EPIC, "necromancerarmorbuff", 800, TrinketsLootTable.trinkets).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape", "frenzyhorrorcape", "bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw", "necroticclaw"), 800, true);
        BuffRegistry.registerBuff("necromancerarmorbuff", new NecromancerArmorBuff());
        ItemRegistry.registerItem("magicianboard", new BaseTrinketItem(Item.Rarity.EPIC, "magicianboardbuff", 800, TrinketsLootTable.trinkets).addDisables("scryingmagicianscard", "balancedsummonerboard", "balancedfrostfirefoci", "spiritboard", "summonersbestiary"), 800, true);
        BuffRegistry.registerBuff("magicianboardbuff", new MagicianBoardBuff());
        ItemRegistry.registerItem("transplantedheart", new BaseTrinketItem(Item.Rarity.EPIC, "transplantedheartbuff", 800, TrinketsLootTable.trinkets).addDisables("lifependant", "clockworkheart", "frozensoul", "lifeline", "frozenheart"), 800, true);
        BuffRegistry.registerBuff("transplantedheartbuff", new TransplantedHeartBuff());
        ItemRegistry.registerItem("balancedsummonerboard", new BaseTrinketItem(Item.Rarity.EPIC, "balancedsummonerboardbuff", 800, TrinketsLootTable.trinkets).addDisables("balancedfrostfirefoci", "spiritboard", "summonersbestiary"), 800, true);
        BuffRegistry.registerBuff("balancedsummonerboardbuff", new BalancedSummonerBoardBuff());
        ItemRegistry.registerItem("frenzyhorrorcape", new BaseTrinketItem(Item.Rarity.EPIC, "frenzyhorrorcapebuff", 800, TrinketsLootTable.trinkets).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape"), 800, true);
        BuffRegistry.registerBuff("frenzyhorrorcapebuff", new FrenzyHorrorCapeBuff());
        ItemRegistry.registerItem("necroticclaw", new BaseTrinketItem(Item.Rarity.EPIC, "necroticclawbuff", 800, TrinketsLootTable.trinkets).addDisables("bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw"), 800, true);
        BuffRegistry.registerBuff("necroticclawbuff", new NecroticClawBuff());
        ItemRegistry.registerItem("frenzystonering", new BaseTrinketItem(Item.Rarity.EPIC, "frenzystoneringbuff", 800, TrinketsLootTable.trinkets).addDisables("bloodstonering", "frenzyorb"), 800, true);
        BuffRegistry.registerBuff("frenzystoneringbuff", new FrenzystoneRingBuff());
        ItemRegistry.registerItem("crystalamalgamation", new BaseTrinketItem(Item.Rarity.EPIC, "crystalamalgamationbuff", 800, TrinketsLootTable.trinkets), 800, true);
        BuffRegistry.registerBuff("crystalamalgamationbuff", new CrystalAmalgamationBuff());
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
    }

    public static void registerChallenge()
    {
        ItemRegistry.registerItem("doomshroomshield", new BaseTrinketItem(Item.Rarity.RARE, "doomshroomshieldbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("doomshroomshieldcooldown", new ShownCooldownBuff());
        BuffRegistry.registerBuff("doomshroomshieldbuff", new DoomShroomShieldBuff());
        ItemRegistry.registerItem("silvergoblet", new BaseTrinketItem(Item.Rarity.RARE, "silvergobletaurabuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("silvergobletaurabuff", new SilverGobletAuraBuff());
        BuffRegistry.registerBuff("silvergobletbuff", new SilverGobletBuff());
        ItemRegistry.registerItem("jellyfishbowl", new BaseTrinketItem(Item.Rarity.RARE, "jellyfishbowlbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("jellyfishbowlbuff", new JellyfishBowlBuff());
        MobRegistry.registerMob("jellyfishminion", PetJellyfishMinion.class, false);
        BuffRegistry.registerBuff("summonedjellyfishminionbuff", new SummonedJellyfishMinionBuff());
        ItemRegistry.registerItem("giantpumpkin", new BaseTrinketItem(Item.Rarity.RARE, "giantpumpkinbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantpumpkinbuff", new GiantPumpkinBuff());
        MobRegistry.registerMob("pumpkinminion", PetPumpkinMinion.class, false);
        BuffRegistry.registerBuff("summonedpumpkinminionbuff", new SummonedPumpkinMinionBuff());
        ItemRegistry.registerItem("giantcarrot", new BaseTrinketItem(Item.Rarity.RARE, "giantcarrotbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantcarrotbuff", new GiantCarrotBuff());
        MobRegistry.registerMob("carrotminion", PetCarrotMinion.class, false);
        BuffRegistry.registerBuff("summonedcarrotminionbuff", new SummonedCarrotMinionBuff());
        ItemRegistry.registerItem("giantpotato", new BaseTrinketItem(Item.Rarity.RARE, "giantpotatobuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantpotatobuff", new GiantPotatoBuff());
        MobRegistry.registerMob("potatominion", PetPotatoMinion.class, false);
        BuffRegistry.registerBuff("summonedpotatominionbuff", new SummonedPotatoMinionBuff());
        ItemRegistry.registerItem("magicteapot", new BaseTrinketItem(Item.Rarity.RARE, "magicteapotbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("magicteapotbuff", new MagicTeaPotBuff());
        MobRegistry.registerMob("teapotminion", PetTeaPotMinion.class, false);
        BuffRegistry.registerBuff("summonedteapotminionbuff", new SummonedTeaPotMinionBuff());
        ItemRegistry.registerItem("giantonion", new BaseTrinketItem(Item.Rarity.RARE, "giantonionbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantonionbuff", new GiantOnionBuff());
        MobRegistry.registerMob("onionminion", PetOnionMinion.class, false);
        BuffRegistry.registerBuff("summonedonionminionbuff", new SummonedOnionMinionBuff());
        ItemRegistry.registerItem("giantbeet", new BaseTrinketItem(Item.Rarity.RARE, "giantbeetbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantbeetbuff", new GiantBeetBuff());
        MobRegistry.registerMob("beetminion", PetBeetMinion.class, false);
        BuffRegistry.registerBuff("summonedbeetminionbuff", new SummonedBeetMinionBuff());
    }

    public static void registerFoeTrinkets()
    {
        ItemRegistry.registerItem("horrorshield", new ShieldTrinketItem(Item.Rarity.RARE, 10, 0.5F, 2000, 100.0F, 0, 360.0F, 400, TrinketsLootTable.trinkets), 400, false);
    }
}