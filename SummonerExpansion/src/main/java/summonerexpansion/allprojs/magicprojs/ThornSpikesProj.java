package summonerexpansion.allprojs.magicprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GroundPillar;
import necesse.engine.util.GroundPillarList;
import necesse.entity.Entity;
import necesse.entity.manager.GroundPillarHandler;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.geom.Point2D;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.thornSpike;

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
            mirror = GameRandom.globalRandom.nextBoolean();
            texture = thornSpike == null ? null : GameRandom.globalRandom.getOneOf((new GameTextureSection(thornSpike)).sprite(0, 0, 48), (new GameTextureSection(thornSpike)).sprite(1, 0, 48), (new GameTextureSection(thornSpike)).sprite(2, 0, 48));
            behaviour = new GroundPillar.TimedBehaviour(300, 200, 800);
        }

        public DrawOptions getDrawOptions(Level level, long currentTime, double distanceMoved, GameCamera camera)
        {
            GameLight light = level.getLightLevel(Entity.getTileCoordinate(x), Entity.getTileCoordinate(y));
            int drawX = camera.getDrawX(x);
            int drawY = camera.getDrawY(y);
            double height = getHeight(currentTime, distanceMoved);
            int endY = (int)(height * (double)texture.getHeight());
            return texture.section(0, texture.getWidth(), 0, endY).initDraw().mirror(mirror, false).light(light).pos(drawX - texture.getWidth() / 2, drawY - endY);
        }
    }
}