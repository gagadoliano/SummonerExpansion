package summonerexpansion.mobs.summonminions.setminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.WaitForSecondsEvent;
import necesse.entity.manager.EntityManager;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.CrazedRavenFeatherProjectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class SetRavenlordMinion extends AttackingFollowingMob
{
    public int throwLife = 0;

    public SetRavenlordMinion()
    {
        super(10);
        setSpeed(40.0F);
        setFriction(3.0F);
        attackCooldown = 1500;
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
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<SetRavenlordMinion>(600, 300, false, false, 800, 64)
        {
            public boolean attackTarget(SetRavenlordMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    SetRavenlordMinion.fireCrazedRavenProjectiles(mob, target);
                    throwLife++;
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    private static void fireCrazedRavenProjectiles(final SetRavenlordMinion mob, final Mob target)
    {
        final EntityManager entityManager = mob.getLevel().entityManager;
        mob.attack(target.getX(), target.getY(), false);

        for (int i = 0; i < 2; ++i)
        {
            CrazedRavenFeatherProjectile duoProjectile = new CrazedRavenFeatherProjectile(mob.getLevel(), mob.x, mob.y, target.x, target.y, 80.0F, 576, mob.summonDamage, mob, 50);
            duoProjectile.setAngle(duoProjectile.getAngle() - 30.0F + (float)(i * 60));
            entityManager.projectiles.add(duoProjectile);
        }

        entityManager.addLevelEventHidden(new WaitForSecondsEvent(0.5F)
        {
            public void onWaitOver()
            {
                mob.attack(target.getX(), target.getY(), false);
                CrazedRavenFeatherProjectile singleProjectile = new CrazedRavenFeatherProjectile(mob.getLevel(), mob.x, mob.y, target.x, target.y, 120.0F, 768, mob.summonDamage, mob, 50);
                entityManager.projectiles.add(singleProjectile);
            }
        });
        entityManager.addLevelEventHidden(new WaitForSecondsEvent(1.0F)
        {
            public void onWaitOver()
            {
                mob.attack(target.getX(), target.getY(), false);
                for(int i = 0; i < 3; ++i)
                {
                    CrazedRavenFeatherProjectile duoProjectile = new CrazedRavenFeatherProjectile(mob.getLevel(), mob.x, mob.y, target.x, target.y, 40.0F, 448, mob.summonDamage, mob, 50);
                    duoProjectile.setAngle(duoProjectile.getAngle() - 40.0F + (float)(i * 40));
                    entityManager.projectiles.add(duoProjectile);
                }
            }
        });
    }

    public void clientTick()
    {
        super.clientTick();
        if (this.isAttacking)
        {
            this.getAttackAnimProgress();
        }
    }

    public void serverTick()
    {
        super.serverTick();
        if (this.isAttacking)
        {
            this.getAttackAnimProgress();
        }

        if (this.throwLife >= 3)
        {
            this.remove(0.0F, 0.0F, null, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.crazedRaven.body, GameRandom.globalRandom.nextInt(6), 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(GameMath.getTileCoordinate(x), GameMath.getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(GameMath.getTileCoordinate(x), GameMath.getTileCoordinate(y)).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, MobRegistry.Textures.crazedRaven)).sprite(sprite).dir(dir).mask(swimMask).light(light);
        float animProgress = this.getAttackAnimProgress();
        if (this.isAttacking)
        {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(dir).armSprite(MobRegistry.Textures.crazedRaven.body, 0, 8, 32).swingRotation(animProgress).light(light);
            humanDrawOptions.attackAnim(attackOptions, animProgress);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public int getRockSpeed() {
        return 20;
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (this.isClient())
        {
            SoundManager.playSound(GameResources.magicbolt1, SoundEffect.effect(this).pitch(1.4F));
        }
    }
}