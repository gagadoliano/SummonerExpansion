package summonerexpansion.mobs.summonminions.baseminions;

import necesse.engine.network.server.ServerClient;
import necesse.engine.sound.SoundSettings;
import necesse.entity.mobs.*;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.GameResources;

import java.awt.*;

public class ButterflyBase extends FlyingAttackingFollowingMob
{
    public ButterflyBase()
    {
        super(10);
        setSpeed(50.0F);
        setFriction(2.0F);
        collision = new Rectangle(-7, -5, 14, 10);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-16, -28, 32, 34);
        ambientSoundCooldownMin = 500;
        ambientSoundCooldownMax = 1250;
    }

    public void init()
    {
        super.init();
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter){return summonDamage;}

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
        }
    }

    protected SoundSettings getAmbientSound()
    {
        return (new SoundSettings(GameResources.butterflywings)).volume(0.04F).basePitch(0.97F).pitchVariance(0.05F);
    }

    public int getFlyingHeight()
    {
        return 20;
    }
}