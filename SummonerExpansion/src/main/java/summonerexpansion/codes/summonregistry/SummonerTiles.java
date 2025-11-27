package summonerexpansion.codes.summonregistry;

import necesse.engine.registries.TileRegistry;
import summonerexpansion.objects.summontiles.*;

public class SummonerTiles
{
    public static void registerSummonTiles()
    {
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