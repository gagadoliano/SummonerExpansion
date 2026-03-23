package summonerexpansion.items.mounts.transformations;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.Packet;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.items.mounts.minions.MountWolfSummonMinion;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class WolfSummonMount extends BaseTransformMount implements MountAbility
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 35);

    public WolfSummonMount()
    {
        super(65.0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-18, -24, 36, 36);
        swimMaskMove = 16;
        swimMaskOffset = 0;
        swimSinkOffset = -12;
    }

    public void runMountAbility(PlayerMob player, Packet content)
    {
        if (isServer())
        {
            MountWolfSummonMinion mob1 = new MountWolfSummonMinion();
            player.serverFollowersManager.addFollower("wolfminion", mob1, FollowPosition.WALK_CLOSE, "summonedmob", 1, 5, null, false);
            mob1.updateDamage(damage);
            mob1.setRemoveWhenNotInInventory(ItemRegistry.getItem("snowwolftail"), CheckSlotType.MOUNT);
            player.getLevel().entityManager.addMob(mob1, player.x, player.y);
            abilityCooldown = 30;
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.snowWolf.body, 12, i, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 36;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions body = MobRegistry.Textures.snowWolf.body.initDraw().sprite(sprite.x, sprite.y, 64).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                body.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.snowWolf.shadow.initDraw().sprite(0, sprite.y, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> shadow.draw());
    }

    public int getSwimMaskMove() {
        return this.getDir() != 2 ? super.getSwimMaskMove() + 4 : super.getSwimMaskMove();
    }

    public int getRockSpeed() {
        return 13;
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue<>(BuffModifiers.INTIMIDATED, true));
    }
}