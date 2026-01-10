package summonerexpansion.allprojs.armorprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class SetMageSlimeBoltProj extends FollowingProjectile
{
    public SetMageSlimeBoltProj() {
    }

    public SetMageSlimeBoltProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
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
    }

    public void init()
    {
        super.init();
        turnSpeed = 0.13F;
        givesLight = true;
        height = 10.0F;
        piercing = 0;
        isSolid = true;
        particleDirOffset = -30.0F;
        particleRandomOffset = 3.0F;
        setWidth(5.0F);
    }

    public float getTurnSpeed(int targetX, int targetY, float delta)
    {
        return getTurnSpeed(delta) * invDynamicTurnSpeedMod(targetX, targetY, (float)getTurnRadius());
    }

    public Color getParticleColor()
    {
        return new Color(43, 161, 214);
    }

    public Trail getTrail()
    {
        return new Trail(this, getLevel(), new Color(32, 131, 176), 16.0F, 500, 10.0F);
    }

    public void updateTarget()
    {
        if (traveledDistance > 10.0F)
        {
            findTarget((m) -> m.isHostile, 50.0F, 960.0F);
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - 16;
            int drawY = camera.getDrawY(y);
            int anim = GameUtils.getAnim(getWorldEntity().getTime(), 6, 400);
            final TextureDrawOptions options = texture.initDraw().sprite(anim, 0, 32, 64).light(light).rotate(getAngle(), 16, 0).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager)
                {
                    options.draw();
                }
            });
            TextureDrawOptions shadowOptions = shadowTexture.initDraw().sprite(anim, 0, 32, 64).light(light).rotate(getAngle(), 16, 0).pos(drawX, drawY);
            tileList.add((tm) -> shadowOptions.draw());
        }
    }

    protected SoundSettings getSpawnSound()
    {
        return new SoundSettings(GameResources.zap2);
    }

    protected SoundSettings getHitSound()
    {
        return (new SoundSettings(GameResources.slimeSplash4)).volume(0.3F);
    }

    protected SoundSettings getDisappearSound()
    {
        return getHitSound();
    }
}