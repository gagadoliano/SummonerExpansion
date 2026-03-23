package summonerexpansion.codes.registries;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.*;
import summonerexpansion.buffs.*;
import summonerexpansion.objects.auras.*;
import summonerexpansion.objects.crafting.*;
import summonerexpansion.objects.decoration.*;
import summonerexpansion.objects.nature.*;
import summonerexpansion.objects.storages.*;
import summonerexpansion.objects.traps.*;

import java.awt.*;

public class RegistryObjects
{
    public static void registerObjects()
    {
        RegistryObjects.registerSummonCrafting();
        RegistryObjects.registerBuffAuras();
        RegistryObjects.registerRocks();
        RegistryObjects.registerTraps();
        RegistryObjects.registerPlants();
        RegistryObjects.registerFurniture();
    }

    public static class ObjectBuffs
    {
        public static Buff HEALGLYPH;
        public static Buff WATERBANNERBOOST;
        public static Buff CAVEGLOWLAMPBOOST;
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

    public static void registerBuffAuras()
    {
        // Auras
        ObjectRegistry.registerObject("bannerofwater", new BannerOfWater(), 100, true);
        BuffRegistry.registerBuff("bannerofwaterbuff", ObjectBuffs.WATERBANNERBOOST = new WaterBannerBuff());
        // Lights
        ObjectRegistry.registerObject("caveglowlamp", new CaveglowLamp(), 80, true);
        BuffRegistry.registerBuff("caveglowlampbuff", ObjectBuffs.CAVEGLOWLAMPBOOST = new CaveglowLampBuff());
    }

    public static void registerTraps()
    {
        // Glyphs
        ObjectRegistry.registerObject("glyphtrapheal", new HealGlyphTrapObject(), 50, true);
        BuffRegistry.registerBuff("healglyphbuff", ObjectBuffs.HEALGLYPH = new HealGlyphBuff());
    }

    public static void registerRocks()
    {
        // Rocks
        Color titaniumMapColor = new Color(207, 207, 207);
        String[] titaniumRocksCategory = new String[]{"objects", "landscaping", "forestrocksandores"};
        String[] titaniumSwampRocksCategory = new String[]{"objects", "landscaping", "swamprocksandores"};
        ObjectRegistry.registerObject("titaniumrockoremedium", new BaseSingleOreRockSmall("stone", 4, "titaniumrockmedium", "titaniumore", "rockoremedium", "rockoremedium_titanium", titaniumMapColor, "titaniumore", 2, 10, 5, titaniumRocksCategory), 0F, true);
        ObjectRegistry.registerObject("titaniumrockoresmall", new BaseSingleOreRockSmall("stone", 4, "titaniumrocksmall", "titaniumore", "rockoresmall", "rockoresmall_titanium", titaniumMapColor, "titaniumore", 1, 5, 5, titaniumRocksCategory), 0F, true);
        ObjectRegistry.registerObject("titaniumrockoreswampmedium", new BaseSingleOreRockSmall("swampstone", 4, "titaniumrockswampmedium", "titaniumore", "rockoreswampmedium", "rockoremedium_titanium", titaniumMapColor, "titaniumore", 2, 10, 5, titaniumSwampRocksCategory), 0F, true);
        ObjectRegistry.registerObject("titaniumrockoreswampsmall", new BaseSingleOreRockSmall("swampstone", 4, "titaniumrockswampsmall", "titaniumore", "rockoreswampsmall", "rockoresmall_titanium", titaniumMapColor, "titaniumore", 1, 5, 5, titaniumSwampRocksCategory), 0F, true);
    }

    public static void registerPlants()
    {
        // Grass
        ObjectRegistry.registerObject("ancienttallgrass", new AncientTallGrassObject(), 0F, true);
        ObjectRegistry.registerObject("ancientwatergrass", new CaveWaterPlantObject("ancientwatergrass", 32, new Color(90, 123, 2))
        {
            public GameTexture generateItemTexture()
            {
                return GameTexture.fromFile("items/objects/" + this.getStringID());
            }
        }, 1F, true);

        // Saplings
        ObjectRegistry.registerObject("ancienttree", new AncientTreeObject("ancienttree", "ancientlog", "ancienttreesapling", new Color(73, 28, 45), 45, 60, 110, "ancienttreeleaves"), 0, false, false, true);
        ObjectRegistry.registerObject("ancienttreesapling", new BaseTreeSaplingObject("ancienttreesapling", "ancienttree", 1500, 2000, true), 5, true);
        ObjectRegistry.registerObject("overgrowthorns", (new BaseFruitBushObject("overgrowthorns", "overgrowthornssapling", 900, 1800, "thorns", 5, 2, new Color(191, 90, 62))).setDebrisColor(new Color(114, 28, 3)), 0F, false, false, true);
        ObjectRegistry.registerObject("overgrowthornssapling", new BaseFruitBushSaplingObject("overgrowthornssapling", "overgrowthorns", 1000, 2000, false, "mudtile"), 30, true);

        // Roots
        AncientRootObject.registerAncientRoot();
    }

    public static void registerFurniture()
    {
        // Statues
        ObjectRegistry.registerObject("mushroomminionstatue", new BaseStatueObject("mushroomminionstatue"), 10, true);
        ObjectRegistry.registerObject("apprendicestatue", new BaseStatueObject("apprendicestatue"), 10, true);
        ObjectRegistry.registerObject("fakevultureegg", new BaseStatueObject("fakevultureegg"), 10, true);
        ObjectRegistry.registerObject("beetstatue", new BaseStatueObject("beetstatue"), 10, true);
        ObjectRegistry.registerObject("sandtemplemonkstatue", new SandMonkStatueObject("sandtemplemonkstatue", 16, 4), 10, true);
        AscendedWizardStatueObject.registerAscendedWizardStatue("ascendedwizardstatue", true);

        // Decorations
        ObjectRegistry.registerObject("woodenidol", new BaseTableDecorationObject("woodenidol", new Color(153, 121, 81), 16, 18, 0, 0), 1F, true);
        ObjectRegistry.registerObject("stuffedfrog", new BaseTableDecorationObject("stuffedfrog", new Color(97, 105, 57), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedmouse", new BaseTableDecorationObject("stuffedmouse", new Color(66, 45, 36), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedlocust", new BaseTableDecorationObject("stuffedlocust", new Color(138, 140, 18), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedmosquito", new BaseTableDecorationObject("stuffedmosquito", new Color(71, 68, 36), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedspider", new BaseTableDecorationObject("stuffedspider", new Color(74, 74, 74), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedbird", new BaseTableDecorationObject("stuffedbird", new Color(219, 219, 219), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedbluebird", new BaseTableDecorationObject("stuffedbluebird", new Color(54, 94, 142), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedcanary", new BaseTableDecorationObject("stuffedcanary", new Color(173, 37, 37), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedcardinal", new BaseTableDecorationObject("stuffedcardinal", new Color(66, 45, 36), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedchick", new BaseTableDecorationObject("stuffedchick", new Color(243, 201, 42), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedfrostsentry", new BaseTableDecorationObject("stuffedfrostsentry", new Color(121, 201, 224), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedgoblin", new BaseTableDecorationObject("stuffedgoblin", new Color(120, 150, 79), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedportalminion", new BaseTableDecorationObject("stuffedportalminion", new Color(54, 52, 52), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedrabbit", new BaseTableDecorationObject("stuffedrabbit", new Color(116, 86, 61), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedscorpion", new BaseTableDecorationObject("stuffedscorpion", new Color(33, 45, 58), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedsquirrel", new BaseTableDecorationObject("stuffedsquirrel", new Color(112, 63, 36), 12, 10, 0, -2), 1F, true);
        ObjectRegistry.registerObject("stuffedswampslug", new BaseTableDecorationObject("stuffedswampslug", new Color(97, 105, 57), 12, 10, 0, -2), 1F, true);

        // Furniture
        Color honeyMapColor = new Color(186, 136, 46);
        ObjectRegistry.registerObject("beehivechest", new BeehiveChestInventoryObject("beehivechest", 20, honeyMapColor), 10, true);
        Color arcanicMapColor = new Color(144, 143, 155);
        ObjectRegistry.registerObject("arcanicdisplay", new BaseDisplayStandObject("arcanicdisplay", arcanicMapColor, 20, "objects", "furniture"), 10, true);
        ObjectRegistry.registerObject("arcanicchest", new BaseStorageBoxInventoryObject("arcanicchest", 80, arcanicMapColor, "arcanicchesttip","objects", "furniture"), 10F, true);
    }
}