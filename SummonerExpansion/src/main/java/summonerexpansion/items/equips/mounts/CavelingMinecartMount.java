package summonerexpansion.items.equips.mounts;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MinecartMountMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class CavelingMinecartMount extends MinecartMountMob
{
    public static GameTexture texture;

    public CavelingMinecartMount()
    {
        this.setSpeed(200.0F);
        this.setFriction(3.0F);
        this.accelerationMod = 0.05F;
    }

    @Override
    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 47;
        Point sprite = this.getAnimSprite(x, y, this.minecartDir);
        drawY += this.getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions behind = texture.initDraw().sprite(sprite.x, sprite.y, 64).addMaskShader(swimMask).light(light).pos(drawX, drawY);

        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager) {}
            
            @Override
            public void drawBehindRider(TickManager tickManager)
            {
                swimMask.use();
                behind.draw();
                swimMask.stop();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue<>(BuffModifiers.WATER_WALKING, true), new ModifierValue<>(BuffModifiers.ITEM_PICKUP_RANGE, 5F));
    }
}