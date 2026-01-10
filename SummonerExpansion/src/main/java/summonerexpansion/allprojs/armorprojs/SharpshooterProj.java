package summonerexpansion.allprojs.armorprojs;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.projectile.bulletProjectile.BulletProjectile;

public class SharpshooterProj extends BulletProjectile
{
    public SharpshooterProj() {
    }

    public SharpshooterProj(float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner)
    {
        super(x, y, targetX, targetY, speed, distance, damage, knockback, owner);
    }
}