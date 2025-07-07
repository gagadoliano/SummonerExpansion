package summonerexpansion.summonothers;

import necesse.engine.registries.TileRegistry;
import summonerexpansion.summontiles.*;

public class SummonerTiles
{
    public static void registerSummonTiles()
    {
        TileRegistry.registerTile("empoweredamethyst", new EmpoweredAmethyst(), 10, true);
        TileRegistry.registerTile("empoweredsapphire", new EmpoweredSapphire(), 10, true);
        TileRegistry.registerTile("empoweredemerald", new EmpoweredEmerald(), 10, true);
        TileRegistry.registerTile("empoweredruby", new EmpoweredRuby(), 10, true);
    }
}