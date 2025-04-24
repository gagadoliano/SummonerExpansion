package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.function.BiConsumer;

public class CoffinSentry extends AttackingFollowingMob
{
    public static GameTexture texture;
    public float moveAngle;
    public int coffinCharge;

    public CoffinSentry()
    {
        super(10);
        setSpeed(0.0F);
        setFriction(0F);
        attackCooldown = 2000;
        collision = new Rectangle(0, 0, 32, 64);
        hitBox = new Rectangle(0, 0, 32, 64);
        selectBox = new Rectangle();
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<CoffinSentry>(900, 900, false, false, 9000, 64)
        {
            public boolean attackTarget(CoffinSentry mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("bloodbolt", mob.getLevel(), mob.x, mob.y, target.x, target.y, 80.0F, 900, CoffinSentry.this.summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(20.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    coffinCharge++;
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void serverTick()
    {
        super.serverTick();
        if (coffinCharge >= 10)
        {
            FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("batcryptminion", getFollowingItemAttacker().getLevel());
            getFollowingItemAttacker().serverFollowersManager.addFollower("batcryptminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, (p) -> 100, null, false);
            mob.updateDamage(summonDamage);
            this.getLevel().entityManager.addMob(mob, x, y);
            coffinCharge = 0;
        }
    }

    public boolean canBePushed(Mob other) {return false;}

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 10; ++i)
        {
            this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.COSMETIC).movesConstantAngle((float) GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(51, 51, 51));
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = texture.initDraw().light(light).rotate(this.moveAngle, 16, 20).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}
