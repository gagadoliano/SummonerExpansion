package summonerexpansion.items.equips.mounts;

import necesse.engine.Settings;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.input.Control;
import necesse.engine.localization.Localization;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.StaminaBuff;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MountFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class ChiefSummonMount extends MountFollowingMob implements ActiveMountAbility
{
    protected int ChiefAxeCharge = 0;

    public ChiefSummonMount()
    {
        super(100);
        setSpeed(60.0F);
        setFriction(3F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-26, -24, 52, 48);
        selectBox = new Rectangle(-19, -52, 38, 64);
        swimMaskMove = 20;
        swimMaskOffset = -20;
        swimSinkOffset = 0;
    }

    public void onActiveMountAbilityStarted(PlayerMob player, Packet content)
    {
        ChiefAxeCharge = 20;
    }

    public boolean tickActiveMountAbility(PlayerMob player, boolean isRunningClient)
    {
        ChiefAxeCharge = 20;
        if (ChiefAxeCharge > 0)
        {
            long msToDeplete = 9000L;
            float usage = 50.0F / (float)msToDeplete;
            if (!StaminaBuff.useStaminaAndGetValid(player, usage))
            {
                return false;
            }

            player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("chiefbuff"), player, 1F, this), true);
        }
        return !isRunningClient || Control.TRINKET_ABILITY.isDown();
    }

    public void onActiveMountAbilityUpdate(PlayerMob player, Packet content) {
    }

    public void onActiveMountAbilityStopped(PlayerMob player) {
        ChiefAxeCharge = 0;
    }

    public boolean canRunMountAbility(PlayerMob player, Packet content)
    {
        return player.isServer() && !Settings.strictServerAuthority || StaminaBuff.canStartStaminaUsage(player);
    }

    public void serverTick()
    {
        super.serverTick();
        if (ChiefAxeCharge > 0)
        {
            setSpeed(90.0F);
            --ChiefAxeCharge;
        }
        else
        {
            setSpeed(60.0F);
        }
    }

    public void clientTick()
    {
        super.clientTick();
        if (ChiefAxeCharge > 0)
        {
            setSpeed(90.0F);
            --ChiefAxeCharge;
        }
        else
        {
            setSpeed(60.0F);
        }
    }

    protected String getInteractTip(PlayerMob perspective, boolean debug)
    {
        return isMounted() ? null : Localization.translate("controls", "usetip");
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            int sprite = GameRandom.globalRandom.nextInt(7);
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.chieftain.body, sprite % 4, 8 + i / 4, 64, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 64;
        int drawY = camera.getDrawY(y) - 86;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        Point armCenterPos;
        if (dir == 0)
        {
            armCenterPos = new Point(70, 20);
        }
        else if (dir == 1)
        {
            armCenterPos = new Point(53, 23);
        }
        else if (dir == 2)
        {
            armCenterPos = new Point(41, 22);
        }
        else
        {
            armCenterPos = new Point(73, 25);
        }
        MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, MobRegistry.Textures.chieftain)).sprite(sprite, 128).size(128, 128).mask(swimMask).dir(dir).light(light).attackOffsets(armCenterPos.x, armCenterPos.y, 20, 30, 25, 0, 30);
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.human_big_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += this.getBobbing(x, y);
        return shadowTexture.initDraw().sprite(this.getDir(), 0, res).light(light).pos(drawX, drawY);
    }

    public int getRockSpeed() {
        return 30;
    }

    public boolean shouldDrawRider() {
        return false;
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.ARMOR, 0.10F), new ModifierValue(BuffModifiers.MAX_HEALTH, 0.10F));
    }
}