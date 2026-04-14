package summonerexpansion.codes.registries;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.entity.mobs.buffs.staticBuffs.ShownItemCooldownBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.inventory.item.Item;
import summonerexpansion.items.weapons.*;
import summonerexpansion.items.weapons.base.*;
import summonerexpansion.items.weapons.magic.*;
import summonerexpansion.items.weapons.melee.*;
import summonerexpansion.items.weapons.ranged.*;

import java.awt.*;

public class RegistryWeapons
{
    public static void registerItems()
    {
        RegistryWeapons.registerTier1();
        RegistryWeapons.registerTier2();
        RegistryWeapons.registerTier3();
        RegistryWeapons.registerTier4();
    }

    public static void registerTier1()
    {
        // Minion
        ItemRegistry.registerItem("enchantedbrainonastick", new EnchantedBrainOnAStick(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("magiccopperlamp", new BaseSummonWeapon(5F,25F,1F,100, FollowPosition.CIRCLE_FAR, Item.Rarity.COMMON,"lampminioncopper","magiccopperlamptip"), 50, true);
        ItemRegistry.registerItem("redspiderstaff", new RedSpiderStaff(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("bookbutterfly", new BookButterfly(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("cactusstaff", new BaseSummonWeapon(12F,50F,1F,100, FollowPosition.WALK_CLOSE, Item.Rarity.COMMON,"cactusminion","cactusstafftip"), 50, true);
        ItemRegistry.registerItem("polarhead", new BaseSummonSetWeapon(20F,50F,2F,100, FollowPosition.WALK_CLOSE, Item.Rarity.COMMON,"bearpolarminion","polarheadtip", "frostcrownsetbonus", new Color(87, 189, 216)), 50, true);
        ItemRegistry.registerItem("royalhive", new BaseSummonWeapon(10F,30F,1F,100, FollowPosition.WALK_CLOSE, Item.Rarity.COMMON,"beequeenminion","royalhivetip"), 50, true);
        ItemRegistry.registerItem("bearhead", new BaseSummonSetWeapon(30F,50F,2F,100, FollowPosition.WALK_CLOSE, Item.Rarity.COMMON,"bearminion","bearheadtip", "leathersummonersetbonus", new Color(206, 135, 70)), 50, true);
        // Melee
        ItemRegistry.registerItem("wormbucket", new BaseSummonBoomerangWeapon(10F, 35F, 300, 100, 400,1, 100, Item.Rarity.COMMON, "wormproj"), 50, true);
        ItemRegistry.registerItem("spidersummonsword", new SpiderSummonSword(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("druidleatherclaw", new DruidLeatherClaw(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("druidpolarclaw", new DruidPolarClaw(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("ramnunchucks", new RamNunchucks(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("webspiderwhip", new WebSpiderWhip(100, Item.Rarity.COMMON), 50, true);
        // Ranged
        // Magic
        ItemRegistry.registerItem("iceblossomstaff", new IceBlossomStaff(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("sunflowerstaff", new SunflowerStaff(100, Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("firemonestaff", new FiremoneStaff(100, Item.Rarity.COMMON), 50, true);
        // Throwing
        ItemRegistry.registerItem("leafshotheatpack", new BaseSeedPackWeapon(18, 300, 6, "leafshotheatsentry", "seedpacksentry", "leafshotheatpacktip", Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("leafshotcoldpack", new BaseSeedPackWeapon(20, 300, 6, "leafshotcoldsentry", "seedpacksentry", "leafshotcoldpacktip", Item.Rarity.COMMON), 50, true);
        ItemRegistry.registerItem("leafshotpack", new BaseSeedPackWeapon(22, 300, 6, "leafshotsentry", "seedpacksentry", "leafshotpacktip", Item.Rarity.COMMON), 50, true);
        // Secondary
        ItemRegistry.registerItem("explosivesnowball", new BaseSummonSecondarySetWeapon(20,40,3,6,100,FollowPosition.PYRAMID, Item.Rarity.COMMON, "explosivesnowmanminion", "explosivesnowballtip", "frostcrownsetbonus", new Color(87, 189, 216)), 50, true);
        ItemRegistry.registerItem("magicwoodtools", new BaseMagicToolsWeapon(5,35,30,100, Item.Rarity.COMMON, "woodpickminion", "woodaxeminion", "woodtoolstip"), 50, true);
        ItemRegistry.registerItem("magiccoppertools", new BaseMagicToolsWeapon(7,40,32,100, Item.Rarity.COMMON, "copperpickminion", "copperaxeminion", "coppertoolstip"), 50, true);
        ItemRegistry.registerItem("magicirontools", new BaseMagicToolsWeapon(10,45,34,100, Item.Rarity.COMMON, "ironpickminion", "ironaxeminion", "irontoolstip"), 50, true);
        ItemRegistry.registerItem("magicgoldtools", new BaseMagicToolsWeapon(12,50,36,100, Item.Rarity.COMMON, "goldpickminion", "goldaxeminion", "goldtoolstip"), 50, true);
        ItemRegistry.registerItem("bookmagma", new BaseSummonSecondaryWeapon(22, 35,3,6,100, FollowPosition.SLIME_CIRCLE_MOVEMENT, Item.Rarity.COMMON, "magmaslimeminion", "bookmagmatip"), 50, true);
        ItemRegistry.registerItem("bookbee", new BaseSummonSecondaryWeapon(10, 30, 6, 6, 100, FollowPosition.FLYING_CIRCLE, Item.Rarity.COMMON, "beebookminion", "bookbeetip"), 50, true);
        // Sentry
        ItemRegistry.registerItem("bookmushroom", new BookMushroom(100, Item.Rarity.COMMON), 50, true);
    }

    public static void registerTier2()
    {
        // Minion
        ItemRegistry.registerItem("magicdungeoncandelabra", new BaseSummonWeapon(15F,65F,1F,100, FollowPosition.CIRCLE_FAR, Item.Rarity.UNCOMMON,"lampminiondungeon","lampminiondungeontip"), 100, true);
        ItemRegistry.registerItem("icewizardstaff", new BaseSummonSetWeapon(32F,50F,1F,100, FollowPosition.WALK_CLOSE, Item.Rarity.UNCOMMON,"icewizardminion","icewizardtip", "frostcrownsetbonus", new Color(87, 189, 216)), 100, true);
        ItemRegistry.registerItem("magicgoldlamp", new BaseSummonWeapon(10F,40F,2F,100, FollowPosition.CIRCLE_FAR, Item.Rarity.UNCOMMON,"lampminiongold","magicgoldlamptip"), 100, true);
        ItemRegistry.registerItem("runebonestaff", new RuneboneStaff(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("goldpitchfork", new BaseSummonWeapon(30F,120F,2F,100, FollowPosition.WALK_CLOSE, Item.Rarity.UNCOMMON,"farmerminion","goldpitchforktip"), 100, true);
        ItemRegistry.registerItem("vampirewings", new BaseSummonSetWeapon(20F,65F,1F,100, FollowPosition.WALK_CLOSE, Item.Rarity.UNCOMMON,"vampireminion","vampirewingstip", "bloodplatecowlsetbonus", new Color(180, 15, 50)), 100, true);
        // Melee
        ItemRegistry.registerItem("globeboomerang", new GlobeBoomerang(200, Item.Rarity.COMMON, null), 50, true);
        ItemRegistry.registerItem("druidnecroticclaw", new DruidNecroticClaw(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("druidvultureclaw", new DruidVultureClaw(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("druidspiderclaw", new DruidSpiderClaw(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("druiddemonclaw", new DruidDemonClaw(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("goblinsword", new GoblinSword(200, Item.Rarity.UNCOMMON), 100, true);
        BuffRegistry.registerBuff("goblincooldowndebuff", new ShownItemCooldownBuff(1, true, "items/weapons/goblinsword"));
        // Ranged
        // Magic
        ItemRegistry.registerItem("applewalkingstick", new AppleWalkingStick(200, Item.Rarity.UNCOMMON), 100, true);
        ItemRegistry.registerItem("thornstaff", new ThornStaff(200, Item.Rarity.UNCOMMON), 100, true);
        // Secondary
        ItemRegistry.registerItem("bookfrozen", new BaseSummonSecondarySetWeapon(30,45,2,4,200, FollowPosition.WALK_CLOSE, Item.Rarity.UNCOMMON,"frozendwarfminion","bookfrozentip", "frostcrownsetbonus", new Color(87, 189, 216)), 100, true);
        ItemRegistry.registerItem("bookrunic", new BaseSummonSecondaryWeapon(30,65,2,4,200, FollowPosition.FLYING_CIRCLE, Item.Rarity.UNCOMMON,"runicshieldminion","bookrunictip"), 100, true);
        // Sentry
    }

    public static void registerTier3()
    {
        // Minion
        ItemRegistry.registerItem("magictungstenlamp", new BaseSummonWeapon(25F,45F,1F,100, FollowPosition.CIRCLE_FAR, Item.Rarity.RARE,"lampminiontungsten","magictungstenlamptip"), 200, true);
        ItemRegistry.registerItem("sandwormstaff", new BaseSummonWeapon(50F,150F,2F,100, FollowPosition.FLYING_CIRCLE_FAST, Item.Rarity.RARE,"sandwormheadminion","sandwormstafftip"), 200, true);
        ItemRegistry.registerItem("dryadessence", new BaseSummonWeapon(45F,90F,3F,100, FollowPosition.WALK_CLOSE, Item.Rarity.RARE,"spiritghoulminion","dryadessencetip"), 200, true);
        // Melee
        ItemRegistry.registerItem("druidprimordialclaws", new DruidPrimordialClaws(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("druidancestorclaw", new DruidAncestorClaw(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("fishianspear", new FishianSpear(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("horrorscythe", new HorrorScythe(400, Item.Rarity.RARE), 200, true);
        ItemRegistry.registerItem("horrorglaive", new HorrorGlaive(400, Item.Rarity.RARE), 200, true);
        BuffRegistry.registerBuff("horrorglaivecooldowndebuff", new ShownItemCooldownBuff(1, true, "items/weapons/horrorglaive"));
        ItemRegistry.registerItem("horrorsword", new HorrorSword(400, Item.Rarity.RARE), 200, true);
        // Ranged
        ItemRegistry.registerItem("yinyangninjastar", new YinYangNinjastar(400, null), 200, true);
        ItemRegistry.registerItem("mosquitobow", new MosquitoBow(400, Item.Rarity.RARE), 200, true);
        // Magic
        ItemRegistry.registerItem("pinewoodstaff", new PineWoodStaff(400, Item.Rarity.RARE), 200, true);
        // Secondary
        // Sentry
        ItemRegistry.registerItem("caveglowstaff", new BaseSummonWeapon(35F,80F,1F,400, FollowPosition.WALK_CLOSE, Item.Rarity.RARE,"caveglowsentry","caveglowsentrytip"), 400, true);
        ItemRegistry.registerItem("coffebeampack", new CoffeBeamPack(Item.Rarity.RARE), 200, true);
    }

    public static void registerTier4()
    {
        // Minion
        ItemRegistry.registerItem("magiccastlecandelabra", new BaseSummonWeapon(50F,75F,1F,100, FollowPosition.CIRCLE_FAR, Item.Rarity.EPIC,"lampminioncastle","lampminioncastletip"), 400, true);
        ItemRegistry.registerItem("gemamethystshards", new BaseSummonWeapon(120F,140F,1F,100, FollowPosition.WALK_CLOSE, Item.Rarity.EPIC,"golemamethystminion","amethystshardtip"), 400, true);
        ItemRegistry.registerItem("gemsapphireshards", new BaseSummonWeapon(80F,100F,1F,100, FollowPosition.WALK_CLOSE, Item.Rarity.EPIC,"golemsapphireminion","sapphireshardtip"), 400, true);
        ItemRegistry.registerItem("gememeraldshards", new BaseSummonWeapon(110F,125F,1F,100, FollowPosition.WALK_CLOSE, Item.Rarity.EPIC,"golememeraldminion","emeraldshardtip"), 400, true);
        ItemRegistry.registerItem("gemtopazshards", new BaseSummonWeapon(90F,110F,1F,100, FollowPosition.WALK_CLOSE, Item.Rarity.EPIC,"golemtopazminion","topazshardtip"), 400, true);
        ItemRegistry.registerItem("gemrubyshards", new BaseSummonWeapon(100F,120F,1F,100, FollowPosition.WALK_CLOSE, Item.Rarity.EPIC,"golemrubyminion","rubyshardtip"), 400, true);
        // Melee
        ItemRegistry.registerItem("druidfallendragonclaw", new DruidFallenDragonClaw(800, Item.Rarity.EPIC), 400, true);
        // Ranged
        // Magic
        // Secondary
        // Sentry
        ItemRegistry.registerItem("vampirecoffin", new BaseSummonSetWeapon(60F,80F,1F,800, FollowPosition.WALK_CLOSE, Item.Rarity.EPIC,"coffinsentry","coffinsentrytip", "bloodplatecowlsetbonus", new Color(180, 15, 50)), 400, true);

        // Challenge
        ItemRegistry.registerItem("xmastreescepter", new XmasTreeScepter(800, Item.Rarity.EPIC), 10, true);
    }
}