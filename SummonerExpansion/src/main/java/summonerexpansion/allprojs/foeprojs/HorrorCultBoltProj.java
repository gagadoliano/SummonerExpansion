package summonerexpansion.allprojs.foeprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
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
import java.util.Comparator;
import java.util.List;

public class HorrorCultBoltProj extends FollowingProjectile
{
    public HorrorCultBoltProj()
    {
    }

    public HorrorCultBoltProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
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
        this.turnSpeed = 0.13F;
        this.givesLight = true;
        this.height = 10.0F;
        this.piercing = 0;
        this.isSolid = true;
        this.particleDirOffset = -30.0F;
        this.particleRandomOffset = 3.0F;
        this.setWidth(5.0F);
    }

    public float getTurnSpeed(int targetX, int targetY, float delta)
    {
        return this.getTurnSpeed(delta) * this.invDynamicTurnSpeedMod(targetX, targetY, (float)this.getTurnRadius());
    }

    public Color getParticleColor()
    {
        return new Color(177, 5, 0);
    }

    public Trail getTrail()
    {
        return new Trail(this, this.getLevel(), new Color(177, 5, 0), 16.0F, 500, 10.0F);
    }

    public void updateTarget()
    {
        if (!this.isClient())
        {
            if (this.target != null && this.target != this.getOwner())
            {
                if (!this.isSamePlace(this.target) || ((Mob)this.target).getDistance(this.getOwner()) > 960.0F)
                {
                    this.target = this.getOwner();
                    this.sendServerTargetUpdate(false);
                }
            }
            else
            {
                ServerClient target = GameUtils.streamServerClients(this.getLevel()).min(Comparator.comparing((m) -> m.playerMob.getDistance(this.getOwner()))).orElse(null);
                if (target != null && target.playerMob != this.getOwner() && target.playerMob.getDistance(this.getOwner()) < 960.0F)
                {
                    this.target = target.playerMob;
                    this.sendServerTargetUpdate(false);
                }
            }
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - 16;
            int drawY = camera.getDrawY(this.y);
            int anim = GameUtils.getAnim(this.getWorldEntity().getTime(), 6, 400);
            final TextureDrawOptions options = this.texture.initDraw().sprite(anim, 0, 32, 64).light(light).rotate(this.getAngle(), 16, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager)
                {
                    options.draw();
                }
            });
            TextureDrawOptions shadowOptions = this.shadowTexture.initDraw().sprite(anim, 0, 32, 64).light(light).rotate(this.getAngle(), 16, 0).pos(drawX, drawY);
            tileList.add((tm) -> shadowOptions.draw());
        }
    }

    protected SoundSettings getSpawnSound()
    {
        return (new SoundSettings(GameResources.magicbolt1)).basePitch(1.5F).volume(0.2F);
    }
}