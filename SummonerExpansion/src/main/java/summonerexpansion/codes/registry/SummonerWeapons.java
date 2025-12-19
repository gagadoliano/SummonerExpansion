package summonerexpansion.codes.registry;

import necesse.engine.registries.ItemRegistry;
import necesse.inventory.item.Item;
import summonerexpansion.items.equips.allweapons.*;
import summonerexpansion.items.equips.allweapons.magicsummon.*;
import summonerexpansion.items.equips.allweapons.meleesummon.*;
import summonerexpansion.items.equips.allweapons.rangedsummon.*;
import summonerexpansion.items.equips.allweapons.supportsummon.XmasTreeScepter;

public class SummonerWeapons
{
    public static void registerSummonWeapons()
    {
        // --Weapons T1--

        // Minion
        ItemRegistry.registerItem("enchantedbrainonastick", new EnchantedBrainOnAStick(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("magiccopperlamp", new MagicCopperLamp(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("redspiderstaff", new RedSpiderStaff(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("cactusstaff", new CactusStaff(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("royalhive", new RoyalHive(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("polarhead", new BearPolarHead(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("bearhead", new BearHead(100, Item.Rarity.COMMON), 50, true);
        // Melee
        ItemRegistry.registerItem("wormbucket", new WormBucket(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("druidleatherclaw", new DruidLeatherClaw(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("druidpolarclaw", new DruidPolarClaw(100, Item.Rarity.COMMON), 50, true);
        // Ranged
        // Magic
        ItemRegistry.registerItem("iceblossomstaff", new IceBlossomStaff(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("sunflowerstaff", new SunflowerStaff(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("firemonestaff", new FiremoneStaff(100, Item.Rarity.COMMON), 50, true);
        // Throwing
        ItemRegistry.registerItem("leafshotheatpack", new LeafShotHeatPack(Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("leafshotcoldpack", new LeafShotColdPack(Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("leafshotpack", new LeafShotPack(Item.Rarity.COMMON), 50, true);
        // Secondary
        ItemRegistry.registerItem("explosivesnowball", new ExplosiveSnowball(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("magiccoppertools", new MagicCopperTools(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("magicirontools", new MagicIronTools(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("magicgoldtools", new MagicGoldTools(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("magicwoodtools", new MagicWoodTools(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("bookmagma", new BookMagma(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("bookbee", new BookBee(100, Item.Rarity.COMMON), 50, true);
        // Sentry
        ItemRegistry.registerItem("bookmushroom", new BookMushroom(100, Item.Rarity.COMMON), 50, true);

        // --Weapons T2--

        // Minion
        ItemRegistry.registerItem("magicdungeoncandelabra", new MagicDungeonCandelabra(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("icewizardstaff", new IceWizardStaff(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("magicgoldlamp", new MagicGoldLamp(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("runebonestaff", new RuneboneStaff(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("goldpitchfork", new GoldPitchfork(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("vampirewings", new VampireWings(200, Item.Rarity.UNCOMMON), 100, true);
        // Melee
        ItemRegistry.registerItem("goblinsword", new GoblinSword(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("druiddemonclaw", new DruidDemonClaw(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("druidspiderclaw", new DruidSpiderClaw(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("druidnecroticclaw", new DruidNecroticClaw(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("druidvultureclaw", new DruidVultureClaw(200, Item.Rarity.UNCOMMON), 100, true);
        // Ranged
        // Secondary
        ItemRegistry.registerItem("bookfrozen", new BookFrozen(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("bookrunic", new BookRunic(200, Item.Rarity.UNCOMMON), 100, true);
        // Sentry

        // --Weapons T3--

        // Minion
        ItemRegistry.registerItem("magiccastlecandelabra", new MagicCastleCandelabra(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("magictungstenlamp", new MagicTungstenLamp(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("sandwormstaff", new SandWormStaff(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("dryadessence", new DryadEssence(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("yinyangninjastar", new YinYangNinjastar(400, null), 200, true);
        // Melee
        ItemRegistry.registerItem("fishianspear", new FishianSpear(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("horrorscythe", new HorrorScythe(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("horrorglaive", new HorrorGlaive(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("horrorsword", new HorrorSword(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("druidancestorclaw", new DruidAncestorClaw(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("druidprimordialclaws", new DruidPrimordialClaws(400, Item.Rarity.RARE), 200, true);
        // Ranged
        ItemRegistry.registerItem("mosquitobow", new MosquitoBow(400, Item.Rarity.RARE), 200, true);
        // Secondary
        // Sentry

        // --Weapons T4--

        // Minion
        ItemRegistry.registerItem("gemamethystshards", new GemAmethystShards(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("gemsapphireshards", new GemSapphireShards(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("gememeraldshards", new GemEmeraldShards(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("gemtopazshards", new GemTopazShards(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("gemrubyshards", new GemRubyShards(800, Item.Rarity.EPIC), 400, true);
        // Melee
        ItemRegistry.registerItem("druidfallendragonclaw", new DruidFallenDragonClaw(800, Item.Rarity.EPIC), 400, true);
        // Ranged
        // Secondary
        // Sentry
        ItemRegistry.registerItem("caveglowstaff", new CaveglowStaff(800, Item.Rarity.EPIC), 400, true);
        ItemRegistry.registerItem("vampirecoffin", new VampireCoffin(800, Item.Rarity.EPIC), 400, true);
        // Challenge
        ItemRegistry.registerItem("xmastreescepter", new XmasTreeScepter(800, Item.Rarity.EPIC), 10, true);
    }
}