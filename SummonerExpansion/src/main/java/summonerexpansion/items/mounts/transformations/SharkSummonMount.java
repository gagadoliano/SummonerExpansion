package summonerexpansion.items.mounts.transformations;

import necesse.engine.GlobalData;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.Packet;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundSettings;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.MountAbility;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.projectiles.mount.MountSharkWaveProj;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class SharkSummonMount extends BaseTransformMount implements MountAbility
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 25);

    public SharkSummonMount()
    {
        super(80.0F);
        setSwimSpeed(1.0F);
        setFriction(1.0F);
        collision = new Rectangle(-16, -14, 32, 28);
        hitBox = new Rectangle(-20, -16, 40, 32);
        selectBox = new Rectangle(-20, -20, 40, 40);
        swimMaskMove = 0;
        swimMaskOffset = 0;
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
            player.getLevel().entityManager.projectiles.add(new MountSharkWaveProj(player.getLevel(), x, y, camera.getMouseLevelPosX(), camera.getMouseLevelPosY(), 45f, 500, damage, player));
            abilityCooldown = 20;
        }
    }

    public float getFullInLiquidAtPercent(int x, int y) {
        return 0.4F;
    }

    public CollisionFilter getLevelCollisionFilter() {
        return super.getLevelCollisionFilter().allLandTiles();
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 3; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.shark, i, 12, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 48;
        int drawY = camera.getDrawY(y) - 48;
        Point sprite = getAnimSprite(x, y, getDir());
        final DrawOptions options = MobRegistry.Textures.fakeShark.initDraw().sprite(sprite.x, sprite.y, 96).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (isClient())
        {
            SoundManager.playSound((new SoundSettings(GameResources.sharkAttack)).volume(0.4F), this);
        }
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.human_baby_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += getBobbing(x, y);
        return shadowTexture.initDraw().sprite(getDir(), 0, res).light(light).pos(drawX, drawY);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        Point p = new Point(0, dir);
        if (Math.abs(dx) <= 0.01F && Math.abs(dy) <= 0.01F)
        {
            p.x = 0;
        }
        else
        {
            p.x = (int)(getDistanceRan() / (double)getRockSpeed()) % 4 + 1;
        }
        return p;
    }

    public int getRockSpeed() {
        return 14;
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue<>(BuffModifiers.INTIMIDATED, true));
    }
}