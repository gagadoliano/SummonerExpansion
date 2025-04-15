package summonerexpansion.summonprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GroundPillarList;
import necesse.entity.manager.GroundPillarHandler;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import summonerexpansion.summonminions.HorrorSentry;

import java.awt.geom.Point2D;
import java.util.List;

public class HorrorSentryProj extends Projectile
{
    private double distCounter;
    private double distBuffer;
    private final GroundPillarList<HorrorSentry.HorrorPillar> pillars = new GroundPillarList();

    public HorrorSentryProj() {}

    public HorrorSentryProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
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
        this.maxMovePerTick = 12;
        this.height = 0.0F;
        this.piercing = 1;
        this.setWidth(20.0F);
        if (this.isClient())
        {
            this.getLevel().entityManager.addPillarHandler(new GroundPillarHandler<HorrorSentry.HorrorPillar>(this.pillars)
            {
                protected boolean canRemove() {
                    return HorrorSentryProj.this.removed();
                }
                public double getCurrentDistanceMoved() {
                    return HorrorSentryProj.this.distCounter;
                }
            });
        }
    }

    public Trail getTrail() {
        return null;
    }

    public void onMoveTick(Point2D.Float startPos, double movedDist)
    {
        super.onMoveTick(startPos, movedDist);
        this.distCounter += movedDist;
        this.distBuffer += movedDist;
        while(this.distBuffer > 12.0)
        {
            this.distBuffer -= 12.0;
            synchronized(this.pillars)
            {
                this.pillars.add(new HorrorSentry.HorrorPillar((int)(this.x + this.dx * 20.0F + GameRandom.globalRandom.floatGaussian() * 4.0F), (int)(this.y + this.dy * 20.0F + GameRandom.globalRandom.floatGaussian() * 4.0F), this.distCounter, this.getWorldEntity().getLocalTime()));
            }
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {}
}
