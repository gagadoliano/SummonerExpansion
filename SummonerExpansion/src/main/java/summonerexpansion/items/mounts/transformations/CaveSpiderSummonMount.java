package summonerexpansion.items.mounts.transformations;

import necesse.engine.GlobalData;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.Packet;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.hostile.GiantCaveSpiderMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.CaveSpiderSpitProjectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.item.toolItem.projectileToolItem.ProjectileToolItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class CaveSpiderSummonMount extends BaseTransformMount implements MountAbility
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 20);
    public GiantCaveSpiderMob.Variant variant;
    public CaveSpiderSummonMount()
    {
        super(45.0F);
        setFriction(3.0F);
        variant = GiantCaveSpiderMob.Variant.BLACK;
        collision = new Rectangle(-20, -20, 40, 40);
        hitBox = new Rectangle(-30, -25, 60, 50);
        selectBox = new Rectangle(-40, -45, 80, 60);
        swimMaskMove = 20;
        swimMaskOffset = -24;
        swimSinkOffset = 0;
    }

    public void runMountAbility(PlayerMob player, Packet content)
    {
        if (isServer())
        {
            GameCamera camera = GlobalData.getCurrentState().getCamera();
            if (camera == null) {
                return;
            }
            player.attack(camera.getMouseLevelPosX(), camera.getMouseLevelPosY(), false);
            Point point = ProjectileToolItem.controlledRangePosition(GameRandom.globalRandom, player.getX(), player.getY(), camera.getMouseLevelPosX(), camera.getMouseLevelPosY(), Math.max(320, 10), 32, 16);
            int pointDistance = (int)player.getDistance((float)point.x, (float)point.y);
            player.getLevel().entityManager.projectiles.add(new CaveSpiderSpitProjectile(variant, x, y, (float)point.x, (float)point.y, damage, player, pointDistance));
            abilityCooldown = 30;
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 10; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), variant.texture.get().body, 14 + i / 5, i % 5, 48, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 48;
        int drawY = camera.getDrawY(y) - 60;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        if (isAttacking)
        {
            sprite.x = 6;
        }
        final MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions body = ((MobTexture)variant.texture.get()).body.initDraw().sprite(sprite.x, sprite.y, 96).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                body.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = ((MobTexture)variant.texture.get()).shadow.initDraw().sprite(0, sprite.y, 96).light(light).pos(drawX, drawY);
        tileList.add((tm) -> shadow.draw());
    }

    public int getRockSpeed() {
        return 15;
    }

    public void attack(int x, int y, boolean showAllDirections)
    {
        super.attack(x, y, showAllDirections);
        setFacingDir(attackDir.x, attackDir.y);
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        setFacingDir(attackDir.x, attackDir.y);
        if (isClient())
        {
            SoundManager.playSound(GameResources.spit, SoundEffect.effect(this));
        }
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue<>(BuffModifiers.INTIMIDATED, true));
    }
}