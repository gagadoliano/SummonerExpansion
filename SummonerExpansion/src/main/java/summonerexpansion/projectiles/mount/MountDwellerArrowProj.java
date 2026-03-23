package summonerexpansion.projectiles.mount;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.SwampDwellerStaffPetalProjectile;
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

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class MountDwellerArrowProj extends Projectile
{
    private float startSpeed;

    public MountDwellerArrowProj() {
    }

    public MountDwellerArrowProj(float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner)
    {
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDistance(distance);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setOwner(owner);
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextFloat(this.startSpeed);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        this.startSpeed = reader.getNextFloat();
    }

    public void init()
    {
        super.init();
        height = 14.0F;
        startSpeed = speed;
        setWidth(8.0F);
        canBounce = false;
    }

    public void onMoveTick(Point2D.Float startPos, double movedDist)
    {
        super.onMoveTick(startPos, movedDist);
        float perc = Math.abs(GameMath.limit(traveledDistance / (float)distance, 0.0F, 1.0F) - 1.0F);
        speed = Math.max(50.0F, perc * startSpeed);
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (isServer())
        {
            int projectiles;
            if (mob == null)
            {
                projectiles = 6;
            }
            else
            {
                projectiles = 4;
            }

            float startX = x - dx * 2.0F;
            float startY = y - dy * 2.0F;
            float angle = (float) GameRandom.globalRandom.nextInt(360);

            for(int i = 0; i < projectiles; ++i)
            {
                Point2D.Float dir = GameMath.getAngleDir(angle + (float)i * 360.0F / (float)projectiles);
                SwampDwellerStaffPetalProjectile projectile = new SwampDwellerStaffPetalProjectile(getLevel(), getOwner(), startX, startY, startX + dir.x * 100.0F, startY + dir.y * 100.0F, startSpeed, distance * 100, getDamage().modFinalMultiplier(0.66F), knockback);
                if (modifier != null)
                {
                    modifier.initChildProjectile(projectile, 0.5F, projectiles / 2);
                }
                if (mob != null)
                {
                    projectile.startHitCooldown(mob);
                }
                getLevel().entityManager.projectiles.add(projectile);
            }
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(this).volume(1.2F).falloffDistance(1000));
        }
    }

    public Color getParticleColor() {
        return new Color(46, 71, 50);
    }

    public Trail getTrail()
    {
        return new Trail(this, getLevel(), getParticleColor(), 12.0F, 200, getHeight());
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), 0);
        }
    }

    protected SoundSettings getMoveSound() {
        return (new SoundSettings(GameResources.swing2)).volume(0.2F);
    }
}