package summonerexpansion.codes.events;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.GroundEffectEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobHitCooldowns;
import necesse.entity.particle.Particle;
import necesse.level.maps.LevelObjectHit;
import summonerexpansion.particles.SpiritGhoulParticle;

import java.awt.*;

public class SpiritGhoulPoolEvent extends GroundEffectEvent
{
    public GameDamage damage;
    public float lingerTimeSeconds;
    protected int tickCounter;
    protected MobHitCooldowns hitCooldowns;
    public static Rectangle hitBox = new Rectangle(-15, -10, 30, 20);

    public SpiritGhoulPoolEvent() {
    }

    public SpiritGhoulPoolEvent(Mob owner, int x, int y, GameRandom uniqueIDRandom, GameDamage damage, float lingerTimeSeconds)
    {
        super(owner, x, y, uniqueIDRandom);
        this.damage = damage;
        this.lingerTimeSeconds = lingerTimeSeconds;
    }

    public boolean isNetworkImportant() {
        return true;
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        this.damage.writePacket(writer);
        writer.putNextFloat(this.lingerTimeSeconds);
        writer.putNextInt(this.tickCounter);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        this.damage = GameDamage.fromReader(reader);
        this.lingerTimeSeconds = reader.getNextFloat();
        this.tickCounter = reader.getNextInt();
    }

    public void init()
    {
        super.init();
        this.hitCooldowns = new MobHitCooldowns();
        if (this.isClient())
        {
            long msLeft = (long)(this.lingerTimeSeconds * 1000.0F) - (this.tickCounter * 50L);
            this.level.entityManager.addParticle(new SpiritGhoulParticle(this.level, (float)this.x, (float)this.y, msLeft), true, Particle.GType.CRITICAL);
        }
    }

    public Shape getHitBox()
    {
        return new Rectangle(this.x + hitBox.x, this.y + hitBox.y, hitBox.width, hitBox.height);
    }

    public void clientHit(Mob target)
    {
        target.startHitCooldown();
        this.hitCooldowns.startCooldown(target);
    }

    public void serverHit(Mob target, boolean clientSubmitted)
    {
        if (clientSubmitted || this.hitCooldowns.canHit(target))
        {
            target.isServerHit(this.damage, 0.0F, 0.0F, 0.0F, this.owner);
            this.hitCooldowns.startCooldown(target);
        }
    }

    public void hitObject(LevelObjectHit hit) {
        hit.getLevelObject().attackThrough(this.damage, this.owner);
    }

    public boolean canHit(Mob mob) {
        return super.canHit(mob) && mob.isHostile && this.hitCooldowns.canHit(mob);
    }

    public void clientTick()
    {
        ++this.tickCounter;
        if ((float)this.tickCounter > 20.0F * this.lingerTimeSeconds)
        {
            this.over();
        }
        else
        {
            super.clientTick();
        }
    }

    public void serverTick()
    {
        ++this.tickCounter;
        if ((float)this.tickCounter > 20.0F * this.lingerTimeSeconds)
        {
            this.over();
        }
        else
        {
            super.serverTick();
        }
    }
}