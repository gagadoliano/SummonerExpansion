package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.DryadSpiritFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.codes.events.SpiritGhoulPoolEvent;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Stream;

public class SpiritGhoulMinion extends AttackingFollowingMob
{
    public static GameTexture texture;
    protected double distanceRanSinceLastPoolSpawn;

    public SpiritGhoulMinion() 
    {
        super(10);
        setSpeed(15.0F);
        setFriction(4.0F);
        setSwimSpeed(1.5F);
        moveAccuracy = 8;
        collision = new Rectangle(-12, -5, 24, 20);
        hitBox = new Rectangle(-16, -8, 32, 26);
        selectBox = new Rectangle(-20, -20, 40, 40);
        swimMaskMove = 16;
        swimMaskOffset = 0;
        swimSinkOffset = -4;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<SpiritGhoulMinion>(600, summonDamage, 50, 900, 800, 100)
        {
            public boolean attackTarget(SpiritGhoulMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    target.isServerHit(summonDamage, mob.dx, mob.dy, 15.0F, mob);
                    Buff dryadHaunted = BuffRegistry.Debuffs.DRYAD_HAUNTED;
                    ActiveBuff ab = new ActiveBuff(dryadHaunted, target, 60F, getAttackOwner());
                    target.buffManager.addBuff(ab, true);
                    if (target.buffManager.getStacks(dryadHaunted) >= 10)
                    {
                        target.buffManager.removeBuff(dryadHaunted, true);
                        SpiritGhoulMinion.spawnDryadSpirit(getAttackOwner());
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void serverTick()
    {
        super.serverTick();
        if (!inLiquid())
        {
            double distanceRan = getDistanceRan();
            double poolSpawnRunDistance = 16.0;
            if (distanceRan - distanceRanSinceLastPoolSpawn > poolSpawnRunDistance)
            {
                SpiritGhoulPoolEvent event = new SpiritGhoulPoolEvent(this, (int)x, (int)y, GameRandom.globalRandom, summonDamage.modFinalMultiplier(0.5F), 4.0F);
                getLevel().entityManager.addLevelEvent(event);
                distanceRanSinceLastPoolSpawn = distanceRan;
            }
        }
    }

    public static void spawnDryadSpirit(Mob owner)
    {
        if (owner != null && owner.isServer())
        {
            int maxSummons = 2;
            DryadSpiritFollowingMob summonedMob = (DryadSpiritFollowingMob)MobRegistry.getMob("dryadspirit", owner.getLevel());
            ((ItemAttackerMob)owner).serverFollowersManager.addFollower("summonedmobtemp", summonedMob, FollowPosition.FLYING_CIRCLE_FAST, "summonedmob", 1.0F, (p) -> maxSummons, null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(summonedMob, owner.getLevel(), owner.x, owner.y);
            owner.getLevel().entityManager.addMob(summonedMob, spawnPoint.x, spawnPoint.y);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), texture, i, 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 36;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions body = texture.initDraw().sprite(sprite.x, sprite.y, 64).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager)
            {
                swimMask.use();
                body.draw();
                swimMask.stop();
            }
        });
    }

    public int getSwimMaskMove() {
        return getDir() != 2 ? super.getSwimMaskMove() + 4 : super.getSwimMaskMove();
    }

    public int getRockSpeed() {
        return 15;
    }

    public Stream<ModifierValue<?>> getDefaultModifiers()
    {
        return Stream.of((new ModifierValue(BuffModifiers.FRICTION, 0.0F)).min(0.75F));
    }
}