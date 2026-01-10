package summonerexpansion.allprojs.armorprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
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

public class CloudWaterProj extends Projectile
{
    public void init()
    {
        super.init();
        height = 18.0F;
        piercing = 0;
        bouncing = 1000;
        isSolid = false;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (isServer())
        {
            if (mob != null)
            {
                ActiveBuff ab = new ActiveBuff(SummonerBuffs.SummonerDebuffs.WATERWEAK, mob, 60.0F, getOwner());
                mob.addBuff(ab, true);
            }
        }
    }

    public Color getParticleColor() {
        return new Color(55, 125, 216);
    }

    public Trail getTrail() {
        return new Trail(this, getLevel(), new Color(55, 125, 216), 6.0F, 500, 18.0F);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y);
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(getAngle(), texture.getWidth() / 2, 0).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            addShadowDrawables(tileList, drawX, drawY, light, getAngle(), 0);
        }
    }
}