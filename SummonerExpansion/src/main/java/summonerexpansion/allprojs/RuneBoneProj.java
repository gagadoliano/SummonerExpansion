package summonerexpansion.allprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GroundPillar;
import necesse.engine.util.GroundPillarList;
import necesse.entity.manager.GroundPillarHandler;
import necesse.entity.mobs.BoneSpikeMob;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class RuneBoneProj extends Projectile
{
    protected int distanceBetweenSpikes = 30;
    protected int maxPillars;
    protected GameDamage damage;
    private double distCounter;
    private double distBuffer;
    private final GroundPillarList<RuneBoneProj.RuneBonePillar> smallPillars = new GroundPillarList();

    public RuneBoneProj() {}

    public RuneBoneProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, int maxPillars) 
    {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.damage = damage;
        this.knockback = knockback;
        this.maxPillars = maxPillars;
    }

    public void init() 
    {
        super.init();
        this.height = 0.0F;
        this.setWidth(60.0F);
        if (this.isClient()) 
        {
            this.getLevel().entityManager.addPillarHandler(new GroundPillarHandler<RuneBoneProj.RuneBonePillar>(this.smallPillars) 
            {
                protected boolean canRemove() {
                    return RuneBoneProj.this.removed();
                }
                public double getCurrentDistanceMoved() {
                    return RuneBoneProj.this.distCounter;
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
        if (!(this.traveledDistance > (float)this.distance - (float)this.distanceBetweenSpikes / 2.0F)) 
        {
            this.distCounter += movedDist;
            this.distBuffer += movedDist;

            while(this.distBuffer > (double)this.distanceBetweenSpikes) 
            {
                this.distBuffer -= this.distanceBetweenSpikes;
                synchronized(this.smallPillars) 
                {
                    this.smallPillars.add(new RuneBoneProj.RuneBonePillar((int)(this.x + GameRandom.globalRandom.floatGaussian() * 6.0F), (int)(this.y + GameRandom.globalRandom.floatGaussian() * 4.0F), this.distCounter, this.getLocalTime()));
                }
            }

        }
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (getOwner() != null)
        {
            List<BoneSpikeMob> nearbySpikes = this.getNearbySpikes(this.getLevel(), getOwner());
            if (nearbySpikes.size() >= this.maxPillars) {
                for(int i = nearbySpikes.size(); i >= this.maxPillars; --i) {
                    BoneSpikeMob mobToRemove = this.getBoneSpikeWithLowestDuration(nearbySpikes);
                    if (mobToRemove != null) {
                        mobToRemove.forceDespawnSpike();
                    }
                }
            }
        }
        BoneSpikeMob boneSpike = new BoneSpikeMob(getOwner(), this.damage, this.getTime() + 9000L);
        GameRandom random = new GameRandom(this.getUniqueID());
        boneSpike.setPos((float)((int)(x + random.floatGaussian() * 3.0F)), (float)((int)(y + random.floatGaussian() * 1.5F)), true);
        boneSpike.resetUniqueID(random);
        this.getLevel().entityManager.mobs.addHidden(boneSpike);
    }

    public List<BoneSpikeMob> getNearbySpikes(Level level, Mob owner)
    {
        int checkInRange = 640;
        return (List)level.entityManager.mobs.streamInRegionsInRange(owner.x, owner.y, checkInRange).filter((s) -> {
            return s instanceof BoneSpikeMob;
        }).map((s) -> {
            return (BoneSpikeMob)s;
        }).filter((s) -> {
            return s.mobOwner == owner;
        }).filter((s) -> {
            return s.getDistance(owner) <= (float)checkInRange;
        }).collect(Collectors.toList());
    }

    public BoneSpikeMob getBoneSpikeWithLowestDuration(List<BoneSpikeMob> nearbySpikes)
    {
        BoneSpikeMob mobToRemove = null;
        long longestDuration = 0L;
        Iterator var5 = nearbySpikes.iterator();

        while(var5.hasNext()) {
            BoneSpikeMob nearbySpike = (BoneSpikeMob)var5.next();
            if (nearbySpike.isCracking) {
                break;
            }

            if (longestDuration == 0L) {
                longestDuration = nearbySpike.startCrackingTime;
                mobToRemove = nearbySpike;
            } else if (nearbySpike.startCrackingTime < longestDuration) {
                longestDuration = nearbySpike.startCrackingTime;
                mobToRemove = nearbySpike;
            }
        }

        return mobToRemove;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {}

    public static class RuneBonePillar extends GroundPillar 
    {
        public GameTextureSection texture;
        public boolean mirror;

        public RuneBonePillar(int x, int y, double spawnDistance, long spawnTime)
        {
            super(x, y, spawnDistance, spawnTime);
            this.mirror = GameRandom.globalRandom.nextBoolean();
            this.texture = GameResources.smallBoneSpikes == null ? null : GameRandom.globalRandom.getOneOf((new GameTextureSection(GameResources.smallBoneSpikes)).sprite(0, 0, 64), (new GameTextureSection(GameResources.smallBoneSpikes)).sprite(1, 0, 64), (new GameTextureSection(GameResources.smallBoneSpikes)).sprite(2, 0, 64), (new GameTextureSection(GameResources.smallBoneSpikes)).sprite(3, 0, 64));
            this.behaviour = new GroundPillar.TimedBehaviour(750, 150, 150);
        }

        public DrawOptions getDrawOptions(Level level, long currentTime, double distanceMoved, GameCamera camera)
        {
            GameLight light = level.getLightLevel(this.x / 32, this.y / 32);
            int drawX = camera.getDrawX(this.x);
            int drawY = camera.getDrawY(this.y);
            double height = this.getHeight(currentTime, distanceMoved);
            int endY = (int)(height * (double)this.texture.getHeight());
            return this.texture.section(0, this.texture.getWidth(), 0, endY).initDraw().mirror(this.mirror, false).light(light).pos(drawX - this.texture.getWidth() / 2, drawY - endY);
        }
    }
}
