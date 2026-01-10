package summonerexpansion.mobs.summonminions.magicminions;

import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
import necesse.level.maps.Level;

import java.util.function.Predicate;

public class FiremoneSentryT1 extends FiremoneSentry implements OEVicinityBuff
{
    public int flowerRange = 800;

    public FiremoneSentryT1()
    {
        super();
    }

    public Buff[] getBuffs()
    {
        return new Buff[]{BuffRegistry.getBuff("firemonebufft1")};
    }

    public int getBuffRange()
    {
        return flowerRange;
    }

    public boolean shouldBuffPlayers() {
        return true;
    }

    public boolean shouldBuffMobs()
    {
        return true;
    }

    public void applyBuffs(Mob mob)
    {
        Buff[] var2 = this.getBuffs();
        for (Buff buff : var2)
        {
            if (buff != null)
            {
                ActiveBuff ab = new ActiveBuff(buff, mob, 120, this);
                mob.buffManager.addBuff(ab, false);
            }
        }
    }

    public void clientTick()
    {
        super.clientTick();
        tickVicinityBuff(this);
    }

    public void serverTick()
    {
        super.serverTick();
        tickVicinityBuff(this);
    }
}
