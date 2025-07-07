package summonerexpansion.summonothers;

import necesse.engine.localization.message.GameMessage;
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
        ItemRegistry.registerItem("mesmercharm", (new SimpleTrinketItem(Item.Rarity.RARE, "mesmercharmbuff", 100)).addDisables("mesmertablet", "zephyrcharm"), 50, true);
        BuffRegistry.registerBuff("mesmercharmbuff", new SimpleTrinketBuff("mesmercharmtip", new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.STAMINA_CAPACITY, 0.80f)));
        ItemRegistry.registerItem("cactusemblem", new CactusEmblem(), 50, true);
        BuffRegistry.registerBuff("cactusemblembuff", new CactusEmblemBuff());
        // T2
        BuffRegistry.registerBuff("frozenassassinscowlbuff", new SimpleTrinketBuff("frozenassassinscowltip", new ModifierValue(BuffModifiers.SUMMON_CRIT_CHANCE, 0.15F), new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.20F), new ModifierValue(BuffModifiers.SUMMONS_TARGET_RANGE, 0.20F)));
        ItemRegistry.registerItem("essenceofcompanionship", new SimpleTrinketItem(Item.Rarity.RARE, "essenceofcompanionshipbuff", 200).addDisables("companionlocket", "essenceofperspective", "essenceofprolonging"), 100, true);
        ItemRegistry.registerItem("frozenassassinscowl", (new SimpleTrinketItem(Item.Rarity.RARE, "frozenassassinscowlbuff", 200)).addDisables("assassinscowl", "frozenwave"), 100, true);
        ItemRegistry.registerItem("demonicpolarclaw", (new DemonicPolarClaw()).addDisables("polarclaw", "demonclaw"), 200, true);
        BuffRegistry.registerBuff("essenceofcompanionshipbuff", new EssenceOfCompanionshipBuff());
        BuffRegistry.registerBuff("demonicpolarclawbuff", new DemonicPolarClawBuff());
        // T3
        BuffRegistry.registerBuff("spelltabletbuff", new SimpleTrinketBuff("spelltablettip", new ModifierValue(BuffModifiers.MAX_MANA, 0.60F), new ModifierValue(BuffModifiers.MAX_SUMMONS, 2), new ModifierValue(BuffModifiers.SUMMON_ATTACK_SPEED, 0.30F), new ModifierValue(BuffModifiers.MAGIC_ATTACK_SPEED, 0.30F)));
        BuffRegistry.registerBuff("inducingsatchelbuff", new SimpleTrinketBuff("inducingsatcheltip", new ModifierValue(BuffModifiers.SUMMONS_SPEED, 0.40F), new ModifierValue(BuffModifiers.SPEED, 0.20F), new ModifierValue(BuffModifiers.EMITS_LIGHT, true)));
        ItemRegistry.registerItem("spelltablet", (new SimpleTrinketItem(Item.Rarity.RARE, "spelltabletbuff", 400)).addDisables("spellstone", "hysteriatablet", "inducingamulet", "mesmertablet"), 200, true);
        ItemRegistry.registerItem("hystericalmirror", (new SimpleTrinketItem(Item.Rarity.RARE, "hystericalmirrorbuff", 400)).addDisables("scryingmirror", "hysteriatablet", "inducingamulet", "mesmertablet"), 200, true);
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
        // T4
        BuffRegistry.registerBuff("mesmersatchelbuff", new SimpleTrinketBuff("mesmersatcheltip", new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.STAMINA_CAPACITY, 1.00f), new ModifierValue(BuffModifiers.SUMMONS_SPEED, 0.50F), new ModifierValue(BuffModifiers.SPEED, 0.25F), new ModifierValue(BuffModifiers.EMITS_LIGHT, true)));
        ItemRegistry.registerItem("necromancerarmor", (new NecromancerArmor()).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape", "frenzyhorrorcape", "bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw", "necroticclaw"), 500, true);
        ItemRegistry.registerItem("mesmersatchel", (new SimpleTrinketItem(Item.Rarity.RARE, "mesmersatchelbuff", 800)).addDisables("mesmertablet", "zephyrcharm", "explorersatchel", "inducingamulet", "inducingsatchel", "mesmercharm"), 500, true);
        ItemRegistry.registerItem("frenzyhorrorcape", (new FrenzyHorrorCape()).addDisables("bloodstonering", "frenzyorb", "frenzystonering", "shadowhorrorcape"), 500, true);
        ItemRegistry.registerItem("necroticclaw", (new NecroticClaw()).addDisables("bonehilt", "bonepile", "demonicpolarclaw", "polarclaw", "demonclaw"), 500, true);
        ItemRegistry.registerItem("frenzystonering", (new FrenzystoneRing()).addDisables("bloodstonering", "frenzyorb"), 500, true);
        ItemRegistry.registerItem("littleangel", new LittleAngel(), 500, true);
        BuffRegistry.registerBuff("frenzyhorrorcapebuff", new FrenzyHorrorCapeBuff());
        BuffRegistry.registerBuff("necromancerarmorbuff", new NecromancerArmorBuff());
        BuffRegistry.registerBuff("frenzystoneringbuff", new FrenzystoneRingBuff());
        BuffRegistry.registerBuff("necroticclawbuff", new NecroticClawBuff());
        BuffRegistry.registerBuff("littleangelbuff", new LittleAngelBuff());

        // Challenge
        ItemRegistry.registerItem("giantbeet", new SimpleTrinketItem(Item.Rarity.RARE, "giantbeetbuff", 100), 50, true);
        BuffRegistry.registerBuff("giantbeetbuff", new GiantBeetBuff());
        ItemRegistry.registerItem("jellyfishbowl", new SimpleTrinketItem(Item.Rarity.RARE, "jellyfishbowlbuff", 100), 50, true);
        BuffRegistry.registerBuff("jellyfishbowlbuff", new JellyfishBowlBuff());

        // Mounts
        ItemRegistry.registerItem("chieftainhat", new ChieftainHat(), 50, true);
        MobRegistry.registerMob("chiefsummonmount", ChiefSummonMount.class, false);
        BuffRegistry.registerBuff("chiefbuff", new ChiefBuff());

        ItemRegistry.registerItem("magiccheese", new MagicCheese(), 50, true);
        MobRegistry.registerMob("mousesummonmount", MouseSummonMount.class, false);

        ItemRegistry.registerItem("cavelingminecart", new CavelingMinecartItem(), 50, true);
        MobRegistry.registerMob("cavelingminecart", CavelingMinecart.class, false);
        MobRegistry.registerMob("cavelingminecartmount", MinecartMountMob.class, false, false, new LocalMessage("mob", "cavelingminecart"), null);
    }
}