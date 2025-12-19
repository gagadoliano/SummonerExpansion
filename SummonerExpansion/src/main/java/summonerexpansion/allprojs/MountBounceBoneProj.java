package summonerexpansion.allprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
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

public class MountBounceBoneProj extends Projectile
{
    public MountBounceBoneProj()
    {
    }

    public MountBounceBoneProj(float x, float y, float targetX, float targetY, GameDamage damage, Mob owner)
    {
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = 100.0F;
        this.setDamage(damage);
        this.setOwner(owner);
        this.setDistance(2000);
    }

    public void init()
    {
        super.init();
        setWidth(10.0F);
        height = 18.0F;
        spawnTime = getWorldEntity().getTime();
        trailOffset = 0.0F;
        piercing = 5;
        bouncing = 10;
    }

    public Trail getTrail() {
        return new Trail(this, getLevel(), new Color(170, 170, 170), 10.0F, 250, 18.0F);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(getX()) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(getY()) - texture.getHeight() / 2;
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(getAngle(), texture.getWidth() / 2, texture.getHeight() / 2).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            addShadowDrawables(tileList, drawX, drawY, light, getAngle(), texture.getHeight() / 2);
        }
    }

    public float getAngle()
    {
        return dx < 0.0F ? (float)(spawnTime - getWorldEntity().getTime()) : (float)(getWorldEntity().getTime() - spawnTime);
    }
}