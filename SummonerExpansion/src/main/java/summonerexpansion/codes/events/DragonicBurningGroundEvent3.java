package summonerexpansion.codes.events;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.GroundEffectEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobHitCooldowns;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.level.maps.LevelObjectHit;
import summonerexpansion.codes.particles.DragonicBurningGroundParticle3;

import java.awt.*;

import static summonerexpansion.codes.registries.RegistryDebuffs.WeaponDebuffs.DRAGON_POISONDOT;

public class DragonicBurningGroundEvent3 extends GroundEffectEvent
{
    private GameDamage damage = new GameDamage(0.0F);
    protected MobHitCooldowns hitCooldowns = new MobHitCooldowns(1000);
    protected int tickCounter;
    protected int hitCounter;
    private DragonicBurningGroundParticle3 particle;
    protected int hitCooldownMasterUniqueID;
    protected float upgradeTier;

    public DragonicBurningGroundEvent3() {
    }

    public DragonicBurningGroundEvent3(Mob owner, int x, int y, GameRandom uniqueIDRandom, GameDamage damage, int hitCooldownMasterUniqueID, float upgradeTier)
    {
        super(owner, x, y, uniqueIDRandom);
        this.damage = damage;
        this.hitCooldownMasterUniqueID = hitCooldownMasterUniqueID;
        this.upgradeTier = upgradeTier;
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        this.damage.writePacket(writer);
        writer.putNextInt(this.hitCooldownMasterUniqueID);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        GameDamage.fromReader(reader);
        this.hitCooldownMasterUniqueID = reader.getNextInt();
    }

    public void init()
    {
        super.init();
        this.tickCounter = 0;
        if (this.isClient())
        {
            this.level.entityManager.addParticle(this.particle = new DragonicBurningGroundParticle3(this.level, (float)this.x, (float)this.y, 2000L), true, Particle.GType.CRITICAL);
            SoundManager.playSound(GameResources.campfireSizzle, SoundEffect.effect((float)this.x, (float)this.y).volume(0.5F).pitch(GameRandom.globalRandom.getFloatBetween(0.5F, 1.0F)));
        }

        if (this.hitCooldownMasterUniqueID != 0)
        {
            LevelEvent event = this.level.entityManager.events.get(this.hitCooldownMasterUniqueID, true);
            if (event instanceof DragonicBurningGroundEvent2)
            {
                this.hitCooldowns = ((DragonicBurningGroundEvent2)event).hitCooldowns;
            }
        }
    }

    public Shape getHitBox()
    {
        int width = 24;
        int height = 24;
        return new Rectangle(this.x - width / 2, this.y - height / 2, width, height);
    }

    public void clientHit(Mob target)
    {
        target.startHitCooldown();
        this.hitCooldowns.startCooldown(target);
        ++this.hitCounter;
        if (this.hitCounter >= 3)
        {
            this.over();
        }
    }

    public void serverHit(Mob target, boolean clientSubmitted)
    {
        if (clientSubmitted || this.hitCooldowns.canHit(target))
        {
            ActiveBuff buff = new ActiveBuff(DRAGON_POISONDOT, target, 10.0F, this.owner);
            buff.setUpgradeTier(this.upgradeTier);
            target.addBuff(buff, true);
            target.isServerHit(this.damage, 0.0F, 0.0F, 0.0F, this.owner);
            ++this.hitCounter;
            if (this.hitCounter >= 3) {
                this.over();
            }
            this.hitCooldowns.startCooldown(target);
        }
    }

    public void hitObject(LevelObjectHit hit) {
        hit.getLevelObject().attackThrough(this.damage, this.owner);
    }

    public boolean canHit(Mob mob) {
        return super.canHit(mob) && this.hitCooldowns.canHit(mob);
    }

    public void clientTick()
    {
        ++this.tickCounter;
        if ((long)this.tickCounter > 40L)
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
        if ((long)this.tickCounter > 40L)
        {
            this.over();
        }
        else
        {
            super.serverTick();
        }
    }

    public void over()
    {
        super.over();
        if (this.particle != null)
        {
            this.particle.despawnNow();
        }
    }
}