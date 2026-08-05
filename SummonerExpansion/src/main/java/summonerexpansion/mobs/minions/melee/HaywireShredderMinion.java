package summonerexpansion.mobs.minions.melee;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonWalkBase;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class HaywireShredderMinion extends SummonWalkBase
{
    public int lifeTime = 0;

    public HaywireShredderMinion()
    {
        super();
        setSpeed(200.0F);
        setFriction(0.5F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -32, 28, 48);
        selectBox = new Rectangle(-14, -61, 28, 80);
        swimMaskMove = 32;
        swimMaskOffset = -32;
        swimSinkOffset = -8;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(500, summonDamage, 100, 800, 800, 120));
    }

    public void clientTick()
    {
        super.clientTick();
        if (currentSpeed > 10.0F)
        {
            int moveX = GameRandom.globalRandom.getIntBetween(-55, 55);
            int moveY = GameRandom.globalRandom.getIntBetween(-1, -20);
            getLevel().entityManager.addParticle(x, y, Particle.GType.COSMETIC).sprite(GameResources.magicSparkParticles.sprite(GameRandom.globalRandom.nextInt(4), 0, 22)).color(new Color(255, 188, 78)).sizeFades(22, 44).movesConstant((float)moveX, (float)moveY).ignoreLight(true).givesLight(37.0F, 1.0F).lifeTime(200);
            SoundManager.playSound((new SoundSettings(GameResources.haywireShredder)).volume(currentSpeed / 100.0F / 3.0F).pitchVariance(0.1F), this);
        }
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime++;
        if (lifeTime >= 600)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public int stoppingDistance(float friction, float currentSpeed) {
        return 0;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.haywireShredder, GameRandom.globalRandom.nextInt(5), 12, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 80;
        int dir = getDir();
        float angle = GameMath.limit(dx / 10.0F, -10.0F, 10.0F) + (currentSpeed > 10.0F ? GameRandom.globalRandom.getFloatBetween(-5.0F, 5.0F) : 0.0F);
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions drawOptions = MobRegistry.Textures.haywireShredder.initDraw().sprite(sprite.x, sprite.y, 64, 96).addMaskShader(swimMask).rotate(angle, 32, 80).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public Stream<ModifierValue<?>> getDefaultModifiers() {
        return Stream.of(new ModifierValue<>(BuffModifiers.BOUNCY, true));
    }
}