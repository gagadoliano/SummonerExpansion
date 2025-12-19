package summonerexpansion.items.equips.mounts;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MountFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class MouseSummonMount extends MountFollowingMob
{
    public MouseSummonMount()
    {
        super(100);
        setSpeed(60.0F);
        setFriction(3F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-13, -18, 26, 28);
        swimMaskMove = 6;
        swimMaskOffset = 30;
        swimSinkOffset = 0;
    }

    protected String getInteractTip(PlayerMob perspective, boolean debug)
    {
        return isMounted() ? null : Localization.translate("controls", "usetip");
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.mouse.body, 12, i, 16, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 22;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        boolean mirror = (dir == 0 || dir == 2) && this.moveX < 0.0F;
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions options = MobRegistry.Textures.mouse.body.initDraw().sprite(sprite.x, sprite.y, 32).addMaskShader(swimMask).mirror(mirror, false).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.mouse.shadow.initDraw().sprite(0, dir, 32).mirror(mirror, false).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }

    public int getRockSpeed() {
        return 20;
    }

    public boolean shouldDrawRider() {
        return false;
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue(BuffModifiers.INTIMIDATED, true), new ModifierValue(BuffModifiers.TARGET_RANGE, -0.80F));
    }
}