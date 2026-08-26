package summonerexpansion.mobs.deepcavemobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.hostile.HostileWormMobBody;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import static summonerexpansion.codes.registries.RegistryMobTextures.poisonSwampSlimeWorm;

public class PoisonSwampSlimeWormBody extends HostileWormMobBody<PoisonSwampSlimeWormHead, PoisonSwampSlimeWormBody> 
{
    public int bodyIndex;
    public Point sprite = new Point(0, 0);

    public PoisonSwampSlimeWormBody() 
    {
        super(800);
        setArmor(20);
        collision = new Rectangle(-20, -15, 40, 30);
        hitBox = new Rectangle(-25, -20, 50, 40);
        selectBox = new Rectangle(-32, -60, 64, 64);
    }

    public GameMessage getLocalization()
    {
        return new LocalMessage("mob", "poisonswampslimewormmob");
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter)
    {
        return bodyIndex == 0 ? PoisonSwampSlimeWormHead.headCollisionDamage : PoisonSwampSlimeWormHead.bodyCollisionDamage;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        if (isVisible())
        {
            for(int i = 0; i < 2; ++i)
            {
                getLevel().entityManager.addParticle(new FleshParticle(getLevel(), poisonSwampSlimeWorm, 2, GameRandom.globalRandom.nextInt(5), 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
            }
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        if (isVisible())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - 32;
            int drawY = camera.getDrawY(y);
            WormMobHead.addDrawable(list, this, new GameSprite(poisonSwampSlimeWorm, sprite.x, sprite.y, 64), MobRegistry.Textures.slimeWorm_mask, light, (int)height, drawX, drawY, 64, perspective);
            addShadowDrawables(tileList, level, x, y, light, camera);
        }
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.slimeWorm.shadow;
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 32;
        drawY += getBobbing(x, y);
        return shadowTexture.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
    }

    public Stream<ModifierValue<?>> getDefaultModifiers()
    {
        return Stream.of((new ModifierValue(BuffModifiers.POISON_DAMAGE, 0.0F)).max(0.0F));
    }

    public boolean isSlimeImmune() {
        return true;
    }
}