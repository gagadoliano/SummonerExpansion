package summonerexpansion.allprojs.armorprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class TitaniumArrowProj extends Projectile
{
    public void init()
    {
        super.init();
        this.maxMovePerTick = 16;
        this.height = 18.0F;
        this.heightBasedOnDistance = true;
        this.bouncing = 0;
        this.givesLight = false;
        this.trailParticles = 0.0F;
    }

    public Color getParticleColor() {
        return new Color(220, 235, 255, 80);
    }

    public Trail getTrail()
    {
        return new Trail(this, getLevel(), getParticleColor(), 8.0F, 300, getHeight());
    }

    public float getParticleChance() {
        return 0.5F;
    }

    protected int getExtraSpinningParticles() {
        return 0;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - 9;
            int drawY = camera.getDrawY(y);
            int spriteX = GameUtils.getAnim(getWorldEntity().getTime(), 7, 500);
            final DrawOptions options = texture.initDraw().sprite(spriteX, 0, 18, 32).rotate(getAngle(), 9, 0).light(light).pos(drawX, drawY - (int)getHeight());
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