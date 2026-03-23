package summonerexpansion.mobs.minions.base;

import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;

import java.awt.*;

public class SummonFlyingBase extends FlyingAttackingFollowingMob
{
    public SummonFlyingBase()
    {
        super(10);
        moveAccuracy = 15;
        setSpeed(1.0F);
        setFriction(1.0F);
        collision = new Rectangle();
        hitBox = new Rectangle();
        selectBox = new Rectangle();
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(400, summonDamage, 10, 600, 9000, 80));
    }
}