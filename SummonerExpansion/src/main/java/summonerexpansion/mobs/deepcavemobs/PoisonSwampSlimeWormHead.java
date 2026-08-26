package summonerexpansion.mobs.deepcavemobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerChargingCirclingChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.hostile.HostileWormMobHead;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class PoisonSwampSlimeWormHead extends HostileWormMobHead<PoisonSwampSlimeWormBody, PoisonSwampSlimeWormHead>
{
    public static LootTable lootTable = new LootTable(LootItem.between("swampslime", 1, 10), HostileMob.randomMapDrop);
    public static float lengthPerBodyPart = 20.0F;
    public static float waveLength = 350.0F;
    public static GameDamage headCollisionDamage = new GameDamage(60.0F);
    public static GameDamage bodyCollisionDamage = new GameDamage(40.0F);

    public PoisonSwampSlimeWormHead()
    {
        super(800, waveLength, 70.0F, 30, 0.0F, -24.0F);
        moveAccuracy = 120;
        setSpeed(50.0F);
        setArmor(20);
        accelerationMod = 1.0F;
        decelerationMod = 1.0F;
        collision = new Rectangle(-16, -14, 32, 28);
        hitBox = new Rectangle(-20, -16, 40, 32);
        selectBox = new Rectangle(-20, -35, 40, 40);
    }

    protected float getDistToBodyPart(PoisonSwampSlimeWormBody bodyPart, int index, float lastDistance)
    {
        float length = lengthPerBodyPart;
        if (index >= 24)
        {
            int sprite = index - 24 + 1;
            length = Math.max(length - (float)(sprite * 2), 5.0F);
        }
        float movedDist = 0.0F;
        WormMoveLine first = moveLines.getFirst();
        if (first != null)
        {
            movedDist = first.movedDist;
        }
        movedDist /= 0.77F;
        return length - (float)((Math.sin(((float)index - movedDist / (lengthPerBodyPart * 0.8F)) / 2.0F) + (double)1.0F) / (double)2.0F) * length * 0.6F;
    }

    protected void onUpdatedBodyPartPos(PoisonSwampSlimeWormBody bodyPart, int index, float distToBodyPart)
    {
        super.onUpdatedBodyPartPos(bodyPart, index, distToBodyPart);
        int minSprite = 0;
        if (index >= 24)
        {
            minSprite = index - 24 + 1;
        }
        float movedDist = 0.0F;
        WormMoveLine first = moveLines.getFirst();
        if (first != null)
        {
            movedDist = first.movedDist;
        }
        movedDist /= 0.77F;
        float percent = 1.0F - (float)((Math.sin(((float)index - movedDist / (lengthPerBodyPart * 0.8F)) / 2.0F) + (double)1.0F) / (double)2.0F);
        int lerp = GameMath.limit(GameMath.lerp(percent, 0, 4), minSprite, Math.max(minSprite, 4));
        bodyPart.sprite = new Point(0, lerp);
    }

    protected PoisonSwampSlimeWormBody createNewBodyPart(int index)
    {
        PoisonSwampSlimeWormBody bodyPart = new PoisonSwampSlimeWormBody();
        bodyPart.sharesHitCooldownWithNext = index % 3 < 2;
        bodyPart.relaysBuffsToNext = index % 3 < 2;
        bodyPart.bodyIndex = index;
        if (index >= 24)
        {
            int sprite = index - 24 + 1;
            bodyPart.sprite = new Point(0, sprite);
        }
        else
        {
            bodyPart.sprite = new Point(0, 0);
        }
        return bodyPart;
    }

    protected void playMoveSound()
    {
        SoundManager.playSound(GameResources.slimeSplash2, SoundEffect.effect(this).volume(0.2F).pitch(GameRandom.globalRandom.getFloatBetween(0.9F, 1.2F)));
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerChargingCirclingChaserAI<>(null, 900, 300, 20), new FlyingAIMover());
    }

    public float getTurnSpeed(float delta) {
        return super.getTurnSpeed(delta) * 1.2F;
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) {
        return null;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.slimeWorm.shadow;
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 32;
        drawY += getBobbing(x, y);
        return shadowTexture.initDraw().sprite(0, 1, 64).light(light).pos(drawX, drawY);
    }

    public Stream<ModifierValue<?>> getDefaultModifiers()
    {
        return Stream.of((new ModifierValue(BuffModifiers.SLOW, 0.0F)).max(0.2F), (new ModifierValue(BuffModifiers.POISON_DAMAGE, 0.0F)).max(0.0F));
    }

    public boolean isSlimeImmune() {
        return true;
    }
}