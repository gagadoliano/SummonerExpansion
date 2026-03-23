package summonerexpansion.mobs.minions.summon;

import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.gfx.gameTexture.GameTexture;
import summonerexpansion.mobs.minions.base.MagicLampBase;

import static summonerexpansion.codes.registries.RegistryMinionTextures.lampMinionCopper;

public class LampMinionCopper extends MagicLampBase
{
    public LampMinionCopper()
    {
        super(lampMinionCopper);
        setSpeed(60.0F);
        setFriction(1.0F);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI<>(200, null, 15, 500, 640, 64), new FlyingAIMover());
    }

    public void clientTick()
    {
        super.clientTick();
        if (getAttackOwner().buffManager.hasBuff("copperminersetbonus"))
        {
            getLevel().lightManager.refreshParticleLightFloat(x, y, 52.0F, 0.5F, 200);
        }
        else
        {
            getLevel().lightManager.refreshParticleLightFloat(x, y, 52.0F, 0.5F, 150);
        }
    }
}