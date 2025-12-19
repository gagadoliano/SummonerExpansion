package summonerexpansion.codes.entity;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.objectEntity.StandaloneBannerObjectEntity;
import necesse.level.maps.Level;
import summonerexpansion.codes.registry.SummonerBuffs;

public class CaveglowLampObjectEntity extends StandaloneBannerObjectEntity
{
    public CaveglowLampObjectEntity(Level level, int x, int y) {
        super(level, "caveglowlamp", x, y);
    }

    public int getBuffRange() {
        return 800;
    }

    public void applyBuffs(Mob mob)
    {
        ActiveBuff ab = new ActiveBuff(SummonerBuffs.SummonerAuras.CAVEGLOWLAMPBOOST, mob, 100, null);
        mob.buffManager.addBuff(ab, false);
    }
}