package summonerexpansion.projectiles.magic;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GroundPillar;
import necesse.engine.util.GroundPillarList;
import necesse.entity.Entity;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.manager.GroundPillarHandler;
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
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.geom.Point2D;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryParticlesTextures.thornSpike;

public class ThornSpikesProj extends Projectile
{
    private double distCounter;
    private double distBuffer;
    private final GroundPillarList<ThornPillar> pillars = new GroundPillarList<>();

    public ThornSpikesProj() {
    }

    public ThornSpikesProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
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
        maxMovePerTick = 12;
        height = 0.0F;
        piercing = 2;
        setWidth(30.0F);
        if (isClient()) {
            getLevel().entityManager.addPillarHandler(new GroundPillarHandler<ThornPillar>(pillars)
            {
                protected boolean canRemove() {
                    return removed();
                }

                public double getCurrentDistanceMoved() {
                    return distCounter;
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
        distCounter += movedDist;
        distBuffer += movedDist;

        while(distBuffer > (double)12.0F)
        {
            distBuffer -= 12.0F;
            synchronized(pillars)
            {
                pillars.add(new ThornPillar((int)(x + GameRandom.globalRandom.floatGaussian() * 6.0F), (int)(y + GameRandom.globalRandom.floatGaussian() * 4.0F), distCounter, getWorldEntity().getLocalTime()));
            }
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
    }

    public static class ThornPillar extends GroundPillar
    {
        public GameTextureSection texture;
        public boolean mirror;

        public ThornPillar(int x, int y, double spawnDistance, long spawnTime)
        {
            super(x, y, spawnDistance, spawnTime);
            this.mirror = GameRandom.globalRandom.nextBoolean();
            this.texture = null;
            GameTexture pillarSprites = thornSpike;
            if (pillarSprites != null)
            {
                int res = pillarSprites.getHeight();
                int sprite = GameRandom.globalRandom.nextInt(pillarSprites.getWidth() / res);
                this.texture = (new GameTextureSection(thornSpike)).sprite(sprite, 0, res);
            }

            this.behaviour = new GroundPillar.TimedBehaviour(300, 200, 800);
        }

        public DrawOptions getDrawOptions(Level level, long currentTime, double distanceMoved, GameCamera camera, PlayerMob perspective)
        {
            GameLight light = level.getLightLevel(LevelEvent.getTileCoordinate(this.x), LevelEvent.getTileCoordinate(this.y));
            int drawX = camera.getDrawX(this.x);
            int drawY = camera.getDrawY(this.y);
            double height = this.getHeight(currentTime, distanceMoved);
            int endY = (int)(height * (double)this.texture.getHeight());
            return this.texture.section(0, this.texture.getWidth(), 0, endY).initDraw().mirror(this.mirror, false).light(light).pos(drawX - this.texture.getWidth() / 2, drawY - endY);
        }
    }
}