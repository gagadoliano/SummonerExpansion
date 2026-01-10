package summonerexpansion.codes.registry;

import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.inventory.item.placeableItem.tileItem.GrassSeedItem;
import necesse.level.gameTile.SimpleFloorTile;
import summonerexpansion.objects.summontiles.*;

import java.awt.*;

public class SummonerTiles
{
    public static void registerSummonTiles()
    {
        // Terrain tiles
        TileRegistry.registerTile("ancientgrasstile", new AncientGrassTile(), 2, true);
        ItemRegistry.registerItem("ancientgrassseed", new GrassSeedItem("ancientgrasstile"), 2, true);
        TileRegistry.registerTile("beehivefloor", new SimpleFloorTile("beehivefloor", new Color(158, 101, 32)), 0, true);

        // Buff tiles
        TileRegistry.registerTile("empoweredamethyst", new EmpoweredAmethyst(), 10, true);
        TileRegistry.registerTile("empoweredsapphire", new EmpoweredSapphire(), 10, true);
        TileRegistry.registerTile("empoweredemerald", new EmpoweredEmerald(), 10, true);
        TileRegistry.registerTile("empoweredruby", new EmpoweredRuby(), 10, true);
        TileRegistry.registerTile("empowerednewsapphire", new EmpoweredNewSapphire(), 10, true);
        TileRegistry.registerTile("empowerednewemerald", new EmpoweredNewEmerald(), 10, true);
        TileRegistry.registerTile("empowerednewruby", new EmpoweredNewRuby(), 10, true);
        TileRegistry.registerTile("empoweredtopaz", new EmpoweredTopaz(), 10, true);
    }
}