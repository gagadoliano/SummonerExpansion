package summonerexpansion.summonmobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedPlayerChaserWandererAI;
import necesse.entity.mobs.hostile.FlyingHostileMob;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.hostile.SkeletonThrowerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

public class WoodMob extends FlyingHostileMob
{
    public static GameDamage baseDamage = new GameDamage(10F);
    public static GameDamage incursionDamage = new GameDamage(50F);
    public float moveAngle;
    public static GameTexture texture;
    public ParticleTypeSwitcher particleTypes;

    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(0.2f, "oaklog", 1, 10),
            ChanceLootItem.between(0.2f, "palmlog", 1, 10),
            ChanceLootItem.between(0.2f, "pinelog", 1, 10),
            ChanceLootItem.between(0.2f, "maplelog", 1, 10),
            ChanceLootItem.between(0.2f, "birchlog", 1, 10),
            ChanceLootItem.between(0.2f, "sprucelog", 1, 10),
            ChanceLootItem.between(0.2f, "willowlog", 1, 10)
    );

    public WoodMob()
    {
        super(100);
        setSpeed(40);
        setFriction(0.4f);
        setKnockbackModifier(0.0F);
        setArmor(10);
        moveAccuracy = 20;
        attackCooldown = 4000;
        collision = new Rectangle(0, 0, 24, 32);
        hitBox = new Rectangle(0, 0, 24, 32);
        selectBox = new Rectangle(0, 0, 24, 32);
        particleTypes = new ParticleTypeSwitcher(Particle.GType.COSMETIC, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
    }

    @Override
    public void init()
    {
        super.init();
        final GameDamage damage;
        if (getLevel() instanceof IncursionLevel)
        {
            setMaxHealth(500);
            setHealthHidden(getMaxHealth());
            setArmor(20);
            damage = incursionDamage;
        }
        else
        {
            damage = baseDamage;
        }

        ai = new BehaviourTreeAI(this, new ConfusedPlayerChaserWandererAI<WoodMob>(null, 600, 200, 100, false, true)
        {
            public boolean attackTarget(WoodMob mob, Mob target)
            {
                boolean success = shootSimpleProjectile(mob, target, "woodmobproj", damage, 10, 250);
                if (success)
                {
                    wanderAfterAttack = GameRandom.globalRandom.getChance(0.25F);
                }
                return success;
            }
        });
    }

    public void clientTick()
    {
        super.clientTick();
        if (GameRandom.globalRandom.getChance(0.2F))
        {
            float particleX = (x + 20) + GameRandom.globalRandom.floatGaussian() * 5.0F;
            float particleY = (y + 20) + GameRandom.globalRandom.floatGaussian() * 10.0F;
            float moveX = GameRandom.globalRandom.floatGaussian() * 10.0F;
            float moveY = GameRandom.globalRandom.floatGaussian() * 10.0F;
            this.getLevel().entityManager.addParticle(particleX, particleY, particleTypes.next()).sprite(GameResources.magicSparkParticles.sprite(GameRandom.globalRandom.nextInt(4), 0, 32)).sizeFades(10, 20).movesFriction(moveX, moveY, 0.8F).color(new Color(14, 99, 29)).givesLight(190.0F, 0.9F).height(0.0F).ignoreLight(true).lifeTime(500);
        }
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x);
        int drawY = camera.getDrawY(y);
        DrawOptions body = texture.initDraw().light(light).rotate(moveAngle, 0, 0).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}