package summonerexpansion.summonothers;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.gfx.gameTooltips.GameTooltipManager;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.gfx.gameTooltips.TooltipLocation;
import necesse.level.maps.Level;
import summonerexpansion.summonmobs.TankTrainingDummyMob;

public class TankTrainingDummyObjectEntity extends ObjectEntity
{
    private int dummyMobID = -1;

    public TankTrainingDummyObjectEntity(Level level, int x, int y)
    {
        super(level, "tanktrainingdummy", x, y);
    }

    public void setupContentPacket(PacketWriter writer) 
    {
        if (this.dummyMobID == -1) 
        {
            this.generateMobID();
        }
        writer.putNextInt(this.dummyMobID);
    }

    public void applyContentPacket(PacketReader reader) {
        this.dummyMobID = reader.getNextInt();
    }

    public void clientTick() 
    {
        super.clientTick();
        TankTrainingDummyMob m = this.getMob();
        if (m != null) 
        {
            m.keepAlive(this);
        }
    }

    public void serverTick()
    {
        super.serverTick();
        TankTrainingDummyMob m = this.getMob();
        if (m == null)
        {
            m = this.generateMobID();
            this.markDirty();
        }
        m.keepAlive(this);
    }

    private TankTrainingDummyMob generateMobID()
    {
        TankTrainingDummyMob lastMob = this.getMob();
        if (lastMob != null)
        {
            lastMob.remove();
        }

        TankTrainingDummyMob m = new TankTrainingDummyMob();
        float var10002 = (float)(this.tileX * 32 + 16);
        int var10003 = this.tileY * 32;
        this.getLevel().entityManager.addMob(m, var10002, (float)(var10003 + 16));
        this.dummyMobID = m.getUniqueID();
        return m;
    }

    private TankTrainingDummyMob getMob()
    {
        if (this.dummyMobID == -1)
        {
            return null;
        }
        else
        {
            Mob m = this.getLevel().entityManager.mobs.get(this.dummyMobID, false);
            return m != null ? (TankTrainingDummyMob)m : null;
        }
    }

    public void remove()
    {
        super.remove();
        TankTrainingDummyMob m = this.getMob();
        if (m != null)
        {
            m.remove();
        }
    }

    public void onMouseHover(PlayerMob perspective, boolean debug)
    {
        super.onMouseHover(perspective, debug);
        if (debug)
        {
            GameTooltipManager.addTooltip(new StringTooltips("MobID: " + this.dummyMobID), TooltipLocation.INTERACT_FOCUS);
        }
    }
}