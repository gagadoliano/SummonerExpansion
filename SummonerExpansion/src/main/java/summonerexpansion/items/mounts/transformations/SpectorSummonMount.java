package summonerexpansion.items.mounts.transformations;

import necesse.engine.Settings;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.Packet;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.MountAbility;
import necesse.entity.mobs.PlayerMob;
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
import summonerexpansion.items.mounts.minions.MountSpectorSummonMinion;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class SpectorSummonMount extends BaseTransformMount implements MountAbility
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 50);

    public SpectorSummonMount()
    {
        super(65.0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -13, 20, 26);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-16, -32, 32, 38);
    }

    public void runMountAbility(PlayerMob player, Packet content)
    {
        if (isServer())
        {
            MountSpectorSummonMinion mob1 = new MountSpectorSummonMinion();
            player.serverFollowersManager.addFollower("spectorminion", mob1, FollowPosition.WALK_CLOSE, "summonedmob", 1, 5, null, false);
            mob1.updateDamage(damage);
            mob1.setRemoveWhenNotInInventory(ItemRegistry.getItem("forestspectorhands"), CheckSlotType.MOUNT);
            player.getLevel().entityManager.addMob(mob1, player.x, player.y);
            abilityCooldown = 30;
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.forestSpector, i, 8, 32, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 56;
        int anim = GameUtils.getAnim(getTime(), 5, 500);
        drawY += getBobbing(x, y);
        final DrawOptions options = MobRegistry.Textures.forestSpector.initDraw().sprite(anim, getDir(), 64).light(light).alpha(0.8F).pos(drawX, drawY);
        topList.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.forestSpector_shadow.initDraw().sprite(anim, getDir(), 64, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> shadow.draw());
    }

    public int getRockSpeed() {
        return 10;
    }

    public int getFlyingHeight() {
        return 64;
    }

    public boolean isWaterWalking() {
        return true;
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue<>(BuffModifiers.INTIMIDATED, true));
    }
}