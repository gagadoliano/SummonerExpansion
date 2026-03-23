package summonerexpansion.mobs.minions.base;

import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;

public class SummonWalkBase extends AttackingFollowingMob
{
    public SummonWalkBase()
    {
        super(10);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(100, null, 10, 500, 900, 64));
    }
}