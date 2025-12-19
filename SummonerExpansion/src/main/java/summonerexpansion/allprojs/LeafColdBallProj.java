package summonerexpansion.allprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
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

public class LeafColdBallProj extends Projectile
{
    private long spawnTime;

    public LeafColdBallProj() {}

    public void init()
    {
        super.init();
        height = 18;
        piercing = 0;
        trailOffset = 0.0F;
        spawnTime = this.getLevel().getWorldEntity().getTime();
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (this.isServer())
        {
            if (mob != null)
            {
                ActiveBuff ab = new ActiveBuff("chilled", mob, 10.0F, this.getOwner());
                mob.addBuff(ab, true);
            }
        }
    }

    public Color getParticleColor() {
        return new Color(0, 107, 145);
    }

    protected int getExtraSpinningParticles() {
        return super.getExtraSpinningParticles() + 2;
    }

    protected void modifySpinningParticle(ParticleOption particle) {
        particle.lifeTime(1000);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(0, 107, 145), 12.0F, 500, 18.0F);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y) - this.texture.getHeight() / 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    ((TextureDrawOptions)options).draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), this.shadowTexture.getHeight() / 2);
        }
    }

    public float getAngle() {
        return (float)(this.getWorldEntity().getTime() - this.spawnTime);
    }
}
