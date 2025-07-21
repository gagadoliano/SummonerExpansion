package summonerexpansion.summonmounts;

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
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class ChiefSummonMount extends MountFollowingMob implements ActiveMountAbility
{
    public static GameTexture texture;
    public static GameTexture texture_mask;
    protected int ChiefAxeCharge = 0;

    public ChiefSummonMount()
    {
        super(100);
        setSpeed(60.0F);
        setFriction(3F);
        setSwimSpeed(0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-26, -24, 52, 48);
        selectBox = new Rectangle(-19, -52, 38, 64);
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
        return player.isServer() && !Settings.strictServerAuthority ? true : StaminaBuff.canStartStaminaUsage(player);
    }

    public void serverTick()
    {
        super.serverTick();
        if (ChiefAxeCharge > 0)
        {
            --ChiefAxeCharge;
        }
    }

    public void clientTick()
    {
        super.clientTick();
        if (ChiefAxeCharge > 0)
        {
            --ChiefAxeCharge;
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

    @Override
    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 64;
        int drawY = camera.getDrawY(y) - 86;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final DrawOptions options = texture.initDraw().sprite(sprite.x, sprite.y, 128).light(light).pos(drawX - 2, drawY);
        list.add(new MobDrawable()
        {
            @Override
            public void draw(TickManager tickManager) {
            }

            @Override
            public void drawBehindRider(TickManager tickManager) {
                options.draw();
            }
        });
    }

    @Override
    public Point getSpriteOffset(int spriteX, int spriteY)
    {
        Point point = new Point(0, 0);
        if (isAccelerating() && (spriteX == 1 || spriteX == 2))
        {
            point.y = -5;
        }
        point.x += getRiderDrawXOffset();
        point.y += getRiderDrawYOffset() + 12;
        return point;
    }

    @Override
    public int getRiderDrawYOffset()
    {
        PlayerMob player = (PlayerMob) getFollowingMob();
        if (player != null)
        {
            return -25;
        }
        else
        {
            return 0;
        }
    }

    public GameTexture getRiderMask() {
        return texture_mask;
    }

    public int getRockSpeed() {
        return 50;
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.ARMOR, 0.10F), new ModifierValue(BuffModifiers.MAX_HEALTH, 0.10F));
    }
}