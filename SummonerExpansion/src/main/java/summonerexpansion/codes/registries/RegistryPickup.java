package summonerexpansion.codes.registries;

import necesse.engine.registries.PickupRegistry;
import summonerexpansion.codes.pickups.SpiderSwordPickupEntity;
import summonerexpansion.codes.pickups.VampireMinionHeartPickupEntity;

public class RegistryPickup
{
    public static void registerPickups()
    {
        PickupRegistry.registerPickup("vampireminionheartpickupentity", VampireMinionHeartPickupEntity.class);
        PickupRegistry.registerPickup("spiderswordpickupentity", SpiderSwordPickupEntity.class);
    }
}