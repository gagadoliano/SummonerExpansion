package summonerexpansion.codes.registry;

import necesse.engine.registries.MapIconRegistry;
import necesse.level.maps.mapData.TextureGameMapIcon;

public class SummonerMapIcon
{
    public static void registerSummonMapIcons()
    {
        MapIconRegistry.registerIcon("druidhomeicon", new TextureGameMapIcon("ui/mapicons/druidhomeicon"));
        MapIconRegistry.registerIcon("horrorculticon", new TextureGameMapIcon("ui/mapicons/horrorculticon"));
    }
}