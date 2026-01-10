package summonerexpansion.codes.events;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.WaitForSecondsEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.MobAbilityLevelEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.particle.Particle;
import summonerexpansion.allprojs.armorprojs.TitaniumLightningProj;
import summonerexpansion.particles.TitaniumLightningGlyphParticle;

import java.awt.*;

public class TitaniumLightningLevelEvent extends MobAbilityLevelEvent
{
    public Point targetPos;
    public GameDamage damage;
    public float timeUntilBeamLandsInSeconds;

    public TitaniumLightningLevelEvent() {
    }

    public TitaniumLightningLevelEvent(Mob owner, GameRandom uniqueIDRandom, Point targetPos, GameDamage damage, float timeUntilBeamLandsInSeconds)
    {
        super(owner, uniqueIDRandom);
        this.targetPos = targetPos;
        this.damage = damage;
        this.timeUntilBeamLandsInSeconds = timeUntilBeamLandsInSeconds;
    }

    public boolean isNetworkImportant() {
        return true;
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextInt(this.targetPos.x);
        writer.putNextInt(this.targetPos.y);
        writer.putNextFloat(this.timeUntilBeamLandsInSeconds);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        this.targetPos = new Point(reader.getNextInt(), reader.getNextInt());
        this.timeUntilBeamLandsInSeconds = reader.getNextFloat();
    }

    public void init()
    {
        super.init();
        this.chargeUpSpiritBeamGlyph(this.targetPos.x, this.targetPos.y);
        this.over();
    }

    public void chargeUpSpiritBeamGlyph(final int xPos, final int yPos)
    {
        if (this.isClient())
        {
            long lifeTime = (long)(this.timeUntilBeamLandsInSeconds * 4000.0F);
            Particle particle = new TitaniumLightningGlyphParticle(this.level, (float)xPos, (float)yPos, lifeTime);
            this.level.entityManager.addParticle(particle, Particle.GType.CRITICAL);
        }

        this.level.entityManager.events.addHidden(new WaitForSecondsEvent(this.timeUntilBeamLandsInSeconds)
        {
            public void onWaitOver()
            {
                TitaniumLightningLevelEvent.this.fireLightProj(xPos, yPos);
            }
        });
    }

    public void fireLightProj(int xPos, int yPos)
    {
        if (this.owner != null)
        {
            TitaniumLightningProj lightingProjectile = new TitaniumLightningProj(this.level, this.owner, (float)xPos, (float)(yPos - 800), (float)xPos, (float)yPos, this.damage, 150.0F / this.timeUntilBeamLandsInSeconds, 800);
            lightingProjectile.resetUniqueID((new GameRandom(this.getUniqueID())).nextSeeded(67));
            this.getLevel().entityManager.projectiles.addHidden(lightingProjectile);
        }

    }
}
