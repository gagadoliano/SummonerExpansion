package summonerexpansion.codes.registries;

import necesse.engine.registries.PacketRegistry;
import summonerexpansion.codes.packets.PacketTitaniumBowAimUpdate;

public class RegistryPackets
{
    public static void registerPackets()
    {
        // Armor set
        PacketRegistry.registerPacket(PacketTitaniumBowAimUpdate.class);
    }
}