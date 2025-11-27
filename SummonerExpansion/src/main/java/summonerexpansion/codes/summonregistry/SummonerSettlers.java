package summonerexpansion.codes.summonregistry;

import necesse.engine.registries.SettlerRegistry;
import summonerexpansion.mobs.summonmobs.*;

public class SummonerSettlers
{
    public static void registerSummonSettlers()
    {
        SettlerRegistry.registerSettler("druid", new DruidSettler());
    }
}