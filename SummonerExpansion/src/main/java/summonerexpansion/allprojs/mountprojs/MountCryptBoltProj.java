package summonerexpansion.allprojs.mountprojs;

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

public class MountCryptBoltProj extends FollowingProjectile
{
    public MountCryptBoltProj()
    {
    }

    public MountCryptBoltProj(Level level, float x, float y, float targetX, float targetY, GameDamage damage, Mob owner)
    {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = 100F;
        this.setDamage(damage);
        this.setOwner(owner);
        this.setDistance(1000);
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

    public Color getParticleColor() {
        return new Color(177, 5, 0);
    }

    public Trail getTrail() {
        return new Trail(this, getLevel(), new Color(177, 5, 0), 16.0F, 500, 10.0F);
    }

    public void updateTarget()
    {
        if (this.traveledDistance > 10.0F)
        {
            this.findTarget((m) -> m.isHostile, 0.0F, 1000F);
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
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            TextureDrawOptions shadowOptions = shadowTexture.initDraw().sprite(anim, 0, 32, 64).light(light).rotate(getAngle(), 16, 0).pos(drawX, drawY);
            tileList.add((tm) -> shadowOptions.draw());
        }
    }

    protected SoundSettings getSpawnSound()
    {
        return (new SoundSettings(GameResources.magicbolt1)).basePitch(1.5F).volume(0.2F);
    }
}