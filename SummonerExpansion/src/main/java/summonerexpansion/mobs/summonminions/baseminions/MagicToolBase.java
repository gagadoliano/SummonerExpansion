package summonerexpansion.mobs.summonminions.baseminions;

import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerValidTargetCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.entity.trails.Trail;
import necesse.entity.trails.TrailVector;
import necesse.level.maps.CollisionFilter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class MagicToolBase extends FlyingAttackingFollowingMob
{
    public Trail trail;
    public float moveAngle;
    private float toMove;
    public Color color;
    public int toolHit;
    public int toolLimit;
    public int searchRange;

    public MagicToolBase()
    {
        super(10);
        setSpeed(40.0F);
        setFriction(1.0F);
        moveAccuracy = 10;
        collision = new Rectangle(6, 6, 18, 16);
        hitBox = new Rectangle(6, 6, 18, 16);
        selectBox = new Rectangle();
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI(this, new PlayerFlyingFollowerValidTargetCollisionChaserAI<MagicToolBase>(searchRange, null, 15, 500, 900, 64)
        {
            public boolean isValidTarget(MagicToolBase mob, ItemAttackerMob owner, Mob target)
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
        }, new FlyingAIMover());
        if (isClient())
        {
            trail = new Trail(this, getLevel(), color, 16.0F, 200, 0.0F);
            trail.drawOnTop = true;
            trail.removeOnFadeOut = false;
            getLevel().entityManager.addTrail(trail);
        }
    }

    public void tickMovement(float delta)
    {
        toMove += delta;
        while(toMove > 4.0F)
        {
            float oldX = x;
            float oldY = y;
            super.tickMovement(4.0F);
            toMove -= 4.0F;
            Point2D.Float d = GameMath.normalize(oldX - x, oldY - y);
            moveAngle = (float)Math.toDegrees(Math.atan2(d.y, d.x)) - 90.0F;
            if (trail != null)
            {
                float trailOffset = 5.0F;
                trail.addPoint(new TrailVector(x + d.x * trailOffset, y + d.y * trailOffset, -d.x, -d.y, trail.thickness, 0.0F));
            }
        }
    }

    public void clientTick()
    {
        super.clientTick();
        getLevel().entityManager.addParticle(x + (float)(GameRandom.globalRandom.nextGaussian() * (double)4.0F), y + (float)(GameRandom.globalRandom.nextGaussian() * (double)4.0F), Particle.GType.IMPORTANT_COSMETIC).movesConstant(dx / 10.0F, dy / 10.0F).color(color);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 30; ++i)
        {
            getLevel().entityManager.addParticle(x, y, Particle.GType.COSMETIC).movesConstantAngle((float)GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(color);
        }
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