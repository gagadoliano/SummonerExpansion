package summonerexpansion.projectiles.magic;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;
import summonerexpansion.buffs.debuffs.SwampFlaskDebuff;
import summonerexpansion.codes.events.SwampSlimeFlaskExplosionEvent;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryParticlesTextures.swampSlimeFlaskDebris;

public class SwampSlimeFlaskProj extends Projectile
{
    public int splashRange;

    public SwampSlimeFlaskProj() {
    }

    public SwampSlimeFlaskProj(Level level, Mob owner, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, int splashRange)
    {
        this(level, owner, owner.x, owner.y, targetX, targetY, speed, distance, damage, knockback, splashRange);
    }

    public SwampSlimeFlaskProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, int splashRange)
    {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.setTarget(targetX, targetY);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setDistance(distance);
        this.setOwner(owner);
        this.splashRange = splashRange;
    }

    public void init()
    {
        super.init();
        this.spawnTime = this.getWorldEntity().getTime();
        this.isSolid = false;
        this.canHitMobs = false;
        this.doesImpactDamage = false;
        this.canBreakObjects = false;
        this.trailOffset = 0.0F;
        this.particleRandomOffset = 1.0F;
        this.particleDirOffset = 0.0F;
    }

    public float tickMovement(float delta)
    {
        float out = super.tickMovement(delta);
        float percDistance = GameMath.limit((float)distance / 100.0F, 0.0F, 2.0F) / 2.0F;
        float maxTileHeight = 2.5F;
        float travelPerc = GameMath.limit(traveledDistance / (float)distance, 0.0F, 1.0F);
        float bounceHeight = GameMath.sin(travelPerc * 180.0F);
        float groundHeight = GameMath.lerp(travelPerc, 24.0F, 0.0F);
        height = groundHeight + bounceHeight * 32.0F * maxTileHeight * percDistance;
        return out;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (!isClient())
        {
            spawnSplashEvent();
        }
    }

    public Color getParticleColor() {
        return SwampFlaskDebuff.getSwampFlaskParticleColor();
    }

    public Trail getTrail() {
        return null;
    }

    protected int getExtraSpinningParticles() {
        return 1;
    }

    protected void spawnSplashEvent()
    {
        getLevel().entityManager.events.add(new SwampSlimeFlaskExplosionEvent(x, y, splashRange, getDamage(), 0.0F, getOwner()));
    }

    protected void spawnDeathParticles()
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), swampSlimeFlaskDebris, i, 0, 32, x, y, height, dx * 5.0F, dy * 5.0F), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(getTileX(), getTileY());
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y) - texture.getHeight() / 2;
            angle = (float)(getWorldEntity().getTime() - spawnTime) / 5.0F;
            if (dx < 0.0F)
            {
                angle = -angle;
            }
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(angle, texture.getWidth() / 2, texture.getHeight() / 2).pos(drawX, drawY - (int)getHeight());
            float shadowAlpha = Math.abs(GameMath.limit(height / 250.0F, 0.0F, 1.0F) - 1.0F);
            int shadowX = camera.getDrawX(x) - shadowTexture.getWidth() / 2;
            int shadowY = camera.getDrawY(y) - shadowTexture.getHeight() / 2;
            final TextureDrawOptions shadowOptions = shadowTexture.initDraw().light(light).rotate(angle).alpha(shadowAlpha).pos(shadowX, shadowY);
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager)
                {
                    shadowOptions.draw();
                    options.draw();
                }
                public int getSortY() {
                    return super.getSortY() + (int)height;
                }
            });
        }
    }
}