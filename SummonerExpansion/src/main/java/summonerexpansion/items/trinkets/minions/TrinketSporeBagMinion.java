package summonerexpansion.items.trinkets.minions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.AINodeResult;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerValidTargetCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.networkField.BooleanNetworkField;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.entity.trails.Trail;
import necesse.entity.trails.TrailVector;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMinionTextures.sporebagMinion;

public class TrinketSporeBagMinion extends FlyingAttackingFollowingMob 
{
    public Trail trail;
    public float moveAngle;
    private float toMove;
    public BooleanNetworkField hasTarget;
    public float baseDamage = 50.0F;

    public TrinketSporeBagMinion() 
    {
        super(10);
        moveAccuracy = 15;
        setFriction(2.0F);
        collision = new Rectangle(-8, -8, 16, 16);
        hitBox = new Rectangle(-8, -8, 16, 16);
        selectBox = new Rectangle();
        hasTarget = registerNetworkField(new BooleanNetworkField(false));
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) {
        return summonDamage;
    }

    public int getCollisionKnockback(Mob target) {
        return 15;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback) 
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null) 
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerValidTargetCollisionChaserAI<TrinketSporeBagMinion>(200, null, 45, 500, 900, 64)
        {
            public boolean isValidTarget(TrinketSporeBagMinion mob, ItemAttackerMob owner, Mob target)
            {
                if (owner == null)
                {
                    return false;
                }
                else
                {
                    Object result = GameUtils.castRayFirstHit(new Line2D.Float(owner.x, owner.y, target.x, target.y), 100.0F, (line) ->
                    {
                        CollisionFilter collisionFilter = mob.modifyChasingCollisionFilter((new CollisionFilter()).projectileCollision(), target);
                        return mob.getLevel().collides(line, collisionFilter) ? new Object() : null;
                    });
                    return result == null;
                }
            }
            public AINodeResult tick(TrinketSporeBagMinion mob, Blackboard<TrinketSporeBagMinion> blackboard)
            {
                AINodeResult out = super.tick(mob, blackboard);
                Mob chaserTarget = blackboard.getObject(Mob.class, "chaserTarget");
                TrinketSporeBagMinion.this.hasTarget.set(chaserTarget != null);
                return out;
            }
        }, new FlyingAIMover());
        if (isClient())
        {
            trail = new Trail(this, getLevel(), new Color(135, 14, 129, 255), 14.0F, 1000, 0.0F);
            trail.drawOnTop = true;
            trail.removeOnFadeOut = false;
            getLevel().entityManager.addTrail(trail);
        }
    }

    public void tickMovement(float delta)
    {
        if (getAttackOwner() != null)
        {
            float additionalSpeedBasedOnMovementSpeed = (getAttackOwner().buffManager.getModifier(BuffModifiers.SPEED) - 1.0F) * 100.0F / 2.0F;
            if (hasTarget.get())
            {
                setSpeed(250.0F + additionalSpeedBasedOnMovementSpeed);
                moveAngle -= delta;
            }
            else
            {
                setSpeed(120.0F + additionalSpeedBasedOnMovementSpeed);
                moveAngle -= 0.1F * delta;
            }
            toMove += delta;
            while(toMove > 4.0F)
            {
                float oldX = x;
                float oldY = y;
                super.tickMovement(4.0F);
                toMove -= 4.0F;
                Point2D.Float d = GameMath.normalize(oldX - x, oldY - y);
                if (trail != null)
                {
                    float trailOffset = 0.0F;
                    trail.addPoint(new TrailVector(x + d.x * trailOffset, y + d.y * trailOffset, -d.x, -d.y, trail.thickness, 0.0F));
                }
            }
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 20; ++i)
        {
            getLevel().entityManager.addParticle(x, y, Particle.GType.COSMETIC).movesConstantAngle((float) GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(135, 14, 129));
        }
    }

    protected SoundSettings getHitDeathSound()
    {
        return (new SoundSettings(GameResources.spit)).volume(0.5F);
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 16;
        DrawOptions body = sporebagMinion.initDraw().startGlowOptions(level, getID()).light(light).applyEnemyTracker(this, perspective).rotate(moveAngle, 16, 16).pos(drawX, drawY);
        topList.add((tm) -> body.draw());
    }

    public void dispose()
    {
        super.dispose();
        if (trail != null)
        {
            trail.removeOnFadeOut = true;
        }
    }
}