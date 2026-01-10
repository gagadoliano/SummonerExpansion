package summonerexpansion.allprojs.trinketprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.boomerangProjectile.SpinningProjectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;
import summonerexpansion.codes.events.PumpkinExplosionLevelEvent;

import java.awt.*;
import java.util.List;

public class PetPumpkinProj extends SpinningProjectile
{
    public PetPumpkinProj() {}

    public void init()
    {
        super.init();
        height = 24;
        setWidth(24.0F);
        dropItem = true;
        heightBasedOnDistance = true;
    }

    protected void dropItem()
    {
        super.dropItem();
        if (GameRandom.globalRandom.nextInt(100) <= 10)
        {
            getLevel().entityManager.pickups.add((new InventoryItem("pumpkin")).getPickupEntity(getLevel(), x, y));
        }
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (mob != null && this.isServer())
        {
            PumpkinExplosionLevelEvent event = new PumpkinExplosionLevelEvent(x, y, 80, getDamage(), getAttackOwner());
            getLevel().entityManager.events.add(event);
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y) - texture.getHeight() / 2;
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(getAngle(), texture.getWidth() / 2, texture.getHeight() / 2).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }

    public float getAngle() {
        return super.getAngle() * 1.5F;
    }
}