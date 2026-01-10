package summonerexpansion.codes.registry;

import necesse.engine.registries.PacketRegistry;
import summonerexpansion.codes.packets.PacketTitaniumBowAimUpdate;

public class SummonerPackets
{
    public static void registerSummonPackets()
    {
        // Armor set
        PacketRegistry.registerPacket(PacketTitaniumBowAimUpdate.class);
    }
}
