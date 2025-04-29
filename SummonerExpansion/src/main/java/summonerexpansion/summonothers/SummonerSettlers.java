package summonerexpansion.summonothers;

import necesse.engine.registries.SettlerRegistry;
import summonerexpansion.summonmobs.DruidSettler;

public class SummonerSettlers
{
    public static void registerSummonSettlers()
    {
        SettlerRegistry.registerSettler("druid", new DruidSettler());
    }
}