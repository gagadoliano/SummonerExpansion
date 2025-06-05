package summonerexpansion.summonothers;

import necesse.engine.localization.Localization;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.level.gameObject.FruitBushObject;
import necesse.level.gameObject.SaplingObject;
import summonerexpansion.summonobjects.*;

import java.awt.*;

public class SummonerObjects
{
    public static void registerSummonObjects()
    {
        ObjectRegistry.registerObject("tanktrainingdummy", new TankTrainingDummyObject(), 50, true);
        ObjectRegistry.registerObject("summoningbookshelf", new SummoningBookshelf(), 50, true);
        ObjectRegistry.registerObject("bannerofwater", new BannerOfWater(), 100, true);
        ObjectRegistry.registerObject("overgrowthorns", (new FruitBushObject("overgrowthorns", "overgrowthornssapling", 900.0F, 1800.0F, "thorns", 1.0F, 2, new Color(191, 90, 62))).setDebrisColor(new Color(114, 28, 3)), 0.0F, false);
        ObjectRegistry.registerObject("overgrowthornssapling", new SaplingObject("overgrowthornssapling", "overgrowthorns", 1200, 2100, false, "mudtile"){
            public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
            {
                ListGameTooltips tooltips = new ListGameTooltips();
                tooltips.add(Localization.translate("itemtooltip", "overgrowthornstip"));
                return tooltips;
            }
        }, 30, true);
    }
}