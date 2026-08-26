package summonerexpansion.projectiles.hostile;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.ParticleOption;
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

import java.awt.*;
import java.util.List;

public class PoisonSwampProj extends Projectile
{
    public PoisonSwampProj() {
    }

    public PoisonSwampProj(float x, float y, float angle, float speed, int distance, GameDamage damage, Mob owner)
    {
        this.x = x;
        this.y = y;
        this.setAngle(angle);
        this.speed = speed;
        this.setDamage(damage);
        this.setOwner(owner);
        this.setDistance(distance);
    }

    public void init()
    {
        super.init();
        this.height = 18.0F;
        this.isSolid = true;
        this.givesLight = false;
        this.trailOffset = 0.0F;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (this.isServer())
        {
            if (mob != null)
            {
                ActiveBuff ab = new ActiveBuff(BuffRegistry.Debuffs.SPIDER_VENOM, mob, 10.0F, this.getOwner());
                mob.addBuff(ab, true);
                if (this.modifier != null)
                {
                    this.modifier.doHitLogic(mob, object, x, y);
                }
            }
        }
    }

    public Color getParticleColor() {
        return new Color(102, 128, 83);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(102, 128, 83), 16.0F, 500, 16.0F);
    }

    protected int getExtraSpinningParticles() {
        return super.getExtraSpinningParticles() + 1;
    }

    protected void modifySpinningParticle(ParticleOption particle)
    {
        super.modifySpinningParticle(particle);
        particle.sizeFades(8, 14);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y) - this.texture.getHeight() / 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), this.shadowTexture.getHeight() / 2);
        }
    }
}