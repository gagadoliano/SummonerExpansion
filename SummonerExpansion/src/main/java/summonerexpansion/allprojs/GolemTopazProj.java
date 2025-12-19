package summonerexpansion.allprojs;

import java.awt.Color;
import java.util.List;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.boomerangProjectile.SpinningProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.ThemeColorRegistry;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

public class GolemTopazProj extends SpinningProjectile
{
    public GolemTopazProj() {}

    public void init()
    {
        super.init();
        this.setWidth(5.0F, true);
        this.height = 18.0F;
        this.bouncing = 20;
        this.piercing = 5;
        this.distance = 2000;
        this.speed = 140.0F;
    }

    public Color getParticleColor() {
        return ThemeColorRegistry.TOPAZ.getRandomColor();
    }

    protected Color getWallHitColor() {
        return ThemeColorRegistry.TOPAZ.getRandomColor();
    }

    protected int getExtraSpinningParticles() {
        return 0;
    }

    public Trail getTrail() {
        return null;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
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

    public float getAngle() {
        return super.getAngle() * 1.5F;
    }
}