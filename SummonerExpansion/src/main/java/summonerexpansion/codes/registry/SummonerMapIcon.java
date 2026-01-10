package summonerexpansion.codes.registry;

import necesse.engine.registries.MapIconRegistry;
import necesse.level.maps.mapData.TextureGameMapIcon;

public class SummonerMapIcon
{
    public static void registerSummonMapIcons()
    {
        MapIconRegistry.registerIcon("ancientforesticon", new TextureGameMapIcon("ui/mapicons/ancientforesticon"));
        MapIconRegistry.registerIcon("beehivechesticon", new TextureGameMapIcon("ui/mapicons/beehivechesticon"));
        MapIconRegistry.registerIcon("horrorculticon", new TextureGameMapIcon("ui/mapicons/horrorculticon"));
        MapIconRegistry.registerIcon("druidhomeicon", new TextureGameMapIcon("ui/mapicons/druidhomeicon"));
        MapIconRegistry.registerIcon("titaniumicon", new TextureGameMapIcon("ui/mapicons/titaniumicon"));
        MapIconRegistry.registerIcon("spidericon", new TextureGameMapIcon("ui/mapicons/spidericon"));
    }
}