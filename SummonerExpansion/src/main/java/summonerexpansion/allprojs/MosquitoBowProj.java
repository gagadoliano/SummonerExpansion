package summonerexpansion.allprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.ParticleOption;
import necesse.entity.particle.fireworks.FireworksExplosion;
import necesse.entity.particle.fireworks.FireworksPath;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;
import summonerexpansion.codes.events.MosquitoBowEvent;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class MosquitoBowProj extends Projectile
{
    protected boolean isFallingProjectile;
    protected Point2D.Float targetPoints;
    protected GameDamage damage;
    protected float eventResilienceGain;

    public MosquitoBowProj() {
    }

    public MosquitoBowProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, float eventResilienceGain, int knockback, Point2D.Float targetPoints, boolean isFallingProjectile) 
    {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.damage = damage;
        this.eventResilienceGain = eventResilienceGain;
        this.knockback = knockback;
        this.targetPoints = targetPoints;
        this.isFallingProjectile = isFallingProjectile;
    }

    public void init() 
    {
        super.init();
        height = 40.0F;
        piercing = 0;
        isSolid = false;
        heightBasedOnDistance = true;
        trailOffset = -25.0F;
        removeIfOutOfBounds = false;
        canBreakObjects = false;
        setWidth(6.0F, false);
    }

    public void setupSpawnPacket(PacketWriter writer) 
    {
        super.setupSpawnPacket(writer);
        writer.putNextBoolean(isFallingProjectile);
        if (targetPoints != null) 
        {
            writer.putNextBoolean(true);
            writer.putNextFloat(targetPoints.x);
            writer.putNextFloat(targetPoints.y);
        } 
        else 
        {
            writer.putNextBoolean(false);
        }
    }

    public void applySpawnPacket(PacketReader reader) 
    {
        super.applySpawnPacket(reader);
        isFallingProjectile = reader.getNextBoolean();
        if (reader.getNextBoolean()) 
        {
            targetPoints = new Point2D.Float(reader.getNextFloat(), reader.getNextFloat());
        } 
        else 
        {
            targetPoints = null;
        }
    }

    public boolean canHit(Mob mob) {
        return false;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) 
    {
        super.doHitLogic(mob, object, x, y);
        if (!isFallingProjectile) 
        {
            Projectile projectile = new MosquitoBowProj(getLevel(), getOwner(), targetPoints.x, targetPoints.y - (float)distance, targetPoints.x, targetPoints.y, speed, distance, damage, eventResilienceGain, knockback, null, true);
            projectile.getUniqueID((new GameRandom(getUniqueID())).nextSeeded(69));
            getLevel().entityManager.projectiles.addHidden(projectile);
        } 
        else 
        {
            float targetY;
            float targetX;
            if (mob != null) 
            {
                targetX = mob.x;
                targetY = mob.y;
            } 
            else 
            {
                targetX = x;
                targetY = y;
            }
            int lifetime = 200;
            int range = 65;
            if (!isServer()) 
            {
                SoundManager.playSound(GameResources.bowhit, SoundEffect.effect(this));
                SoundManager.playSound(GameResources.slimeSplash3, SoundEffect.effect(this));
                FireworksExplosion explosion = new FireworksExplosion(FireworksPath.sphere((float)GameRandom.globalRandom.getIntBetween(range - 10, range)));
                explosion.colorGetter = (particle, progress, random) -> ParticleOption.randomizeColor(54.0F, 0F, 0.2F, 0F, 0F, 0F);
                explosion.trailChance = 0.5F;
                explosion.particles = 40;
                explosion.lifetime = lifetime;
                explosion.popOptions = null;
                explosion.particleLightHue = 0.0F;
                explosion.explosionSound = null;
                explosion.spawnExplosion(getLevel(), targetX, targetY, getHeight(), GameRandom.globalRandom);
            }
            if (!isClient())
            {
                Rectangle targetBox = new Rectangle((int)targetX - range, (int)targetY - range, range * 2, range * 2);
                streamTargets(getOwner(), targetBox).filter((m) -> m.canBeHit(this) && m.getDistance(targetX, targetY) <= (float)range).forEach((m) -> {
                    m.isServerHit(getDamage(), m.x - x, m.y - y, (float)knockback, this);
                });
            }
            getLevel().entityManager.addLevelEventHidden(new MosquitoBowEvent(getOwner(), (int)x, (int)y, new GameRandom(getUniqueID()), getDamage(), eventResilienceGain));
        }
    }

    public Color getParticleColor() {
        return new Color(62, 59, 32);
    }

    public Trail getTrail() 
    {
        Trail trail = new Trail(this, getLevel(), new Color(87, 83, 44), 12.0F, 200, getHeight());
        trail.drawOnTop = true;
        return trail;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) 
    {
        if (!removed()) 
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y);
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(getAngle(), texture.getWidth() / 2, 0).pos(drawX, drawY - (int)getHeight());
            topList.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            addShadowDrawables(tileList, drawX, drawY, light, getAngle(), 0);
        }
    }
}
