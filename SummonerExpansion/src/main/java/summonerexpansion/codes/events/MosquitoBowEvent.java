package summonerexpansion.codes.events;

import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.GroundEffectEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobHitCooldowns;
import necesse.entity.particle.Particle;
import necesse.entity.trails.Trail;
import necesse.level.maps.LevelObjectHit;
import summonerexpansion.particles.MosquitoBowParticle;

import java.awt.*;

public class MosquitoBowEvent extends GroundEffectEvent
{
    protected int tickCounter;
    protected int hitCounter;
    protected MobHitCooldowns hitCooldowns;
    protected GameDamage damage;
    protected float resilienceGain;
    protected Trail trail;
    private MosquitoBowParticle particle;

    public MosquitoBowEvent() {
    }

    public MosquitoBowEvent(Mob owner, int x, int y, GameRandom uniqueIDRandom, GameDamage damage, float resilienceGain) 
    {
        super(owner, x, y, uniqueIDRandom);
        this.damage = damage;
        this.resilienceGain = resilienceGain;
    }

    public void init() 
    {
        super.init();
        tickCounter = 0;
        hitCooldowns = new MobHitCooldowns();
        if (isClient()) 
        {
            level.entityManager.addParticle(particle = new MosquitoBowParticle(level, (float)x, (float)y, 2000L), Particle.GType.CRITICAL);
        }
    }

    public Shape getHitBox() 
    {
        int width = 95;
        int height = 80;
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public void clientHit(Mob target) 
    {
        target.startHitCooldown();
        hitCooldowns.startCooldown(target);
        ++hitCounter;
        if (hitCounter >= 9) 
        {
            over();
        }
    }

    public void serverHit(Mob target, boolean clientSubmitted) 
    {
        if (clientSubmitted || hitCooldowns.canHit(target)) 
        {
            target.isServerHit(damage.modFinalMultiplier(0.25F), 0.0F, 0.0F, 0.0F, owner);
            if (target.canGiveResilience(owner) && resilienceGain != 0.0F) 
            {
                owner.addResilience(resilienceGain);
                resilienceGain = 0.0F;
            }
            hitCooldowns.startCooldown(target);
            ++hitCounter;
            if (hitCounter >= 9) 
            {
                over();
            }
        }
    }

    public void hitObject(LevelObjectHit hit) {
        hit.getLevelObject().attackThrough(damage, owner);
    }

    public boolean canHit(Mob mob) {
        return super.canHit(mob) && hitCooldowns.canHit(mob);
    }

    public void clientTick() 
    {
        ++tickCounter;
        if (tickCounter > 40) 
        {
            over();
        } 
        else 
        {
            super.clientTick();
        }
    }

    public void serverTick() 
    {
        ++tickCounter;
        if (tickCounter > 40) 
        {
            over();
        } 
        else 
        {
            super.serverTick();
        }
    }

    public void over() 
    {
        super.over();
        if (particle != null) 
        {
            particle.despawnNow();
        }
    }
}