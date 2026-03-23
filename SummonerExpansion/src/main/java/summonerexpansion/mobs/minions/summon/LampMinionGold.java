package summonerexpansion.mobs.minions.summon;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.ActiveBuff;
import summonerexpansion.mobs.minions.base.MagicLampBase;

import static summonerexpansion.codes.registries.RegistryDebuffs.WeaponDebuffs.LAMPGOLDFIRE;
import static summonerexpansion.codes.registries.RegistryMinionTextures.lampMinionGold;

public class LampMinionGold extends MagicLampBase
{
    public LampMinionGold()
    {
        super(lampMinionGold);
        setSpeed(65.0F);
        setFriction(1.0F);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI<>(300, null, 15, 500, 640, 70), new FlyingAIMover());
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);

            ActiveBuff buff = new ActiveBuff(LAMPGOLDFIRE, target, 600, this);
            target.addBuff(buff, true);
        }
    }

    public void clientTick()
    {
        super.clientTick();
        if (getAttackOwner().buffManager.hasBuff("copperminersetbonus"))
        {
            getLevel().lightManager.refreshParticleLightFloat(x, y, 52.0F, 0.5F, 220);
        }
        else
        {
            getLevel().lightManager.refreshParticleLightFloat(x, y, 52.0F, 0.5F, 165);
        }
    }
}