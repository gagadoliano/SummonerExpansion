package summonerexpansion.mobs.minions.base;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.particle.Particle;

import java.awt.*;
import java.awt.geom.Point2D;

public class SummonFishBase extends SummonFlyingBase
{
    public int lifeTime;
    public Color particleColor;
    public float moveAngle;
    private float toMove;

    public SummonFishBase()
    {
        super();
    }

    public SummonFishBase(int lifeTime, Color particleColor)
    {
        super();
        setSpeed(40.0F);
        setFriction(1.0F);
        moveAccuracy = 10;
        collision = new Rectangle(0, 0, 28, 28);
        hitBox = new Rectangle(0, 0, 28, 28);
        selectBox = new Rectangle(0, 0, 28, 28);
        this.lifeTime = lifeTime;
        this.particleColor = particleColor;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(600, summonDamage, 50, 800, 9000, 64));
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
        }
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime -= 1;
        if (lifeTime <= 0)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 10; ++i)
        {
            getLevel().entityManager.addParticle(x, y, Particle.GType.COSMETIC).movesConstantAngle((float) GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(particleColor);
        }
    }
}