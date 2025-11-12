package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.particle.SmokePuffParticle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.TilePosition;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.summonothers.SummonerTextures.vampireMinion;

public class VampireMinion extends AttackingFollowingMob
{
    private boolean isBat;

    public VampireMinion()
    {
        super(10);
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
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, vampireMinion)).sprite(sprite).dir(dir).mask(swimMask).light(light);
        float animProgress = getAttackAnimProgress();
        if (isAttacking) 
        {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(dir).armSprite(vampireMinion.body, 0, 8, 32).swingRotation(animProgress).light(light);
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

    public int getRockSpeed() {
        return 25;
    }
}