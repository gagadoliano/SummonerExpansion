package summonerexpansion.mobs.summonminions;

import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTexture.GameTexture;
import summonerexpansion.mobs.summonminions.baseminions.MagicLampBase;

import java.awt.*;

public class LampMinionDungeon extends MagicLampBase
{
    public static GameTexture texture;
    public boolean nightDebuff;

    public LampMinionDungeon()
    {
        super();
        setSpeed(70.0F);
        setFriction(1.0F);
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();
        if (owner != null && target != null)
        {
            if (nightDebuff)
            {
                ActiveBuff buff = new ActiveBuff(BuffRegistry.getBuff("lampdungeondebuff"), target, 600, this);
                target.addBuff(buff, true);
            }
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
        }
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI(300, (GameDamage)null, 15, 500, 800, 64), new FlyingAIMover());
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

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, i, 2, 32, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }
}