package summonerexpansion.mobs.summonminions;

import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.gfx.gameTexture.GameTexture;
import summonerexpansion.mobs.summonminions.baseminions.MagicLampBase;

import java.awt.*;

public class LampMinionTungsten extends MagicLampBase
{
    public static GameTexture texture;

    public LampMinionTungsten()
    {
        super();
        setSpeed(80.0F);
        setFriction(1.0F);
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();
        if (owner != null && target != null)
        {
            ActiveBuff buff = new ActiveBuff(BuffRegistry.getBuff("lamptungstendebuff"), target, 600, this);
            target.addBuff(buff, true);
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
        }
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI(400, (GameDamage)null, 15, 500, 640, 64), new FlyingAIMover());
    }

    public void clientTick()
    {
        super.clientTick();
        if (getAttackOwner().buffManager.hasBuff("copperminersetbonus"))
        {
            getLevel().lightManager.refreshParticleLightFloat(x, y, 52.0F, 0.5F, 250);
        }
        else
        {
            getLevel().lightManager.refreshParticleLightFloat(x, y, 52.0F, 0.5F, 175);
        }
    }
}