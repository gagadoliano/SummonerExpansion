package summonerexpansion.allprojs.foeprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
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

public class HorrorCultMinionProj extends Projectile {
    public HorrorCultMinionProj() {
        super(true);
    }

    public HorrorCultMinionProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
    {
        this();
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.setTarget(targetX, targetY);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setDistance(distance);
        this.setOwner(owner);
    }

    public void init()
    {
        super.init();
        this.givesLight = true;
        this.height = 10.0F;
        this.piercing = 0;
        this.isSolid = false;
        this.particleDirOffset = -30.0F;
        this.particleRandomOffset = 3.0F;
        this.setWidth(5.0F);
    }

    public Color getParticleColor() {
        return new Color(25, 41, 58);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(25, 41, 58), 16.0F, 500, 10.0F);
    }

    public void refreshParticleLight()
    {
        this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, 20.0F, this.lightSaturation);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this).minLevelCopy(100.0F);
            int drawX = camera.getDrawX(this.x) - 16;
            int drawY = camera.getDrawY(this.y);
            int anim = GameUtils.getAnim(this.getWorldEntity().getTime(), 6, 400);
            final TextureDrawOptions options = this.texture.initDraw().sprite(anim, 0, 32, 64).light(light).rotate(this.getAngle(), 16, 0).pos(drawX, drawY - (int)this.getHeight());
            topList.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            TextureDrawOptions shadowOptions = this.shadowTexture.initDraw().sprite(anim, 0, 32, 64).light(light).rotate(this.getAngle(), 16, 0).pos(drawX, drawY);
            topList.add((tm) -> shadowOptions.draw());
        }
    }
}