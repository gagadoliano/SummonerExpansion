package summonerexpansion.allprojs.meleeprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class SpiderSwordProj extends Projectile
{
    private boolean hitMob = false;

    public SpiderSwordProj() {
    }

    public SpiderSwordProj(Level level, float x, float y, float targetX, float targetY, int distance, GameDamage damage, Mob owner)
    {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = (float)distance * 1.1F;
        this.setDamage(damage);
        this.setOwner(owner);
        this.setDistance(distance);
    }

    public void init()
    {
        super.init();
        this.canBounce = false;
        this.height = 16.0F;
        this.piercing = 3;
        this.setWidth(100.0F);
        if (this.isClient())
        {
            int amount = this.distance / 6;
            this.spawnSprayParticles(amount);
        }
    }

    public void onMoveTick(Point2D.Float startPos, double movedDist)
    {
        super.onMoveTick(startPos, movedDist);
        float progress = traveledDistance / (float)distance;
        speed = GameMath.lerp(progress, (float)distance * 1.1F, 5.0F);
    }

    public void spawnSprayParticles(int amount)
    {
        ParticleTypeSwitcher particleTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);

        for(int i = 0; i < amount; ++i)
        {
            float posX = x + GameRandom.globalRandom.floatGaussian() * 30.0F;
            float posY = y + GameRandom.globalRandom.floatGaussian() * 30.0F;
            float projectileHeight = getHeight();
            float startHeight = GameRandom.globalRandom.getFloatBetween(projectileHeight - 2.0F, projectileHeight + 4.0F);
            float startHeightSpeed = GameRandom.globalRandom.getFloatBetween(0.0F, 60.0F);
            float endHeight = GameRandom.globalRandom.getFloatBetween(-10.0F, -5.0F);
            float gravity = GameRandom.globalRandom.getFloatBetween(16.0F, 40.0F);
            float distanceLeft = (float)distance - traveledDistance;
            float floatPower = GameRandom.globalRandom.getFloatBetween(0.1F, 1.0F);
            float power = floatPower * (distanceLeft + 50.0F);
            float friction = 1.0F;
            int lifeAdded = (int)(250.0F * floatPower);
            int timeToLive = GameRandom.globalRandom.getIntBetween(250 + lifeAdded, 750 + lifeAdded);
            int timeToFadeOut = GameRandom.globalRandom.getIntBetween(500, 1000);
            int totalTime = timeToLive + timeToFadeOut;
            ParticleOption.HeightMover heightMover = new ParticleOption.HeightMover(startHeight, startHeightSpeed, gravity, 2.0F, endHeight, 0.0F);
            ParticleOption.FrictionMover frictionMover = new ParticleOption.FrictionMover(dx * power, dy * power, friction);
            ParticleOption.CollisionMover mover = new ParticleOption.CollisionMover(getLevel(), frictionMover);
            getLevel().entityManager.addParticle(posX, posY, particleTypeSwitcher.next()).fadesAlphaTime(0, timeToFadeOut).color(new Color(166, 204, 52)).sizeFadesInAndOut(6, 12, 100, 0).sprite(GameResources.bubbleParticle.sprite(GameRandom.globalRandom.nextInt(4), 0, 12)).height(heightMover).rotates().moves((pos, delta, lifeTime, timeAlive, lifePercent) ->
            {
                if (heightMover.currentHeight > endHeight && (!removed() || !hitMob))
                {
                    mover.tick(pos, delta, lifeTime, timeAlive, lifePercent);
                }

            }).givesLight(110.0F, 0.7F).lifeTime(totalTime);
        }
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        hitMob = mob != null;
    }

    public Color getParticleColor() {
        return null;
    }

    public Trail getTrail() {
        return null;
    }

    protected Color getWallHitColor() {
        return null;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
    }
}