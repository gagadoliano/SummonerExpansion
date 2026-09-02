package summonerexpansion.projectiles.sentry;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class FrostbiteProj extends Projectile 
{
    public FrostbiteProj() {
    }

    public FrostbiteProj(float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner) 
    {
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDistance(distance);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setOwner(owner);
    }

    public void init()
    {
        super.init();
        piercing = 9999;
        setWidth(75.0F);
        isSolid = false;
        givesLight = true;
        particleRandomOffset = 8.0F;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (isServer())
        {
            if (mob != null && mob.getHealth() >= (mob.getMaxHealth()/2) && !mob.isBoss())
            {
                ActiveBuff ab = new ActiveBuff(BuffRegistry.FROZEN_MOB, mob, 5F, getOwner());
                mob.addBuff(ab, true);
                if (modifier != null)
                {
                    modifier.doHitLogic(mob, object, x, y);
                }
            }
        }
    }

    public Color getParticleColor()
    {
        return new Color(64, 151, 234);
    }

    public Trail getTrail() {
        return null;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - 64;
            int drawY = camera.getDrawY(y) - 64;
            TextureDrawOptions options = MobRegistry.Textures.cryoQueen.initDraw().sprite(2, 3, 128).light(light).rotate(getAngle() - 135.0F, 64, 64).pos(drawX, drawY);
            topList.add((tm) -> options.draw());
        }
    }
}