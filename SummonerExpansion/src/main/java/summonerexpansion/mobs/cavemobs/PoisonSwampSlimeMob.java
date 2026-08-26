package summonerexpansion.mobs.cavemobs;

import necesse.engine.CameraShake;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameUtils;
import necesse.engine.util.MovedRectangle;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.CoordinateMobAbility;
import necesse.entity.mobs.ability.IntMobAbility;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.composites.SelectorAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.CooldownAttackTargetAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.EscapeAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.WandererAINode;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.hostile.HostileSlimeMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.projectiles.hostile.PoisonSwampProj;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Stream;

import static summonerexpansion.codes.registries.RegistryMobTextures.poisonSwampSlime;

public class PoisonSwampSlimeMob extends HostileSlimeMob 
{
    public static LootTable lootTable = new LootTable(LootItem.between("swampsludge", 1, 2), LootItem.between("ivyore", 1, 5), LootItem.between("swampslime", 1, 2), HostileMob.randomMapDrop);
    public static GameDamage baseDamage = new GameDamage(10F);
    public static GameDamage incursionDamage = new GameDamage(90F);
    public IntMobAbility startPounceAbility;
    public CoordinateMobAbility pounceAbility;
    public Mob pounceTarget;
    public long pounceStartTime;
    public int pounceChargeTime;
    public CameraShake pounceShake;
    float startTime;
    int fireBallCounter;
    float shootInterval = 200F;
    float shootCooldown = 200F;

    public PoisonSwampSlimeMob() 
    {
        super(280);
        this.setSpeed(30.0F);
        this.setFriction(2.0F);
        this.setKnockbackModifier(0.5F);
        this.setArmor(10);
        this.jumpStats.setJumpStrength(150.0F);
        this.jumpStats.setJumpCooldown(150);
        this.collision = new Rectangle(-20, -10, 40, 16);
        this.hitBox = new Rectangle(-22, -20, 44, 28);
        this.selectBox = new Rectangle(-24, -40, 48, 48);
        this.swimMaskMove = 16;
        this.swimMaskOffset = 0;
        this.swimSinkOffset = 0;
        this.startPounceAbility = this.registerAbility(new IntMobAbility() 
        {
            protected void run(int value) 
            {
                if (value >= 0) 
                {
                    pounceStartTime = getWorldEntity().getLocalTime();
                    pounceChargeTime = value;
                    SoundManager.playSound((new SoundSettings(GameResources.slimeSplash3)).volume(0.3F), PoisonSwampSlimeMob.this);
                }
                else
                {
                    pounceStartTime = 0L;
                }
            }
        });
        this.pounceAbility = this.registerAbility(new CoordinateMobAbility()
        {
            protected void run(int x, int y)
            {
                float dist = Math.min(512.0F, getDistance((float)x, (float)y));
                Point2D.Float norm = GameMath.normalize((float)(x - getX()), (float)(y - getY()));
                runJump(norm.x * dist * 2.6F, norm.y * dist * 2.6F);
                pounceStartTime = 0L;
                SoundManager.playSound((new SoundSettings(GameResources.slimeSplash4)).volume(0.2F), PoisonSwampSlimeMob.this);
            }
        });
    }

    public void onJump()
    {
        if (this.isClient())
        {
            SoundManager.playSound((new SoundSettings(GameResources.blunthit)).volume(0.1F), this);
            SoundManager.playSound((new SoundSettings(GameResources.slimeSplash2)).volume(0.2F), this);
        }
    }

    public void init()
    {
        super.init();
        GameDamage damage;
        if (this.getLevel() instanceof IncursionLevel)
        {
            this.setMaxHealth(600);
            this.setHealthHidden(this.getMaxHealth());
            this.setArmor(30);
            damage = incursionDamage;
        }
        else
        {
            damage = baseDamage;
        }
        this.ai = new BehaviourTreeAI<>(this, new PoisonSwampSlimeMob.GiantSwampSlimeAI(300, damage, 100, CooldownAttackTargetAINode.CooldownTimer.CAN_ATTACK, 3500, 200, 4000));
        this.startTime = (float)this.getLevel().getTime();
    }

    public void serverTick()
    {
        super.serverTick();
        if (readyToShoot() && shootCooldown == 0)
        {
            spawnFireBall((float)(this.fireBallCounter * 25));
            ++this.fireBallCounter;
            if (this.fireBallCounter > 15)
            {
                fireBallCounter = 0;
                shootCooldown = 300F;
            }
        }
        else
        {
            shootCooldown--;
        }
    }

    public boolean readyToShoot()
    {
        return (float)this.getLevel().getTime() >= this.startTime + this.shootInterval * (float)this.fireBallCounter;
    }

    public void spawnFireBall(float angle)
    {
        if (this.isServer())
        {
            PoisonSwampProj projectile = new PoisonSwampProj(this.x, this.y, angle, 40.0F, 400, baseDamage, this);
            projectile.setLevel(this.getLevel());
            projectile.moveDist(60.0F);
            this.getLevel().entityManager.projectiles.add(projectile);
        }
    }

    public boolean isPouncing()
    {
        return this.pounceStartTime > 0L;
    }

    protected void calcAcceleration(float speed, float friction, float moveX, float moveY, float delta)
    {
        if (this.isPouncing())
        {
            if (this.isServer())
            {
                if (this.pounceTarget != null && this.isSamePlace(this.pounceTarget))
                {
                    long timeSinceStartPounce = this.getWorldEntity().getLocalTime() - this.pounceStartTime;
                    if (timeSinceStartPounce >= (long)this.pounceChargeTime)
                    {
                        if (!this.getLevel().collides(new MovedRectangle(this, this.pounceTarget.getX(), this.pounceTarget.getY()), this.modifyChasingCollisionFilter(this.getLevelCollisionFilter(), this.pounceTarget))) {
                            this.ai.blackboard.put("currentTarget", this.pounceTarget);
                            this.ai.blackboard.put("chaserTarget", this.pounceTarget);
                            this.pounceAbility.runAndSend(this.pounceTarget.getX(), this.pounceTarget.getY());
                        }
                        else
                        {
                            this.startPounceAbility.runAndSend(-1);
                        }
                    }
                }
                else
                {
                    this.startPounceAbility.runAndSend(-1);
                }
            }
            super.calcAcceleration(speed, friction, 0.0F, 0.0F, delta);
        }
        else
        {
            super.calcAcceleration(speed, friction, moveX, moveY, delta);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), poisonSwampSlime, i, 4, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public Point getPathMoveOffset()
    {
        return new Point(32, 32);
    }

    public LootTable getLootTable()
    {
        return lootTable;
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 26 - 32;
        boolean inLiquid = this.inLiquid(x, y);
        int spriteX;
        if (inLiquid)
        {
            spriteX = GameUtils.getAnim(this.getWorldEntity().getTime(), 2, 1000);
        }
        else
        {
            spriteX = this.getJumpAnimationFrame(6);
            if (spriteX == 0 && this.isPouncing())
            {
                if (this.pounceShake == null || this.pounceShake.isOver(this.getWorldEntity().getLocalTime()))
                {
                    this.pounceShake = new CameraShake(this.getWorldEntity().getLocalTime(), 1000, 50, 2.0F, 2.0F, true);
                }
                Point2D.Float shake = this.pounceShake.getCurrentShake(this.getWorldEntity().getLocalTime());
                drawX = (int)((float)drawX + shake.x);
                drawY = (int)((float)drawY + shake.y);
                spriteX = GameUtils.getAnim(this.getWorldEntity().getTime(), 2, 200);
            }
        }

        drawY += this.getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions options = poisonSwampSlime.initDraw().sprite(spriteX, inLiquid ? 1 : 0, 64).addMaskShader(swimMask).startGlowOptions(this, (long)this.getID()).light(light).applyEnemyTracker(this, perspective).pos(drawX, drawY);
        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager)
            {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.giantSwampSlime_shadow.initDraw().sprite(spriteX, 0, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> shadow.draw());
    }

    public Stream<ModifierValue<?>> getDefaultModifiers()
    {
        return Stream.of((new ModifierValue(BuffModifiers.POISON_DAMAGE, 0.0F)).max(0.0F));
    }

    public boolean isSlimeImmune() {
        return true;
    }

    public static class GiantSwampSlimeAI extends SelectorAINode<PoisonSwampSlimeMob>
    {
        public GiantSwampSlimeAI(int searchDistance, GameDamage damage, int knockback, CooldownAttackTargetAINode.CooldownTimer cooldownTimer, int attackCooldown, int attackDistance, int wanderFrequency) {
            this.addChild(new EscapeAINode<PoisonSwampSlimeMob>()
            {
                public boolean shouldEscape(PoisonSwampSlimeMob mob, Blackboard<PoisonSwampSlimeMob> blackboard)
                {
                    return mob.isHostile && !mob.isSummoned && mob.shouldLevelRetreat();
                }
            });
            CollisionPlayerChaserAI<PoisonSwampSlimeMob> chaser = new CollisionPlayerChaserAI<>(searchDistance, damage, knockback);
            CooldownAttackTargetAINode<PoisonSwampSlimeMob> cooldownAttackNode = new CooldownAttackTargetAINode<PoisonSwampSlimeMob>(cooldownTimer, attackCooldown, attackDistance) {
                public boolean canAttackTarget(PoisonSwampSlimeMob mob, Mob target)
                {
                    if (!mob.inLiquid() && !mob.isPouncing())
                    {
                        if (mob.getDistance(target) < 64.0F)
                        {
                            return false;
                        }
                        else
                        {
                            return !mob.getLevel().collides(new MovedRectangle(mob, target.getX(), target.getY()), mob.modifyChasingCollisionFilter(mob.getLevelCollisionFilter(), target));
                        }
                    }
                    else
                    {
                        return false;
                    }
                }

                public boolean attackTarget(PoisonSwampSlimeMob mob, Mob target)
                {
                    mob.pounceTarget = target;
                    mob.startPounceAbility.runAndSend(1000);
                    return true;
                }
            };
            cooldownAttackNode.randomizeAttackTimer();
            chaser.addChild(cooldownAttackNode);
            this.addChild(chaser);
            this.addChild(new WandererAINode<>(wanderFrequency));
        }
    }
}