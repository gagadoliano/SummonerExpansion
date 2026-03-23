package summonerexpansion.mobs.minions.summon;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.ActiveBuff;
import summonerexpansion.mobs.minions.base.MagicLampBase;

import static summonerexpansion.codes.registries.RegistryDebuffs.WeaponDebuffs.LAMPDUNGEONFIRE;
import static summonerexpansion.codes.registries.RegistryMinionTextures.lampMinionDungeon;

public class LampMinionDungeon extends MagicLampBase
{
    public boolean nightDebuff;

    public LampMinionDungeon()
    {
        super(lampMinionDungeon);
        setSpeed(70.0F);
        setFriction(1.0F);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI<>(300, null, 15, 500, 800, 64), new FlyingAIMover());
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();
        if (owner != null && target != null)
        {
            if (nightDebuff)
            {
                ActiveBuff buff = new ActiveBuff(LAMPDUNGEONFIRE, target, 600, this);
                target.addBuff(buff, true);
            }
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
        }
    }

    public void clientTick()
    {
        super.clientTick();
        if (getAttackOwner().buffManager.hasBuff("copperminersetbonus"))
        {
            getLevel().lightManager.refreshParticleLightFloat(x, y, 270.0F, 0.6F, 220);
        }
        else
        {
            getLevel().lightManager.refreshParticleLightFloat(x, y, 270.0F, 0.6F, 160);
        }
    }

    public void serverTick()
    {
        super.serverTick();
        nightDebuff = getAttackOwner().getLevel().getWorldEntity().isNight();
    }
}