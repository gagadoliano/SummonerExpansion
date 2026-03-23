package summonerexpansion.items.mounts.transformations;

import necesse.engine.GlobalData;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.Packet;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.projectiles.IceMinionJavelinProj;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class FrozenDwarfSummonMount extends BaseTransformMount implements MountAbility
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 24);
    public boolean hasHair;

    public FrozenDwarfSummonMount()
    {
        super(45.0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -35, 28, 42);
        swimMaskMove = 14;
        swimMaskOffset = -2;
        swimSinkOffset = -4;
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
            player.getLevel().entityManager.projectiles.add(new IceMinionJavelinProj(x, y, camera.getMouseLevelPosX(), camera.getMouseLevelPosY(), damage, player));
            abilityCooldown = 10;
        }
    }

    public void init()
    {
        super.init();
        hasHair = (new GameRandom(getUniqueID())).nextSeeded(51).nextBoolean();
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.frozenDwarf.body, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        float animProgress = getAttackAnimProgress();
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, MobRegistry.Textures.frozenDwarf)).hairTexture(hasHair ? MobRegistry.Textures.frozenDwarfHair : null).sprite(sprite).dir(dir).mask(swimMask).light(light).attackOffsets(dir == 3 ? 36 : 28, 23, 10, 15, 12, 4, 12);
        if (isAttacking)
        {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(dir).itemSprite(MobRegistry.Textures.frozenDwarf.body, 0, 9, 32).itemRotatePoint(4, 4).itemEnd().armSprite(MobRegistry.Textures.frozenDwarf.body, 0, 8, 32).swingRotation(animProgress).light(light);
            humanDrawOptions.attackAnim(attackOptions, animProgress);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public int getRockSpeed() {
        return 20;
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (isClient())
        {
            SoundManager.playSound(GameResources.swing1, SoundEffect.effect(this).volume(0.5F));
        }
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue<>(BuffModifiers.INTIMIDATED, true));
    }
}