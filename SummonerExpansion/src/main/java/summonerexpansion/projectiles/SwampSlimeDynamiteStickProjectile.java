package summonerexpansion.projectiles;

import necesse.entity.levelEvent.explosionEvent.DynamiteExplosionEvent;
import necesse.entity.levelEvent.explosionEvent.ExplosionEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.projectile.BombProjectile;

public class SwampSlimeDynamiteStickProjectile extends BombProjectile
{
    public SwampSlimeDynamiteStickProjectile() {
    }

    public SwampSlimeDynamiteStickProjectile(float x, float y, float targetX, float targetY, int speed, int distance, GameDamage damage, Mob owner) {
        super(x, y, targetX, targetY, speed, distance, damage, owner);
    }

    public void init()
    {
        super.init();
        this.stopsRotatingOnStationary = true;
    }

    public int getFuseTime() {
        return 8000;
    }

    public float getParticleAngle() {
        return 220.0F;
    }

    public float getParticleDistance() {
        return 14.0F;
    }

    public ExplosionEvent getExplosionEvent(float x, float y)
    {
        float toolTier = Math.max(6.0F, this.getOwnerToolTier() + 1.0F);
        return new DynamiteExplosionEvent(x, y, 500, new GameDamage(400.0F, 1000.0F), true, false, toolTier, this.getOwner());
    }
}