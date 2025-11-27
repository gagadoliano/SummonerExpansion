package summonerexpansion.items.equips.summonmounts;

import necesse.engine.GlobalData;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.Packet;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MountFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.summonprojs.MountVultureProj;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class VultureSummonMount extends MountFollowingMob implements MountAbility
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 20);
    public int abilityCooldown = 0;

    public VultureSummonMount()
    {
        super(100);
        setSpeed(90.0F);
        setFriction(1F);
        collision = new Rectangle(-18, -15, 36, 30);
        hitBox = new Rectangle(-18, -15, 36, 36);
        selectBox = new Rectangle(-20, -34, 40, 36);
    }

    public void runMountAbility(PlayerMob player, Packet content)
    {
        if (this.isServer())
        {
            GameCamera camera = GlobalData.getCurrentState().getCamera();
            if (camera == null) {
                return;
            }
            getFollowingMob().attack(camera.getMouseLevelPosX(), camera.getMouseLevelPosY(), false);
            getFollowingMob().getLevel().entityManager.projectiles.add(new MountVultureProj(x, y, camera.getMouseLevelPosX(), camera.getMouseLevelPosY(), damage, getFollowingMob()));
            abilityCooldown = 30;
        }
    }

    public boolean canRunMountAbility(PlayerMob player, Packet content) {
        return abilityCooldown == 0;
    }

    public void serverTick()
    {
        super.serverTick();
        if (abilityCooldown > 0)
        {
            abilityCooldown--;
        }
    }

    public void clientTick()
    {
        super.clientTick();
        if (abilityCooldown > 0)
        {
            abilityCooldown--;
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
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.vultureHatchling, GameRandom.globalRandom.nextInt(4), 2, 32, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void setFacingDir(float deltaX, float deltaY)
    {
        if (deltaX < 0.0F)
        {
            setDir(0);
        }
        else if (deltaX > 0.0F)
        {
            setDir(1);
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 32 - 16;
        int dir = getDir();
        long time = level.getWorldEntity().getTime() % 350L;
        int sprite;
        if (time < 100L)
        {
            sprite = 0;
        }
        else if (time < 200L)
        {
            sprite = 1;
        }
        else if (time < 300L)
        {
            sprite = 2;
        }
        else
        {
            sprite = 3;
        }
        float rotate = dx / 10.0F;
        DrawOptions options = MobRegistry.Textures.vultureHatchling.initDraw().sprite(sprite, 0, 64).light(light).mirror(dir == 0, false).rotate(rotate, 32, 32).pos(drawX, drawY);
        topList.add((tm) -> options.draw());
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.vultureHatchling_shadow;
        int drawX = camera.getDrawX(x) - shadowTexture.getWidth() / 2;
        int drawY = camera.getDrawY(y) - shadowTexture.getHeight() / 2 + 13;
        return shadowTexture.initDraw().light(light).pos(drawX, drawY);
    }

    public boolean shouldDrawRider() {
        return false;
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue<>(BuffModifiers.INTIMIDATED, true), new ModifierValue<>(BuffModifiers.WATER_WALKING, true));
    }
}