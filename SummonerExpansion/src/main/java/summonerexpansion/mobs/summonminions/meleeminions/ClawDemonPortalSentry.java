package summonerexpansion.mobs.summonminions.meleeminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.SmokePuffParticle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class ClawDemonPortalSentry extends AttackingFollowingMob
{
    public float moveAngle;
    public int portalCharge;
    public int portalLife = 0;

    public ClawDemonPortalSentry()
    {
        super(10);
        setSpeed(0.0F);
        setFriction(0F);
        attackCooldown = 2000;
        collision = new Rectangle(-10, -12, 20, 20);
        hitBox = new Rectangle(-15, -17, 30, 30);
        selectBox = new Rectangle(-18, -58, 36, 58);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<ClawDemonPortalSentry>(900, 900, false, false, 9000, 64)
        {
            public boolean attackTarget(ClawDemonPortalSentry mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("clawdemonproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, (120.0F * projVel), 900, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(40.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    portalCharge++;
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

        if (++portalLife >= 900)
        {
            remove(0.0F, 0.0F, null, true);
        }

        if (portalCharge >= 6)
        {
            AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("clawdemonportalminion", getFollowingItemAttacker().getLevel());
            getFollowingItemAttacker().serverFollowersManager.addFollower("clawdemonportalminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, (p) -> 100, null, false);
            mob.updateDamage(summonDamage);
            getLevel().entityManager.addMob(mob, x, y);
            remove(0.0F, 0.0F, null, true);
        }
    }

    public boolean canBePushed(Mob other) {return false;}

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        getLevel().entityManager.addParticle(new SmokePuffParticle(this.getLevel(), (float)this.getX(), (float)this.getY(), new Color(50, 50, 50)), Particle.GType.CRITICAL);
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 62;
        int offset = (int)(this.getWorldEntity().getTime() % 1600L) / 200;
        if (offset > 4)
        {
            offset = 4 - offset % 4;
        }
        final DrawOptions options = MobRegistry.Textures.evilsProtector2.initDraw().sprite(2, 0, 64).light(light).pos(drawX, drawY + offset);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }
}