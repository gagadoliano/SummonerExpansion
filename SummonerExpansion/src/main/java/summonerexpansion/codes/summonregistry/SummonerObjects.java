package summonerexpansion.codes.summonregistry;

import necesse.engine.localization.Localization;
import necesse.engine.registries.ObjectRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.level.gameObject.FruitBushObject;
import necesse.level.gameObject.RockObject;
import necesse.level.gameObject.SaplingObject;
import summonerexpansion.objects.summonobjects.*;

import java.awt.*;

public class SummonerObjects
{
    public static void registerSummonObjects()
    {
        // Dummy
        ObjectRegistry.registerObject("tanktrainingdummy", new TankTrainingDummyObject(), 50, true);

        // Workbench
        ObjectRegistry.registerObject("summoningbookshelf", new SummoningBookshelf(), 50, true);
        ArcanicConverter.registerArcanicConverter("arcanicconverter", true, true);

        // Duo Workbench
        SummoningTableDuo.registerSummoningTable();
        DemonicSummoningTableDuo.registerDemonicSummoningTable();
        TungstenSummoningTableDuo.registerTungstenSummoningTable();
        FallenSummoningTableDuo.registerFallenSummoningTable();

        // Auras
        ObjectRegistry.registerObject("bannerofwater", new BannerOfWater(), 100, true);

        // Lights
        ObjectRegistry.registerObject("caveglowlamp", new CaveglowLamp(), 80, true);

        // Plants
        ObjectRegistry.registerObject("overgrowthorns", (new FruitBushObject("overgrowthorns", "overgrowthornssapling", 900.0F, 1800.0F, "thorns", 5.0F, 2, new Color(191, 90, 62))).setDebrisColor(new Color(114, 28, 3)), 0.0F, false);
        ObjectRegistry.registerObject("overgrowthornssapling", new SaplingObject("overgrowthornssapling", "overgrowthorns", 1200, 2100, false, "mudtile")
        {
            public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
            {
                ListGameTooltips tooltips = new ListGameTooltips();
                tooltips.add(Localization.translate("itemtooltip", "overgrowthornstip"));
                return tooltips;
            }
        }, 30, true);

        // Walls
        RockObject empoweredrubyrock;
        ObjectRegistry.registerObject("empoweredrubyrock", empoweredrubyrock = new RockObject("empoweredrubyrock", new Color(87, 49, 59), "ruby", 0, 3, 1), 1.0F, true);
        empoweredrubyrock.toolTier = 8F;
    }
}