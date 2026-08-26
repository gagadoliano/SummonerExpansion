package summonerexpansion.items.mounts.transformations;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.Packet;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.SwampBoltProjectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class SwampShooterSummonMount extends BaseTransformMount implements MountAbility
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 50);

    public SwampShooterSummonMount()
    {
        super(0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -41, 28, 48);
    }

    public void runMountAbility(PlayerMob player, Packet content)
    {
        if (isServer())
        {
            Point aim = readAimTarget(content);
            if (aim == null) {
                return;
            }
            player.attack(aim.x, aim.y, false);
            float projVel = player.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
            player.getLevel().entityManager.projectiles.add(new SwampBoltProjectile(getLevel(), player, x, y, aim.x, aim.y, (100F * projVel), 800, damage, 50));
            abilityCooldown = 8;
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.swampShooter, 10, i, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 56;
        drawY += this.getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        boolean mirror = false;
        int spriteY;
        if (this.attackDir != null)
        {
            float threshold = 0.4F;
            if (Math.abs(this.attackDir.x) - Math.abs(this.attackDir.y) <= threshold)
            {
                spriteY = this.attackDir.y < 0.0F ? 0 : 2;
                if (this.attackDir.x < 0.0F)
                {
                    mirror = true;
                }
            }
            else
            {
                spriteY = this.attackDir.x < 0.0F ? 3 : 1;
            }
        }
        else
        {
            int dir = this.getDir();
            if (dir != 0 && dir != 1)
            {
                spriteY = 3;
            }
            else
            {
                spriteY = 1;
            }
        }
        final DrawOptions body = MobRegistry.Textures.swampShooter.initDraw().sprite(0, spriteY, 64).mirror(mirror, false).startGlowOptions(this, this.getID()).light(light).applyEnemyTracker(this, perspective).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                body.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.human_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += this.getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        return shadowTexture.initDraw().sprite(0, 0, res).light(light).pos(drawX, drawY);
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (this.isClient())
        {
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(this).pitch(1.2F));
        }
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue<>(BuffModifiers.INTIMIDATED, true));
    }
}