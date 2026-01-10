package summonerexpansion.allprojs.trinketprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.util.List;

public class PetBeetProj extends Projectile
{
    public PetBeetProj() {}

    public void init()
    {
        super.init();
        height = 22;
        setWidth(22.0F);
        dropItem = true;
        heightBasedOnDistance = true;
    }

    protected void dropItem()
    {
        super.dropItem();
        if (GameRandom.globalRandom.nextInt(100) <= 10)
        {
            getLevel().entityManager.pickups.add((new InventoryItem("beet")).getPickupEntity(getLevel(), x, y));
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y);
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(getAngle(), texture.getWidth() / 2, 0).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this) {public void draw(TickManager tickManager) {
                    options.draw();
                }});
        }
    }
}
