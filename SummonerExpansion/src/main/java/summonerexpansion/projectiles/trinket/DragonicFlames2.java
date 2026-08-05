package summonerexpansion.projectiles.trinket;

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
import summonerexpansion.codes.events.DragonicBurningGroundEvent2;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class DragonicFlames2 extends Projectile
{
    private boolean hitMob = false;
    private double distBuffer;
    protected float upgradeTier;
    protected int firstGroundEventUniqueID;

    public DragonicFlames2() {
    }

    public DragonicFlames2(Level level, float x, float y, float targetX, float targetY, int distance, GameDamage damage, Mob owner, float upgradeTier)
    {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = (float)distance * 1.1F;
        this.setDamage(damage);
        this.setOwner(owner);
        this.setDistance(distance);
        this.upgradeTier = upgradeTier;
    }

    public void init()
    {
        super.init();
        this.canBounce = false;
        this.height = 16.0F;
        this.piercing = 2;
        this.setWidth(25.0F);
        if (this.isClient())
        {
            int amount = this.distance / 6;
            this.spawnSprayParticles(amount);
        }
    }

    public void onMoveTick(Point2D.Float startPos, double movedDist)
    {
        super.onMoveTick(startPos, movedDist);
        float progress = this.traveledDistance / (float)this.distance;
        this.speed = GameMath.lerp(progress, (float)this.distance * 1.1F, 5.0F);
        this.distBuffer += movedDist;
        if (this.isServer())
        {
            while(this.distBuffer > (double)32.0F)
            {
                this.distBuffer -= 32.0F;
                if (this.getLevel().isLiquidTile(this.getTileX(), this.getTileY())) {
                    break;
                }
                GameRandom random = GameRandom.globalRandom;
                if (random.getChance(0.5F)) {
                    int eventX = this.getX() + random.getIntBetween(-16, 16);
                    int eventY = this.getY() + random.getIntBetween(-16, 16);
                    DragonicBurningGroundEvent2 event = new DragonicBurningGroundEvent2(this.getOwner(), eventX, eventY, random, this.getDamage(), this.firstGroundEventUniqueID, this.upgradeTier);
                    this.getLevel().entityManager.events.add(event);
                    if (this.firstGroundEventUniqueID == 0)
                    {
                        this.firstGroundEventUniqueID = event.getUniqueID();
                    }
                }
            }
        }
    }

    public void spawnSprayParticles(int amount)
    {
        ParticleTypeSwitcher particleTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);

        for(int i = 0; i < amount; ++i)
        {
            GameRandom random = GameRandom.globalRandom;
            float posX = this.x + random.floatGaussian() * 2.0F;
            float posY = this.y + random.floatGaussian() * 2.0F;
            float projectileHeight = this.getHeight();
            float startHeight = random.getFloatBetween(projectileHeight - 2.0F, projectileHeight + 4.0F) - 16.0F;
            float startHeightSpeed = random.getFloatBetween(0.0F, 60.0F);
            float endHeight = random.getFloatBetween(-10.0F, -5.0F);
            float distanceLeft = (float)this.distance - this.traveledDistance;
            float floatPower = random.getFloatBetween(0.1F, 1.0F);
            float power = floatPower * (distanceLeft + 50.0F);
            float gravity = -15.0F * floatPower;
            float friction = 1.0F;
            int lifeAdded = (int)(250.0F * floatPower);
            int timeToLive = random.getIntBetween(250 + lifeAdded, 750 + lifeAdded);
            int timeToFadeOut = random.getIntBetween(500, 1000);
            int totalTime = timeToLive + timeToFadeOut;
            ParticleOption.HeightMover heightMover = new ParticleOption.HeightMover(startHeight, startHeightSpeed, gravity, 2.0F, endHeight, 0.0F);
            ParticleOption.FrictionMover frictionMover = new ParticleOption.FrictionMover(this.dx * power, this.dy * power, friction);
            ParticleOption.CollisionMover mover = new ParticleOption.CollisionMover(this.getLevel(), frictionMover);
            this.getLevel().entityManager.addParticle(posX, posY, particleTypeSwitcher.next()).fadesAlphaTime(0, timeToFadeOut).sprite(GameResources.puffParticles.sprite(random.getIntBetween(0, 4), 0, 12)).color((options, lifeTime, timeAlive, lifePercent) -> {
                float clampedLifePercent = Math.max(0.0F, Math.min(1.0F, lifePercent));
                options.color(new Color((int)(255.0F - 25.0F * clampedLifePercent), (int)(225.0F - 225.0F * clampedLifePercent), (int)(155.0F - 155.0F * clampedLifePercent)));
            }).sizeFadesInAndOut(10, 15, 100, 0).height(heightMover).rotates().moves((pos, delta, lifeTime, timeAlive, lifePercent) ->
            {
                if (heightMover.currentHeight > endHeight && (!this.removed() || !this.hitMob))
                {
                    mover.tick(pos, delta, lifeTime, timeAlive, lifePercent);
                }
            }).ignoreLight(true).givesLight(30.0F, 1.0F).lifeTime(totalTime);
        }
    }

    public GameDamage getDamage()
    {
        float progress = this.traveledDistance / (float)this.distance;
        float modifier = GameMath.lerp(progress, 1.2F, 0.2F);
        return super.getDamage().modFinalMultiplier(modifier);
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        this.hitMob = mob != null;
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