package summonerexpansion.summonothers;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.objectEntity.StandaloneBannerObjectEntity;
import necesse.level.maps.Level;

public class BannerOfWaterObjectEntity extends StandaloneBannerObjectEntity
{
    public BannerOfWaterObjectEntity(Level level, int x, int y) {
        super(level, "bannerofwater", x, y);
    }

    public int getBuffRange() {
        return 2000;
    }

    public void applyBuffs(Mob mob) {
        ActiveBuff ab = new ActiveBuff(SummonerBuffs.SummonerBanners.WATERBANNERBOOST, mob, 100, null);
        mob.buffManager.addBuff(ab, false);
    }
}