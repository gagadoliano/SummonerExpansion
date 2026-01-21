package summonerexpansion.mobs.summonminions;

import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;

public class BeeMinion extends BookBeeMinion
{
    public int BeeCount;

    public BeeMinion()
    {

    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI<>(1000, summonDamage, 5, 500, 9000, 64), new FlyingAIMover());
    }

    public void serverTick()
    {
        super.serverTick();
        if (++BeeCount >= 200)
        {
            remove();
        }
    }
}
