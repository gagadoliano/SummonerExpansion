package summonerexpansion.allprojs.armorprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;

import java.awt.*;
import java.util.List;

public class CopperSetProj extends FollowingProjectile
{
    private boolean hitMob = false;

    public CopperSetProj() {
    }

    public CopperSetProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
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
        maxMovePerTick = 16;
        height = 18.0F;
        piercing = 4;
        givesLight = true;
        canBounce = false;
        isSolid = false;
    }

    public void updateTarget()
    {
        if (traveledDistance > 10.0F)
        {
            findTarget((m) -> m.isHostile, 0.0F, 1000F);
        }
    }

    public float getTurnSpeed(int targetX, int targetY, float delta)
    {
        return getTurnSpeed(delta) * invDynamicTurnSpeedMod(targetX, targetY, (float)getTurnRadius());
    }

    public void onMaxMoveTick()
    {
        if (isClient())
        {
            spawnSpinningParticle();
        }
    }

    public Color getParticleColor() {
        return new Color(220, 164, 20);
    }

    public Trail getTrail()
    {
        return new Trail(this, getLevel(), getParticleColor(), 12.0F, 250, getHeight());
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        hitMob = mob != null;
        if (isServer() && mob != null)
        {
            ActiveBuff ab = new ActiveBuff("coppersetfiredebuff", mob, 15.0F, getOwner());
            mob.addBuff(ab, true);
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
    }
}