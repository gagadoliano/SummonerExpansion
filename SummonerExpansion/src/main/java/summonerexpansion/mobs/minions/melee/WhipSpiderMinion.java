package summonerexpansion.mobs.minions.melee;

import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.BabySpiderMob;

public class WhipSpiderMinion extends BabySpiderMob
{
    public int HitCount;

    public  WhipSpiderMinion()
    {
        setSpeed(30.0F);
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(300, summonDamage, 0, 800, 900, 100));
    }

    public void serverTick()
    {
        super.serverTick();
        HitCount++;
        if (HitCount >= 300)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }
}