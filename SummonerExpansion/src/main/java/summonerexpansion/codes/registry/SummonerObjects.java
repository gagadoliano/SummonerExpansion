package summonerexpansion.codes.registry;

import necesse.engine.localization.Localization;
import necesse.engine.registries.ObjectRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.level.gameObject.*;
import necesse.level.gameObject.container.CoolingBoxInventoryObject;
import necesse.level.gameObject.container.DisplayStandObject;
import necesse.level.gameObject.container.StorageBoxInventoryObject;
import summonerexpansion.objects.summonobjects.*;

import java.awt.*;

public class SummonerObjects
{
    public static void registerSummonObjects()
    {
        SummonerObjects.registerSummonCrafting();
        SummonerObjects.registerSummonBuffAuras();
        SummonerObjects.registerSummonEntityObjects();
        SummonerObjects.registerSummonTraps();
        SummonerObjects.registerSummonRocks();
        SummonerObjects.registerSummonPlants();
        SummonerObjects.registerSummonFurniture();
        SummonerObjects.registerSummonWalls();
    }

    public static void registerSummonCrafting()
    {
        // Workbench
        ObjectRegistry.registerObject("summoningbookshelf", new SummoningBookshelf(), 50, true);
        ArcanicConverter.registerArcanicConverter("arcanicconverter", true, true);

        // Duo Workbench
        SummoningTableDuo.registerSummoningTable();
        DemonicSummoningTableDuo.registerDemonicSummoningTable();
        TungstenSummoningTableDuo.registerTungstenSummoningTable();
        FallenSummoningTableDuo.registerFallenSummoningTable();
    }

    public static void registerSummonBuffAuras()
    {
        // Auras
        ObjectRegistry.registerObject("bannerofwater", new BannerOfWater(), 100, true);

        // Lights
        ObjectRegistry.registerObject("caveglowlamp", new CaveglowLamp(), 80, true);
    }

    public static void registerSummonEntityObjects()
    {
        // Dummy
        ObjectRegistry.registerObject("tanktrainingdummy", new TankTrainingDummyObject(), 50, true);
    }

    public static void registerSummonTraps()
    {
        // Glyphs
        ObjectRegistry.registerObject("glyphtrapheal", new HealGlyphTrapObject(), 50, true);
    }

    public static void registerSummonRocks()
    {
        // Rocks
        Color titaniumMapColor = new Color(207, 207, 207);
        String[] titaniumRocksCategory = new String[]{"objects", "landscaping", "forestrocksandores"};
        String[] titaniumSwampRocksCategory = new String[]{"objects", "landscaping", "swamprocksandores"};
        ObjectRegistry.registerObject("titaniumrockoremedium", new SingleOreRockSmall("stone", 4.0F, "titaniumrockmedium", "titaniumore", "rockoremedium", "rockoremedium_titanium", titaniumMapColor, "titaniumore", 2, 10, 5, titaniumRocksCategory), 0F, true);
        ObjectRegistry.registerObject("titaniumrockoresmall", new SingleOreRockSmall("stone", 4.0F, "titaniumrocksmall", "titaniumore", "rockoresmall", "rockoresmall_titanium", titaniumMapColor, "titaniumore", 1, 5, 5, titaniumRocksCategory), 0F, true);
        ObjectRegistry.registerObject("titaniumrockoreswampmedium", new SingleOreRockSmall("swampstone", 4.0F, "titaniumrockswampmedium", "titaniumore", "rockoreswampmedium", "rockoremedium_titanium", titaniumMapColor, "titaniumore", 2, 10, 5, titaniumSwampRocksCategory), 0F, true);
        ObjectRegistry.registerObject("titaniumrockoreswampsmall", new SingleOreRockSmall("swampstone", 4.0F, "titaniumrockswampsmall", "titaniumore", "rockoreswampsmall", "rockoresmall_titanium", titaniumMapColor, "titaniumore", 1, 5, 5, titaniumSwampRocksCategory), 0F, true);
    }

    public static void registerSummonPlants()
    {
        // Grass
        ObjectRegistry.registerObject("ancienttallgrass", new AncientTallGrassObject(), 0F, true);
        ObjectRegistry.registerObject("ancientwatergrass", new CaveWaterPlantObject("ancientwatergrass", 32, new Color(90, 123, 2)), 1F, true);

        // Saplings
        ObjectRegistry.registerObject("ancienttree", new AncientTreeObject("ancienttree", "ancientlog", "ancienttreesapling", new Color(73, 28, 45), 45, 60, 110, "ancienttreeleaves"), 0.0F, false, false, true);
        ObjectRegistry.registerObject("ancienttreesapling", new TreeSaplingObject("ancienttreesapling", "ancienttree", 1500, 2000, true), 5.0F, true);
        ObjectRegistry.registerObject("overgrowthorns", (new FruitBushObject("overgrowthorns", "overgrowthornssapling", 900.0F, 1800.0F, "thorns", 5.0F, 2, new Color(191, 90, 62))).setDebrisColor(new Color(114, 28, 3)), 0F, false, false, true);
        ObjectRegistry.registerObject("overgrowthornssapling", new SaplingObject("overgrowthornssapling", "overgrowthorns", 1000, 2000, false, "mudtile")
        {
            public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
            {
                ListGameTooltips tooltips = new ListGameTooltips();
                tooltips.add(Localization.translate("itemtooltip", "overgrowthornstip"));
                return tooltips;
            }
        }, 30, true);

        // Roots
        AncientRootObject.registerAncientRoot();
    }

    public static void registerSummonFurniture()
    {
        // Statues
        ObjectRegistry.registerObject("mushroomminionstatue", new StatueObject("mushroomminionstatue"), 10.0F, true);
        ObjectRegistry.registerObject("apprendicestatue", new StatueObject("apprendicestatue"), 10.0F, true);
        ObjectRegistry.registerObject("fakevultureegg", new StatueObject("fakevultureegg"), 10.0F, true);
        ObjectRegistry.registerObject("beetstatue", new StatueObject("beetstatue"), 10.0F, true);
        AscendedWizardStatueObject.registerAscendedWizardStatue("ascendedwizardstatue", true);

        // Decorations
        ObjectRegistry.registerObject("woodenidol", new TableDecorationObject("woodenidol", new Color(153, 121, 81), 16, 18, 0, 0), 1F, true);
        ObjectRegistry.registerObject("stuffedfrog", new TableDecorationObject("stuffedfrog", new Color(97, 105, 57), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedmouse", new TableDecorationObject("stuffedmouse", new Color(66, 45, 36), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedlocust", new TableDecorationObject("stuffedlocust", new Color(138, 140, 18), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedmosquito", new TableDecorationObject("stuffedmosquito", new Color(71, 68, 36), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedspider", new TableDecorationObject("stuffedspider", new Color(74, 74, 74), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedbird", new TableDecorationObject("stuffedbird", new Color(219, 219, 219), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedbluebird", new TableDecorationObject("stuffedbluebird", new Color(54, 94, 142), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedcanary", new TableDecorationObject("stuffedcanary", new Color(173, 37, 37), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedcardinal", new TableDecorationObject("stuffedcardinal", new Color(66, 45, 36), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedchick", new TableDecorationObject("stuffedchick", new Color(243, 201, 42), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedfrostsentry", new TableDecorationObject("stuffedfrostsentry", new Color(121, 201, 224), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedgoblin", new TableDecorationObject("stuffedgoblin", new Color(120, 150, 79), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedportalminion", new TableDecorationObject("stuffedportalminion", new Color(54, 52, 52), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedrabbit", new TableDecorationObject("stuffedrabbit", new Color(116, 86, 61), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedscorpion", new TableDecorationObject("stuffedscorpion", new Color(33, 45, 58), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedsquirrel", new TableDecorationObject("stuffedsquirrel", new Color(112, 63, 36), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedswampslug", new TableDecorationObject("stuffedswampslug", new Color(97, 105, 57), 12, 10, 0, -2), 1F, true);

        // Furniture
        Color honeyMapColor = new Color(186, 136, 46);
        ObjectRegistry.registerObject("beehivechest", new BeehiveChestInventoryObject("beehivechest", 20, honeyMapColor), 10, true);
        Color arcanicMapColor = new Color(144, 143, 155);
        ObjectRegistry.registerObject("arcanicdisplay", new DisplayStandObject("arcanicdisplay", arcanicMapColor, 20, "objects", "furniture"), 10, true);
        ObjectRegistry.registerObject("arcanicchest", new StorageBoxInventoryObject("arcanicchest", 80, arcanicMapColor, "objects", "furniture")
        {
            public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
            {
                ListGameTooltips tooltips = new ListGameTooltips();
                tooltips.add(Localization.translate("itemtooltip", "arcanicchesttip"));
                return tooltips;
            }
        }, 10F, true);
    }

    public static void registerSummonWalls()
    {
        // Fences
        int ancientWoodFenceID = ObjectRegistry.registerObject("ancientwoodfence", new FenceObject("ancientwoodfence", new Color(88, 38, 56), 12, 10, -26), 0F, true);
        FenceGateObject.registerGatePair(ancientWoodFenceID, "ancientwoodfencegate", "ancientwoodfencegate", new Color(88, 38, 56), 12, 10, 0F);

        // Walls
        RockObject empoweredrubyrock;
        ObjectRegistry.registerObject("empoweredrubyrock", empoweredrubyrock = new RockObject("empoweredrubyrock", new Color(87, 49, 59), "ruby", 0, 3, 1), 1F, true);
        empoweredrubyrock.toolTier = 8F;
    }
}