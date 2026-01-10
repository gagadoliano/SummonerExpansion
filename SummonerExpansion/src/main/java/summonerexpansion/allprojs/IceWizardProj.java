package summonerexpansion.allprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
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
import summonerexpansion.codes.registry.SummonerBuffs;

import java.awt.*;
import java.util.List;

public class IceWizardProj extends Projectile
{
    private long spawnTime;

    public IceWizardProj() {
    }

    public IceWizardProj(float x, float y, float targetX, float targetY, float speed, GameDamage damage, Mob owner)
    {
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDamage(damage);
        this.setOwner(owner);
        this.setDistance(400);
    }

    public void init()
    {
        super.init();
        setWidth(8.0F);
        height = 18.0F;
        spawnTime = getWorldEntity().getTime();
        trailOffset = 0.0F;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (isServer())
        {
            if (mob != null)
            {
                ActiveBuff ab = new ActiveBuff(SummonerBuffs.SummonerDebuffs.ICEWIZARDFROST, mob, 10.0F, this.getOwner());
                mob.addBuff(ab, true);
            }
        }
    }

    public Trail getTrail()
    {
        return new Trail(this, getLevel(), new Color(36, 174, 214), 14.0F, 250, getHeight());
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y) - texture.getHeight() / 2;
            float rotate = (float)(getWorldEntity().getTime() - spawnTime);
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(rotate, texture.getWidth() / 2, texture.getHeight() / 2).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager)
                {
                    options.draw();
                }
            });
            addShadowDrawables(tileList, drawX, drawY, light, rotate, texture.getHeight() / 2);
        }
    }
}