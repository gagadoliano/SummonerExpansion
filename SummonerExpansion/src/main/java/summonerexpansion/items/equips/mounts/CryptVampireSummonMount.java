package summonerexpansion.items.equips.mounts;

import necesse.engine.GlobalData;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.Packet;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.particle.SmokePuffParticle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.allprojs.MountCryptBoltProj;
import summonerexpansion.allprojs.MountZombieArrowProj;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class CryptVampireSummonMount extends BaseTransformMount implements MountAbility
{
    private boolean isBat;
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 65);

    public CryptVampireSummonMount()
    {
        super();
        setSpeed(100.0F);
        setFriction(2.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -41, 28, 48);
        swimMaskMove = 16;
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
            getFollowingMob().attack(camera.getMouseLevelPosX(), camera.getMouseLevelPosY(), false);
            getFollowingMob().getLevel().entityManager.projectiles.add(new MountCryptBoltProj(getLevel(), x, y, camera.getMouseLevelPosX(), camera.getMouseLevelPosY(), damage, getFollowingMob()));
            abilityCooldown = 10;
        }
    }

    private void tickIsBat()
    {
        boolean nextIsBat = (this.isAccelerating() || this.hasCurrentMovement()) && this.getSpeedModifier() > 0.0F;
        if (this.isBat != nextIsBat)
        {
            this.isBat = nextIsBat;
            if (this.isClient())
            {
                this.getLevel().entityManager.addParticle(new SmokePuffParticle(this.getLevel(), this.x, this.y), Particle.GType.IMPORTANT_COSMETIC);
                SoundManager.playSound((new SoundSettings(GameResources.swing1)).basePitch(1.1F).volume(0.2F).fallOffDistance(1200), this);
            }
        }
    }

    public void clientTick()
    {
        super.clientTick();
        this.tickIsBat();
    }

    public void serverTick()
    {
        super.serverTick();
        this.tickIsBat();
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.cryptVampire.body, i, 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, MobRegistry.Textures.cryptVampire)).sprite(sprite).dir(dir).mask(swimMask).light(light);
        float animProgress = this.getAttackAnimProgress();
        if (this.isAttacking)
        {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(dir).armSprite(MobRegistry.Textures.cryptVampire.body, 0, 8, 32).swingRotation(animProgress).light(light);
            humanDrawOptions.attackAnim(attackOptions, animProgress);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        Point p = new Point(0, dir);
        if (!this.isBat)
        {
            if (!this.inLiquid(x, y))
            {
                p.x = 0;
            }
            else
            {
                p.x = 5;
            }
        }
        else
        {
            p.x = GameUtils.getAnim(this.getWorldEntity().getTime(), 4, 400) + 1;
        }
        return p;
    }

    public int getRockSpeed() {
        return 25;
    }

    public int getFlyingHeight() {
        return this.isBat ? 20 : super.getFlyingHeight();
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue<>(BuffModifiers.INTIMIDATED, true));
    }
}