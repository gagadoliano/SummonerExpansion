package summonerexpansion.codes.events;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.friendly.human.HumanMob;

public class SnowmanSetLevelEvent extends SnowmanExplosionLevelEvent
{
    public SnowmanSetLevelEvent()
    {
        this(0.0F, 0.0F, 100, new GameDamage(100.0F), false, 0, null);
    }

    public SnowmanSetLevelEvent(float x, float y, int range, GameDamage damage, boolean destructive, int toolTier, Mob owner)
    {
        super(x, y, range, damage, destructive, toolTier, owner);
        knockback = 150;
    }

    protected boolean canHitMob(Mob target)
    {
        return !target.isPlayer && (target instanceof HumanMob || super.canHitMob(target));
    }
}