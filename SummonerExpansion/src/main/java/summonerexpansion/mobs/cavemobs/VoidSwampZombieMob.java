package summonerexpansion.mobs.cavemobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.seasons.GameSeasons;
import necesse.engine.seasons.SeasonalHat;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.NecroticSoulSkullPushEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedCollisionPlayerChaserWandererAI;
import necesse.entity.mobs.buffs.NecroticPoisonBuff;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.item.armorItem.ArmorItem;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static summonerexpansion.codes.registries.RegistryMobTextures.voidSwampZombie;

public class VoidSwampZombieMob extends HostileMob 
{
    public static LootTable lootTable;
    protected SeasonalHat hat;

    public VoidSwampZombieMob() 
    {
        super(260);
        setSpeed(24.0F);
        setFriction(3.0F);
        setArmor(15);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -41, 28, 48);
        swimMaskMove = 16;
        swimMaskOffset = -2;
        swimSinkOffset = -4;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new ConfusedCollisionPlayerChaserWandererAI<>(null, 500, new GameDamage(35.0F), 150, 40000));
        hat = GameSeasons.getHat(new GameRandom(getUniqueID()));
    }

    public LootTable getLootTable()
    {
        return hat != null ? hat.getLootTable(lootTable) : lootTable;
    }

    public DeathMessageTable getDeathMessages()
    {
        return getDeathMessages("zombie", 3);
    }

    protected void onDeath(Attacker attacker, HashSet<Attacker> attackers) 
    {
        super.onDeath(attacker, attackers);
        if (isServer()) 
        {
            NecroticSoulSkullPushEvent event = new NecroticSoulSkullPushEvent(this);
            getLevel().entityManager.events.add(event);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), voidSwampZombie.body, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }

        float maxDist = 128.0F;
        int lifeTime = 1100;
        int minHeight = 0;
        int maxHeight = 30;
        int particles = 77;

        for(int i = 0; i < particles; ++i)
        {
            float height = (float)minHeight + (float)(maxHeight - minHeight) * (float)i / (float)particles;
            AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float outDistance = GameRandom.globalRandom.getFloatBetween(60.0F, maxDist + 32.0F);
            boolean counterclockwise = GameRandom.globalRandom.nextBoolean();
            this.getLevel().entityManager.addParticle(this.x + GameRandom.globalRandom.getFloatBetween(0.0F, GameMath.sin(currentAngle.get()) * maxDist), this.y + GameRandom.globalRandom.getFloatBetween(0.0F, GameMath.cos((Float)currentAngle.get()) * maxDist * 0.75F), Particle.GType.CRITICAL).color(NecroticPoisonBuff.getNecroticParticleColor()).height(height).moves((pos, delta, cLifeTime, timeAlive, lifePercent) ->
            {
                float angle = currentAngle.accumulateAndGet(delta * 150.0F / 250.0F, Float::sum);
                if (counterclockwise)
                {
                    angle = -angle;
                }

                float linearDown = GameMath.lerpExp(lifePercent, 0.525F, 0.0F, 1.0F);
                pos.x = this.x + outDistance * GameMath.sin(angle) * linearDown;
                pos.y = this.y + outDistance * GameMath.cos(angle) * linearDown * 0.75F;
            }).lifeTime(lifeTime).sizeFades(14, 18);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, voidSwampZombie)).sprite(sprite).dir(dir).mask(swimMask).light(light).applyEnemyTracker(this, perspective);
        if (hat != null)
        {
            humanDrawOptions.hatTexture(hat.getDrawOptions(), ArmorItem.HairDrawMode.NO_HAIR);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager)
            {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public int getRockSpeed() {
        return 20;
    }

    protected SoundSettings getAmbientSound()
    {
        return (new SoundSettings(GameResources.zombieGroans[GameRandom.globalRandom.getIntBetween(10, 14)])).volume(0.35F);
    }

    protected SoundSettings getDeathSound()
    {
        return (new SoundSettings(GameResources.zombieGroans[6], GameResources.zombieGroans[7], GameResources.zombieGroans[13], GameResources.zombieGroans[18])).volume(0.3F);
    }

    static
    {
        lootTable = new LootTable(LootItem.between("voidshard", 1, 2), LootItem.between("swampslime", 1, 2), HostileMob.randomMapDrop);
    }
}