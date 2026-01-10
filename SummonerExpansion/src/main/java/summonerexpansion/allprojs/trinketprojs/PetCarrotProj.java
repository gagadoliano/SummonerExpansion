package summonerexpansion.allprojs.trinketprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class PetCarrotProj extends Projectile
{
    public PetCarrotProj() {}

    public void init()
    {
        super.init();
        height = 18;
        setWidth(8.0F);
        piercing = 2;
        dropItem = true;
        heightBasedOnDistance = true;
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(186, 91, 14), 10.0F, 250, 18.0F);
    }

    protected void dropItem()
    {
        super.dropItem();
        if (GameRandom.globalRandom.nextInt(100) <= 10)
        {
            getLevel().entityManager.pickups.add((new InventoryItem("carrot")).getPickupEntity(getLevel(), x, y));
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }

    protected void playHitSound(float x, float y)
    {
        SoundManager.playSound(GameResources.bowhit, SoundEffect.effect(x, y));
    }
}
