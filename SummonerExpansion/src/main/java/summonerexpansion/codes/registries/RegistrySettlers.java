package summonerexpansion.codes.registries;

import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.SettlerRegistry;
import summonerexpansion.mobs.settlers.DruidHumanMob;
import summonerexpansion.mobs.settlers.DruidSettler;

public class RegistrySettlers
{
    public static void registerSettlers()
    {
        MobRegistry.registerMob("druidhuman", DruidHumanMob.class, true);
        SettlerRegistry.registerSettler("druid", new DruidSettler());
    }
}