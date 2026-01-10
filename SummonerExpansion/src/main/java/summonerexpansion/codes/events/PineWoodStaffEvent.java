package summonerexpansion.codes.events;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GroundPillar;
import necesse.engine.util.GroundPillarList;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.WeaponShockWaveLevelEvent;
import necesse.entity.manager.GroundPillarHandler;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;

import static summonerexpansion.codes.registry.SummonerTextures.PineWoodSpike;

public class PineWoodStaffEvent extends WeaponShockWaveLevelEvent
{
    protected final GroundPillarList<PineWoodStaffEvent.PineWoodPillar> pillars = new GroundPillarList<>();

    public PineWoodStaffEvent() {
        super(30.0F, 20.0F, 5.0F);
    }

    public PineWoodStaffEvent(Mob owner, int x, int y, GameRandom uniqueIDRandom, float targetAngle, GameDamage damage, float resilienceGain, float velocity, float knockback, float range)
    {
        super(owner, x, y, uniqueIDRandom, targetAngle, 30.0F, 20.0F, 5.0F, damage, resilienceGain, velocity, knockback, range);
    }

    public void init()
    {
        super.init();
        if (isClient())
        {
            level.entityManager.addPillarHandler(new GroundPillarHandler<PineWoodStaffEvent.PineWoodPillar>(pillars)
            {
                protected boolean canRemove() {
                    return isOver();
                }

                public double getCurrentDistanceMoved() {
                    return 0.0F;
                }
            });
            if (owner != null)
            {
                SoundManager.playSound(GameResources.shake, SoundEffect.effect(owner));
                SoundManager.playSound(GameResources.stomp, SoundEffect.effect(owner).volume(0.5F));
            }
        }
    }

    protected void spawnHitboxParticles(Polygon hitbox) {
    }

    protected void spawnHitboxParticles(float radius, float startAngle, float endAngle)
    {
        if (isClient())
        {
            synchronized(pillars)
            {
                for(Point2D.Float pos : getPositionsAlongHit(radius, startAngle, endAngle, 20.0F, false))
                {
                    pillars.add(new PineWoodStaffEvent.PineWoodPillar((int)(pos.x + GameRandom.globalRandom.getFloatBetween(-10.0F, 10.0F)), (int)(pos.y + GameRandom.globalRandom.getFloatBetween(-10.0F, 10.0F)), radius, level.getWorldEntity().getLocalTime()));
                }
            }
        }
    }

    public int getPierceLimit() {
        return 10;
    }

    public static class PineWoodPillar extends GroundPillar
    {
        public GameTextureSection texture;
        public boolean mirror;

        public PineWoodPillar(int x, int y, double spawnDistance, long spawnTime)
        {
            super(x, y, spawnDistance, spawnTime);
            mirror = GameRandom.globalRandom.nextBoolean();
            texture = null;
            GameTexture pillarSprites = PineWoodSpike;
            if (pillarSprites != null)
            {
                int res = pillarSprites.getHeight();
                int sprite = GameRandom.globalRandom.nextInt(pillarSprites.getWidth() / res);
                texture = (new GameTextureSection(PineWoodSpike)).sprite(sprite, 0, res);
            }
            behaviour = new GroundPillar.TimedBehaviour(200, 100, 200);
        }

        public DrawOptions getDrawOptions(Level level, long currentTime, double distanceMoved, GameCamera camera)
        {
            GameLight light = level.getLightLevel(LevelEvent.getTileCoordinate(x), LevelEvent.getTileCoordinate(y));
            int drawX = camera.getDrawX(x);
            int drawY = camera.getDrawY(y);
            double height = getHeight(currentTime, distanceMoved);
            int endY = (int)(height * (double)texture.getHeight());
            return texture.section(0, texture.getWidth(), 0, endY).initDraw().mirror(mirror, false).light(light).pos(drawX - texture.getWidth() / 2, drawY - endY);
        }
    }
}