package summonerexpansion.mobs.cavemobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedPlayerChaserWandererAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.hostile.VampireMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.particle.SmokePuffParticle;
import necesse.entity.projectile.VampireProjectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.TilePosition;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class VampireMiniBossMob extends HostileMob
{
    public static LootTable lootTable;
    private boolean isBat;

    public VampireMiniBossMob()
    {
        super(750);
        setArmor(15);
        setSpeed(60.0F);
        setFriction(1.0F);
        moveAccuracy = 20;
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
        ai = new BehaviourTreeAI<>(this, new ConfusedPlayerChaserWandererAI<VampireMiniBossMob>(null, 400, 520, 400, true, false)
        {
            public boolean attackTarget(VampireMiniBossMob mob, Mob target)
            {
                if (mob.canAttack() && !mob.isBat)
                {
                    GameDamage damage = new GameDamage(35.0F);
                    mob.attack(target.getX(), target.getY(), false);
                    mob.getLevel().entityManager.projectiles.add(new VampireProjectile(mob.x, mob.y, target.x, target.y, damage, mob));
                    mob.getLevel().entityManager.projectiles.add(new VampireProjectile(mob.x, mob.y, target.x + GameRandom.globalRandom.getFloatBetween(-250f, 250f), target.y + GameRandom.globalRandom.getFloatBetween(-250f, 250f), damage, mob));
                    mob.getLevel().entityManager.projectiles.add(new VampireProjectile(mob.x, mob.y, target.x + GameRandom.globalRandom.getFloatBetween(-250f, 250f), target.y + GameRandom.globalRandom.getFloatBetween(-250f, 250f), damage, mob));
                    wanderAfterAttack = GameRandom.globalRandom.getChance(0.75F);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    private void tickIsBat()
    {
        boolean nextIsBat = (isAccelerating() || hasCurrentMovement()) && getSpeedModifier() > 0.0F;
        if (isBat != nextIsBat)
        {
            isBat = nextIsBat;
            if (isClient())
            {
                getLevel().entityManager.addParticle(new SmokePuffParticle(getLevel(), x, y), Particle.GType.IMPORTANT_COSMETIC);
                SoundManager.playSound((new SoundSettings(GameResources.swing1)).basePitch(1.1F).volume(0.2F).fallOffDistance(1200), this);
            }
        }
    }

    public void clientTick()
    {
        super.clientTick();
        tickIsBat();
    }

    public void serverTick()
    {
        super.serverTick();
        tickIsBat();
    }

    public int getFlyingHeight() {
        return isBat ? 20 : super.getFlyingHeight();
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.vampire.body, i, 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
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
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, MobRegistry.Textures.vampire)).sprite(sprite).dir(dir).mask(swimMask).light(light).applyEnemyTracker(this, perspective);
        float animProgress = getAttackAnimProgress();
        if (isAttacking)
        {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(dir).armSprite(MobRegistry.Textures.vampire.body, 0, 8, 32).swingRotation(animProgress);
            humanDrawOptions.attackAnim(attackOptions, animProgress);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        Point p = new Point(0, dir);
        if (!isBat)
        {
            if (!inLiquid(x, y))
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
            p.x = GameUtils.getAnim(getWorldEntity().getTime(), 4, 400) + 1;
        }
        return p;
    }

    public int getTileWanderPriority(TilePosition pos, Biome baseBiome)
    {
        return pos.tileID() == TileRegistry.cryptAshID ? 1000 : super.getTileWanderPriority(pos, baseBiome);
    }

    public int getRockSpeed() {
        return 25;
    }

    public DeathMessageTable getDeathMessages() {
        return getDeathMessages("vamp", 3);
    }

    static
    {
        lootTable = new LootTable(
                LootItem.between("batwing", 2, 20),
                LootItem.between("demonicbar", 1, 10),
                new ChanceLootItem(0.05F, "bloodplatemask"),
                new ChanceLootItem(0.05F, "bloodplatecowl"),
                new ChanceLootItem(0.05F, "bloodplatechestplate"),
                new ChanceLootItem(0.05F, "bloodplateboots"),
                HostileMob.randomMapDrop);
    }
}