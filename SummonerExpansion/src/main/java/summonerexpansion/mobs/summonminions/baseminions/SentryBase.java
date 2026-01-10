package summonerexpansion.mobs.summonminions.baseminions;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import summonerexpansion.codes.registry.SummonerModifiers;

import java.awt.*;

public class SentryBase extends AttackingFollowingMob
{
    public float sentryAttackCooldown;
    public float sentryAttackAnimTime;

    public SentryBase(float sentryCooldown, float sentryAnimTime)
    {
        super(10);
        setSpeed(0.0F);
        setFriction(0F);
        sentryAttackCooldown = sentryCooldown;
        sentryAttackAnimTime = sentryAnimTime;
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public void serverTick()
    {
        super.serverTick();
        updateAttackSpeed();
    }

    public void clientTick()
    {
        super.clientTick();
        updateAttackSpeed();
    }

    public void updateAttackSpeed()
    {
        attackCooldown = (int)(sentryAttackCooldown * (1.0F / getAttackOwner().buffManager.getModifier(SummonerModifiers.SENTRY_ATTACK_SPEED)));
        attackAnimTime = (int)(sentryAttackAnimTime * (1.0F / getAttackOwner().buffManager.getModifier(SummonerModifiers.SENTRY_ATTACK_SPEED)));
    }
}