package summonerexpansion.summonothers;

import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.SimpleTrinketBuff;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MinecartMountMob;
import necesse.inventory.item.Item;
import necesse.inventory.item.trinketItem.SimpleTrinketItem;
import necesse.inventory.lootTable.presets.TrinketsLootTable;
import summonerexpansion.summonarmor.*;
import summonerexpansion.summonmounts.*;
import summonerexpansion.summonsetbonus.*;
import summonerexpansion.summontrinket.*;
import summonerexpansion.summontrinketbuffs.*;

public class SummonerEquips
{
    public static void registerSummonEquips()
    {
        // --Armors--

        // T1
        ItemRegistry.registerItem("bloodplatemask", new BloodplateMask(), 50, true);
        ItemRegistry.registerItem("frostcrown", new FrostCrown(), 50, true);
        BuffRegistry.registerBuff("frostcrownsetbonus", new FrostCrownSetBonus());
        ItemRegistry.registerItem("copperminerhat", new CopperMinerHat(), 50, true);
        BuffRegistry.registerBuff("copperminersetbonus", new CopperMinerSetBonus());
        ItemRegistry.registerItem("leathersummonerhood", new LeatherSummonerHood(), 50, true);
        BuffRegistry.registerBuff("leathersummonersetbonus", new LeatherSummonerSetBonus());
        ItemRegistry.registerItem("redspiderhelmet", new RedSpiderHelmet(), 50, true);
        ItemRegistry.registerItem("redspiderchestplate", new RedSpiderChestplate(), 50, true);
        ItemRegistry.registerItem("redspiderboots", new RedSpiderBoots(), 50, true);
        BuffRegistry.registerBuff("redspidersetbonus", new RedSpiderSetBonus());
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
        //BuffRegistry.registerBuff("", new ());

        // --Trinkets--
        // T1
        ItemRegistry.registerItem("mesmercharm", new SimpleTrinketItem(Item.Rarity.RARE, "mesmercharmbuff", 100, TrinketsLootTable.trinkets).addDisables("mesmertablet", "zephyrcharm"), 50, true);
        BuffRegistry.registerBuff("mesmercharmbuff", new SimpleTrinketBuff("mesmercharmtip", new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.STAMINA_CAPACITY, 0.80f)));
        ItemRegistry.registerItem("cactusemblem", new SimpleTrinketItem(Item.Rarity.RARE, "cactusemblembuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("cactusemblembuff", new CactusEmblemBuff());
        // T2
        ItemRegistry.registerItem("frozenassassinscowl", new SimpleTrinketItem(Item.Rarity.RARE, "frozenassassinscowlbuff", 200, TrinketsLootTable.trinkets).addDisables("assassinscowl", "frozenwave"), 100, true);
        BuffRegistry.registerBuff("frozenassassinscowlbuff", new SimpleTrinketBuff("frozenassassinscowltip", new ModifierValue(BuffModifiers.SUMMON_CRIT_CHANCE, 0.15F), new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.20F), new ModifierValue(BuffModifiers.SUMMONS_TARGET_RANGE, 0.20F)));
        ItemRegistry.registerItem("essenceofcompanionship", new SimpleTrinketItem(Item.Rarity.RARE, "essenceofcompanionshipbuff", 200, TrinketsLootTable.trinkets).addDisables("companionlocket", "essenceofperspective", "essenceofprolonging"), 100, true);
        BuffRegistry.registerBuff("essenceofcompanionshipbuff", new EssenceOfCompanionshipBuff());
        ItemRegistry.registerItem("demonicpolarclaw", new SimpleTrinketItem(Item.Rarity.RARE, "demonicpolarclawbuff", 200, TrinketsLootTable.trinkets).addDisables("polarclaw", "demonclaw"), 100, true);
        BuffRegistry.registerBuff("demonicpolarclawbuff", new DemonicPolarClawBuff());
        // T3
        ItemRegistry.registerItem("spelltablet", new SimpleTrinketItem(Item.Rarity.RARE, "spelltabletbuff", 400, TrinketsLootTable.trinkets).addDisables("spellstone", "hysteriatablet", "inducingamulet", "mesmertablet"), 200, true);
        BuffRegistry.registerBuff("spelltabletbuff", new SimpleTrinketBuff("spelltablettip", new ModifierValue(BuffModifiers.MAX_MANA, 0.60F), new ModifierValue(BuffModifiers.MAX_SUMMONS, 2), new ModifierValue(BuffModifiers.SUMMON_ATTACK_SPEED, 0.30F), new ModifierValue(BuffModifiers.MAGIC_ATTACK_SPEED, 0.30F)));
        ItemRegistry.registerItem("inducingsatchel", new SimpleTrinketItem(Item.Rarity.RARE, "inducingsatchelbuff", 400, TrinketsLootTable.trinkets).addDisables("explorersatchel", "inducingamulet"), 200, true);
        BuffRegistry.registerBuff("inducingsatchelbuff", new SimpleTrinketBuff("inducingsatcheltip", new ModifierValue(BuffModifiers.SUMMONS_SPEED, 0.40F), new ModifierValue(BuffModifiers.SPEED, 0.20F), new ModifierValue(BuffModifiers.EMITS_LIGHT, true)));
        ItemRegistry.registerItem("hystericalmirror", new SimpleTrinketItem(Item.Rarity.RARE, "hystericalmirrorbuff", 400, TrinketsLootTable.trinkets).addDisables("scryingmirror", "hysteriatablet", "inducingamulet", "mesmertablet"), 200, true);
        BuffRegistry.registerBuff("hystericalmirrorbuff", new SimpleTrinketBuff("hystericalmirrortip", new ModifierValue(BuffModifiers.MAX_SUMMONS, 3), new ModifierValue(BuffModifiers.SUMMONS_SPEED, 0.45F)));
        ItemRegistry.registerItem("challengerarmorpiece", new SimpleTrinketItem(Item.Rarity.RARE, "challengerarmorpiecebuff", 400, TrinketsLootTable.trinkets).addDisables("manica", "claygauntlet", "challengerspauldron"), 200, true);
        BuffRegistry.registerBuff("challengerarmorpiecebuff", new ChallengerArmorPieceBuff());
        ItemRegistry.registerItem("bonepile", new SimpleTrinketItem(Item.Rarity.RARE, "bonepilebuff", 400, TrinketsLootTable.trinkets).addDisables("bonehilt"), 200, true);
        BuffRegistry.registerBuff("bonepilebuff", new SimpleTrinketBuff("bonepiletip", new ModifierValue(BuffModifiers.ARMOR_PEN_FLAT, 25), new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.25F)));
        ItemRegistry.registerItem("shadowhorrorcape", new SimpleTrinketItem(Item.Rarity.RARE, "shadowhorrorcapebuff", 400, TrinketsLootTable.trinkets).addDisables("vampiresgift"), 200, true);
        BuffRegistry.registerBuff("shadowhorrorcapebuff", new ShadowHorrorCapeBuff());
        ItemRegistry.registerItem("doomshroomshield", new SimpleTrinketItem(Item.Rarity.RARE, "doomshroomshieldbuff", 400, TrinketsLootTable.trinkets), 200, true);
        BuffRegistry.registerBuff("doomshroomshieldcooldown", new ShownCooldownBuff());
        BuffRegistry.registerBuff("doomshroomshieldbuff", new DoomShroomShieldBuff());
        ItemRegistry.registerItem("summonergambit", new SummonerGambit(), 200, true);
        BuffRegistry.registerBuff("summonergambitbuff", new SummonerGambitBuff());
        // T4
        ItemRegistry.registerItem("mesmersatchel", new SimpleTrinketItem(Item.Rarity.RARE, "mesmersatchelbuff", 800, TrinketsLootTable.trinkets).addDisables("mesmertablet", "zephyrcharm", "explorersatchel", "inducingamulet", "inducingsatchel", "mesmercharm"), 500, true);
        BuffRegistry.registerBuff("mesmersatchelbuff", new SimpleTrinketBuff("mesmersatcheltip", new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.STAMINA_CAPACITY, 1.00f), new ModifierValue(BuffModifiers.SUMMONS_SPEED, 0.50F), new ModifierValue(BuffModifiers.SPEED, 0.25F), new ModifierValue(BuffModifiers.EMITS_LIGHT, true)));
        ItemRegistry.registerItem("necromancerarmor", new SimpleTrinketItem(Item.Rarity.RARE, "necromancerarmorbuff", 800, TrinketsLootTable.trinkets).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape", "frenzyhorrorcape", "bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw", "necroticclaw"), 500, true);
        BuffRegistry.registerBuff("necromancerarmorbuff", new NecromancerArmorBuff());
        ItemRegistry.registerItem("frenzyhorrorcape", new SimpleTrinketItem(Item.Rarity.RARE, "frenzyhorrorcapebuff", 800, TrinketsLootTable.trinkets).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape"), 500, true);
        BuffRegistry.registerBuff("frenzyhorrorcapebuff", new FrenzyHorrorCapeBuff());
        ItemRegistry.registerItem("necroticclaw", new SimpleTrinketItem(Item.Rarity.RARE, "necroticclawbuff", 800, TrinketsLootTable.trinkets).addDisables("bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw"), 500, true);
        BuffRegistry.registerBuff("necroticclawbuff", new NecroticClawBuff());
        ItemRegistry.registerItem("frenzystonering", new SimpleTrinketItem(Item.Rarity.RARE, "frenzystoneringbuff", 800, TrinketsLootTable.trinkets).addDisables("bloodstonering", "frenzyorb"), 500, true);
        BuffRegistry.registerBuff("frenzystoneringbuff", new FrenzystoneRingBuff());
        ItemRegistry.registerItem("balancedsummonerboard", new SimpleTrinketItem(Item.Rarity.RARE, "balancedsummonerboardbuff", 800, TrinketsLootTable.trinkets).addDisables("balancedfrostfirefoci", "spiritboard", "summonersbestiary"), 500, true);
        BuffRegistry.registerBuff("balancedsummonerboardbuff", new BalancedSummonerBoardBuff());
        ItemRegistry.registerItem("transplantedheart", new SimpleTrinketItem(Item.Rarity.RARE, "transplantedheartbuff", 800, TrinketsLootTable.trinkets).addDisables("lifependant", "clockworkheart", "frozensoul", "lifeline", "frozenheart"), 500, true);
        BuffRegistry.registerBuff("transplantedheartbuff", new TransplantedHeartBuff());
        ItemRegistry.registerItem("scryingmagicianscard", new SimpleTrinketItem(Item.Rarity.RARE, "scryingmagicianscardbuff", 800, TrinketsLootTable.trinkets).addDisables("hystericalmirror", "spelltablet", "forbiddenspellbook", "scryingcards", "prophecyslab", "magicmanual", "spellstone", "hysteriatablet", "inducingamulet", "mesmertablet"), 500, true);
        BuffRegistry.registerBuff("scryingmagicianscardbuff", new ScryingMagiciansCardBuff());
        ItemRegistry.registerItem("littleangel", new LittleAngel(), 500, true);
        BuffRegistry.registerBuff("littleangelbuff", new LittleAngelBuff());
        ItemRegistry.registerItem("calmminerslantern", new CalmMinerLantern().addDisables("miningcharm", "calmingrose", "minersbouquet", "calmingminersbouquet", "toolbox", "diggingclaw", "minersprosthetic", "willowisplantern", "constructionhammer", "telescopicladder", "toolextender", "itemattractor"), 500, true);
        BuffRegistry.registerBuff("calmminerslanternbuff", new CalmMinersLanternBuff());
        ItemRegistry.registerItem("minerslantern", new MinerLantern().addDisables("miningcharm", "calmingrose", "minersbouquet", "calmingminersbouquet", "toolbox", "diggingclaw", "minersprosthetic", "willowisplantern", "constructionhammer", "telescopicladder", "toolextender", "itemattractor"), 500, true);
        BuffRegistry.registerBuff("minerslanternbuff", new MinersLanternBuff());

        // Challenge
        ItemRegistry.registerItem("jellyfishbowl", new SimpleTrinketItem(Item.Rarity.RARE, "jellyfishbowlbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("jellyfishbowlbuff", new JellyfishBowlBuff());
        ItemRegistry.registerItem("giantbeet", new SimpleTrinketItem(Item.Rarity.RARE, "giantbeetbuff", 100, TrinketsLootTable.trinkets), 50, true);
        BuffRegistry.registerBuff("giantbeetbuff", new GiantBeetBuff());

        // Mounts
        ItemRegistry.registerItem("chieftainhat", new ChieftainHat(), 50, true);
        MobRegistry.registerMob("chiefsummonmount", ChiefSummonMount.class, false);
        BuffRegistry.registerBuff("chiefbuff", new ChiefBuff());
        ItemRegistry.registerItem("magiccheese", new MagicCheese(), 50, true);
        MobRegistry.registerMob("mousesummonmount", MouseSummonMount.class, false);
        ItemRegistry.registerItem("cavelingminecart", new CavelingMinecartItem(), 50, true);
        MobRegistry.registerMob("cavelingminecartmount", MinecartMountMob.class, false, false, new LocalMessage("mob", "cavelingminecart"), null);
        MobRegistry.registerMob("cavelingminecart", CavelingMinecart.class, false);
    }
}