package summonerexpansion.mobs.minions.summon;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.particle.SmokePuffParticle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonWalkBase;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMinionTextures.vampireMinion;

public class VampireMinion extends SummonWalkBase
{
    private boolean isBat;

    public VampireMinion()
    {
        super();
        setSpeed(50.0F);
        setFriction(1.0F);
        attackAnimTime = 750;
        attackCooldown = 1200;
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
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<VampireMinion>(600, 320, false, false, 800, 64)
        {
            public boolean attackTarget(VampireMinion mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack() && getAttackOwner().buffManager.hasBuff("bloodplatecowlsetbonus") && !mob.isBat)
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("bloodbolt", mob.getLevel(), mob.x, mob.y, target.x, target.y, (100.0F * projVel), 800, summonDamage.modFinalMultiplier(1.30F), mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(40.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    return true;
                }
                else if (mob.canAttack() && !mob.isBat)
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("bloodbolt", mob.getLevel(), mob.x, mob.y, target.x, target.y, (80.0F * projVel), 640, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(20.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
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

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), vampireMinion.body, i, 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, vampireMinion)).sprite(sprite).dir(dir).mask(swimMask).light(light).applyEnemyTracker(this, perspective);
        float animProgress = this.getAttackAnimProgress();
        if (this.isAttacking)
        {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(dir).armSprite(vampireMinion.body, 0, 8, 32).swingRotation(animProgress);
            humanDrawOptions.attackAnim(attackOptions, animProgress);
        }

        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager)
            {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        Point p = new Point(0, dir);
        if (!this.isBat)
        {
            if (!this.inLiquid(x, y))
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
            p.x = GameUtils.getAnim(this.getWorldEntity().getTime(), 4, 400) + 1;
        }
        return p;
    }

    public int getRockSpeed() {
        return 25;
    }
}