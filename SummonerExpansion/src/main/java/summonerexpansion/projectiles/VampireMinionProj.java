package summonerexpansion.projectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;
import summonerexpansion.codes.pickups.VampireMinionHeartPickupEntity;

import java.awt.*;
import java.util.List;

public class VampireMinionProj extends FollowingProjectile 
{
    public void init() 
    {
        super.init();
        turnSpeed = 0.5F;
        givesLight = true;
        height = 18.0F;
        piercing = 0;
        bouncing = 2;
    }

    protected int getExtraSpinningParticles() {
        return super.getExtraSpinningParticles() + 1;
    }

    public Color getParticleColor() {
        return new Color(127, 0, 0);
    }

    public Trail getTrail() {
        return new Trail(this, getLevel(), new Color(127, 0, 0), 6.0F, 500, 18.0F);
    }

    public void updateTarget() 
    {
        if (traveledDistance > 100.0F) 
        {
            findTarget((m) -> m.isHostile, 160.0F, 160.0F);
        }
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) 
    {
        super.doHitLogic(mob, object, x, y);
        if (mob != null) 
        {
            if (isServer() && mob.hasDied()) 
            {
                VampireMinionHeartPickupEntity pickup = new VampireMinionHeartPickupEntity(getLevel(), (float)mob.getX(), (float)mob.getY(), 0.0F, 0.0F);
                getLevel().entityManager.pickups.add(pickup);
            }
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) 
    {
        if (!removed()) 
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y);
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(getAngle(), texture.getWidth() / 2, 0).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this) 
            {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            addShadowDrawables(tileList, drawX, drawY, light, getAngle(), 0);
        }
    }
}