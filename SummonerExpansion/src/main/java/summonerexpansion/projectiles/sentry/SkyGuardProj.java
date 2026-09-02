package summonerexpansion.projectiles.sentry;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.List;

public class SkyGuardProj extends FollowingProjectile
{
    public SkyGuardProj() {}

    public SkyGuardProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
    {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.knockback = knockback;
    }

    public void init()
    {
        super.init();
        turnSpeed = 1.45f;
        height = 10;
        trailOffset = -14f;
        setWidth(10, true);
        canBounce = false;
        isSolid = false;
    }

    public Color getParticleColor()
    {
        return new Color(209, 136, 28);
    }

    public Trail getTrail()
    {
        return new Trail(this, getLevel(), getParticleColor(), 10, 300, getHeight());
    }

    public void updateTarget()
    {
        if (traveledDistance > 5)
        {
            findTarget(m -> m.isHostile, 0, 1000);
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
    }
}