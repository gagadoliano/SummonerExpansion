package summonerexpansion.allprojs.meleeprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.HorrorWaveParticles;

public class HorrorWaveProj extends Projectile
{
    public HorrorWaveProj()
    {
    }

    public HorrorWaveProj(Level level, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, Mob owner)
    {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDamage(damage);
        this.setOwner(owner);
        this.setDistance(distance);
    }

    public void init()
    {
        super.init();
        this.piercing = 10;
        this.height = 16.0F;
        this.setWidth(45.0F, true);
        this.isSolid = true;
        this.givesLight = true;
        this.particleRandomOffset = 14.0F;
    }

    public Color getParticleColor()
    {
        return new Color(169, 37, 33);
    }

    public Trail getTrail()
    {
        return null;
    }

    public void clientTick()
    {
        super.clientTick();
        float particleAngle = this.getAngle() - 45.0F - 90.0F;
        this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.COSMETIC).sprite(HorrorWaveParticles.sprite(0, 0, 54)).color((options, lifeTime, timeAlive, lifePercent) -> options.alpha(0.5F - 0.5F * lifePercent)).height(this.height).size((options, lifeTime, timeAlive, lifePercent) -> options.size(54, 54)).rotation((lifeTime, timeAlive, lifePercent) -> particleAngle).lifeTime(300);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed())
        {
            float alpha = this.getFadeAlphaTime(300, 200);
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y - this.getHeight()) - this.texture.getHeight() / 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light.minLevelCopy(Math.min(light.getLevel() + 100.0F, 150.0F))).rotate(this.getAngle() - 135.0F, this.texture.getWidth() / 2, this.texture.getHeight() / 2).alpha(alpha).pos(drawX, drawY);
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager)
                {
                    options.draw();
                }
            });
        }
    }
}