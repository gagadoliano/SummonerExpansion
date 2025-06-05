package summonerexpansion.summonminions;

import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
import necesse.level.maps.Level;

import java.util.function.Predicate;

public class SunflowerSentryT1 extends SunflowerSentry implements OEVicinityBuff
{
    public int flowerRange = 800;

    public SunflowerSentryT1()
    {
        super();
    }

    public Buff[] getBuffs()
    {
        return new Buff[]{BuffRegistry.getBuff("sunflowerbufft1")};
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

    public Predicate<Mob> buffMobsFilter()
    {
        return (m) -> m.isHuman && !m.isSummoned && !m.isHostile;
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

    public void tickVicinityBuff(Mob mob)
    {
        Level level = mob.getLevel();
        int posX = (int) mob.x;
        int posY = (int) mob.y;
        tickVicinityBuff(level, posX, posY);
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

