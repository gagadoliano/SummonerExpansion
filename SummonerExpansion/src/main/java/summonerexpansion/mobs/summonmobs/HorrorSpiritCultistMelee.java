package summonerexpansion.mobs.summonmobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.gameAreaSearch.GameAreaStream;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.BooleanMobAbility;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.composites.SequenceAINode;
import necesse.entity.mobs.ai.behaviourTree.decorators.InverterAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.ChaserAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.CooldownAttackTargetAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.EscapeAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.TargetFinderAINode;
import necesse.entity.mobs.ai.behaviourTree.util.TargetFinderDistance;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;
import necesse.level.maps.levelBuffManager.LevelModifiers;
import necesse.level.maps.light.GameLight;
import summonerexpansion.allprojs.HorrorWaveProj;

import java.awt.*;
import java.util.List;

public class HorrorSpiritCultistMelee extends HorrorSpiritCultistMob
{
    public static GameDamage baseDamage = new GameDamage(25.0F);
    public static GameDamage incursionDamage = new GameDamage(100.0F);
    public static GameDamage projBaseDamage = new GameDamage(50.0F);
    public static GameDamage projIncursionDamage = new GameDamage(200.0F);
    protected boolean isBlocking;
    protected boolean isChargingMelee;
    protected boolean isChargingRanged;
    protected long blockStartTime;
    protected long meleeStartTime;
    protected long rangedStartTime;
    protected final BooleanMobAbility startBlockingAbility;
    protected final BooleanMobAbility chargeMeleeAbility;
    protected final BooleanMobAbility chargeRangedAbility;
    private final ParticleTypeSwitcher typeSwitcher;

    public HorrorSpiritCultistMelee()
    {
        typeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
        startBlockingAbility = registerAbility(new BooleanMobAbility()
        {
            protected void run(boolean value)
            {
                isBlocking = value;
                if (value)
                {
                    blockStartTime = getTime();
                    if (isClient())
                    {
                        GameRandom random = GameRandom.globalRandom;
                        for(int i = 0; i < 20; ++i)
                        {
                            getLevel().entityManager.addParticle(x, y, typeSwitcher.next()).sprite(GameResources.magicSparkParticles.sprite(0, 0, 22)).sizeFades(22, 44).movesFriction((float)random.getIntBetween(-100, 100), (float)random.getIntBetween(-100, 100), 0.8F).lifeTime(250);
                        }
                        SoundManager.playSound(GameResources.electricExplosion, SoundEffect.effect(getMob()).pitch(2.5F).volume(1.5F));
                    }
                }

            }
        });
        chargeMeleeAbility = registerAbility(new BooleanMobAbility()
        {
            protected void run(boolean value)
            {
                if (value != isChargingMelee)
                {
                    isChargingMelee = value;
                    if (isChargingMelee)
                    {
                        meleeStartTime = getTime();
                    }
                }

            }
        });
        chargeRangedAbility = registerAbility(new BooleanMobAbility()
        {
            protected void run(boolean value)
            {
                if (value != isChargingRanged)
                {
                    isChargingRanged = value;
                    if (isChargingRanged)
                    {
                        rangedStartTime = getTime();
                    }
                }
            }
        });
    }

    @Override
    public void init()
    {
        ai = new BehaviourTreeAI<>(this, new MeleeCultistAI(200, 64, 10000));
    }

    protected void doBeforeHitLogic(MobBeforeHitEvent event)
    {
        Mob attackerMob = event.attacker.getAttackOwner();
        if (attackerMob != null && !isAttacking && GameMath.diamondDistance(x, y, attackerMob.x, attackerMob.y) > 192.0F)
        {
            startBlockingAbility.runAndSend(true);
            event.prevent();
        }
        super.doBeforeHitLogic(event);
    }

    protected GameDamage getMeleeDamage() 
    {
        return getLevel() instanceof IncursionLevel ? incursionDamage : baseDamage;
    }

    protected GameDamage getRangedDamage() 
    {
        return getLevel() instanceof IncursionLevel ? projIncursionDamage : projBaseDamage;
    }

    public void spawnDamageText(int damage, int size, boolean isCrit)
    {
        if (!isBlocking)
        {
            super.spawnDamageText(damage, size, isCrit);
        }
    }

    public void clientTick()
    {
        super.clientTick();
        setSpeed(isBlocking ? 22.5F : 45.0F);
        if (isChargingRanged)
        {
            int particleCount = 5;
            GameRandom random = GameRandom.globalRandom;
            ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
            float anglePerParticle = 360.0F / (float)particleCount;
            for(int j = 0; j < particleCount; ++j)
            {
                int angle = (int)((float)j * anglePerParticle + random.nextFloat() * anglePerParticle);
                getLevel().entityManager.addParticle(this, (float)Math.sin(Math.toRadians(angle)) * 15.0F, (float)Math.cos(Math.toRadians(angle)) * 15.0F, typeSwitcher.next()).sizeFades(11, 22).color(new Color(169, 37, 33)).heightMoves(0.0F, 45.0F).lifeTime(200);
            }
        }
    }

    public void serverTick()
    {
        super.serverTick();
        if (isBlocking && getTime() - blockStartTime > 1000L)
        {
            startBlockingAbility.runAndSend(false);
        }
        setSpeed(isBlocking ? 22.5F : 45.0F);
    }

    @Override
    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        float animProgress = getAttackAnimProgress();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, look, false)).sprite(sprite).dir(dir).mask(swimMask).light(light).helmet(new InventoryItem("shadowhorrorhood")).chestplate(new InventoryItem("shadowhorrormantle")).boots(new InventoryItem("shadowhorrorboots"));
        if (isBlocking)
        {
            humanDrawOptions.holdItem(new InventoryItem("horrorshield"));
        }
        else if (isChargingMelee)
        {
            humanDrawOptions.itemAttack(new InventoryItem("horrorsword"), null, 0.0F, (float)dir, (float)dir);
        }
        else if (isAttacking)
        {
            humanDrawOptions.itemAttack(new InventoryItem("horrorsword"), null, animProgress, attackDir.x, attackDir.y);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public static class MeleeCultistAI<T extends HorrorSpiritCultistMelee> extends SequenceAINode<T> 
    {
        public final EscapeAINode<T> escapeAINode;
        public final CooldownAttackTargetAINode<T> atttckTargetNode;
        public final TargetFinderAINode<T> targetFinderNode;
        public final ChaserAINode<T> chaserNode;

        public MeleeCultistAI(final int shootDistance, final int meleeDistance, int searchDistance) 
        {
            addChild(new InverterAINode<>(escapeAINode = new EscapeAINode<T>() {
                public boolean shouldEscape(T mob, Blackboard<T> blackboard) {
                    return mob.isHostile && !mob.isSummoned && mob.getLevel().buffManager.getModifier(LevelModifiers.ENEMIES_RETREATING);
                }
            }));
            if (shootDistance > 0) 
            {
                addChild(atttckTargetNode = new CooldownAttackTargetAINode<T>(CooldownAttackTargetAINode.CooldownTimer.TICK, 2000, shootDistance)
                {
                    public boolean attackTarget(T mob, Mob target) 
                    {
                        if (mob.getDistance(target) > (float)meleeDistance) 
                        {
                            mob.chargeMeleeAbility.runAndSend(false);
                        }

                        if (!mob.isBlocking && mob.canAttack()) 
                        {
                            mob.chargeRangedAbility.runAndSend(true);
                            if (mob.getTime() - mob.rangedStartTime > 1000L && GameMath.diamondDistance(mob.x, mob.y, target.x, target.y) > (float)shootDistance / 2.0F) 
                            {
                                Projectile projectile = new HorrorWaveProj(mob.getLevel(), mob.x, mob.y, target.x, target.y, 125.0F, 500, mob.getRangedDamage(), mob);
                                mob.getLevel().entityManager.projectiles.add(projectile);
                                mob.chargeRangedAbility.runAndSend(false);
                                return true;
                            } 
                            else 
                            {
                                return false;
                            }
                        } 
                        else 
                        {
                            mob.chargeRangedAbility.runAndSend(false);
                            return false;
                        }
                    }
                });
                atttckTargetNode.attackTimer = atttckTargetNode.attackCooldown;
            } 
            else 
            {
                atttckTargetNode = null;
            }
            TargetFinderDistance<T> targetFinder = new TargetFinderDistance<>(searchDistance);
            targetFinder.targetLostAddedDistance = searchDistance * 2;
            addChild(targetFinderNode = new TargetFinderAINode<T>(targetFinder)
            {
                public GameAreaStream<? extends Mob> streamPossibleTargets(T mob, Point base, TargetFinderDistance<T> distance) 
                {
                    return TargetFinderAINode.streamPlayersAndHumans(mob, base, distance);
                }
            });
            addChild(chaserNode = new ChaserAINode<T>(meleeDistance, false, true)
            {
                public boolean attackTarget(T mob, Mob target) 
                {
                    mob.chargeRangedAbility.runAndSend(false);
                    if (!mob.isBlocking && mob.canAttack()) 
                    {
                        mob.chargeMeleeAbility.runAndSend(true);
                        if (mob.getTime() - mob.meleeStartTime > 300L) 
                        {
                            if (mob.getDistance(target) <= (float)meleeDistance) 
                            {
                                mob.attack(target.getX(), target.getY(), false);
                                target.isServerHit(mob.getMeleeDamage(), target.x - mob.x, target.y - mob.y, 125.0F, mob);
                                mob.addResilience(20.0F);
                            }
                            mob.chargeMeleeAbility.runAndSend(false);
                            return true;
                        } 
                        else 
                        {
                            return false;
                        }
                    } 
                    else 
                    {
                        mob.chargeMeleeAbility.runAndSend(false);
                        return false;
                    }
                }
            });
        }
    }
}