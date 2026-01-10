package summonerexpansion.codes.packets;

import necesse.engine.network.NetworkPacket;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.packet.PacketRequestMobData;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.level.maps.Level;
import summonerexpansion.codes.registry.SummonerEquips;
import summonerexpansion.items.equips.armorsetbonus.TitaniumRangedSetBonus;

public class PacketTitaniumBowAimUpdate extends Packet
{
    public final int mobUniqueID;
    public final int levelX;
    public final int levelY;

    public PacketTitaniumBowAimUpdate(byte[] data)
    {
        super(data);
        PacketReader reader = new PacketReader(this);
        mobUniqueID = reader.getNextInt();
        levelX = reader.getNextInt();
        levelY = reader.getNextInt();
    }

    public PacketTitaniumBowAimUpdate(Mob mob, int levelX, int levelY)
    {
        mobUniqueID = mob.getUniqueID();
        this.levelX = levelX;
        this.levelY = levelY;
        PacketWriter writer = new PacketWriter(this);
        writer.putNextInt(mobUniqueID);
        writer.putNextInt(levelX);
        writer.putNextInt(levelY);
    }

    protected boolean update(Level level)
    {
        Mob mob = GameUtils.getLevelMob(mobUniqueID, level);
        if (mob != null)
        {
            ActiveBuff buff = mob.buffManager.getBuff(SummonerEquips.SummonerArmorBuffs.TITANIUMRANGEDBUFF);
            if (buff != null)
            {
                TitaniumRangedSetBonus.updateMousePos(buff, levelX, levelY);
                return true;
            }
        }
        return false;
    }

    public void processServer(NetworkPacket packet, Server server, ServerClient client)
    {
        if (client.checkHasRequestedSelf() && !client.isDead())
        {
            client.checkSpawned();
            if (update(client.getLevel()))
            {
                server.network.sendToClientsWithEntity(this, client.playerMob);
            }
        }
    }

    public void processClient(NetworkPacket packet, Client client)
    {
        if (!update(client.getLevel()))
        {
            client.network.sendPacket(new PacketRequestMobData(mobUniqueID));
        }
    }
}