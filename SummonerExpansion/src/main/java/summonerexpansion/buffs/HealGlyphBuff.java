package summonerexpansion.buffs;

import necesse.engine.util.GameMath;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.level.maps.Level;
import summonerexpansion.codes.events.HealGlyphTrapEvent;
import summonerexpansion.codes.registry.SummonerBuffs;
import summonerexpansion.codes.registry.SummonerTextures;

public class HealGlyphBuff extends Buff
{
    public HealGlyphBuff()
    {
        this.isImportant = true;
        this.canCancel = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.addModifier(BuffModifiers.HEALTH_REGEN_FLAT, 1F);
        buff.addModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 1F);
        if (buff.owner.isClient())
        {
            Level level = buff.owner.getLevel();
            int count = 8;
            for (int i = 0; i < count; ++i)
            {
                float offset = (float)i * 360.0F / (float)count;
                float rotationSpeed = 0.15F;
                ParticleOption particle = level.entityManager.addParticle(buff.owner, Particle.GType.IMPORTANT_COSMETIC).lifeTime(buff.getDurationLeft()).sprite(SummonerTextures.healGlyphParticles).color(HealGlyphTrapEvent.particleColor).givesLight(HealGlyphTrapEvent.particleHue, 0.6F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                    pos.x = GameMath.cos((float)buff.owner.getTime() * rotationSpeed + offset) * 12.0F + 1.0F;
                    pos.y = GameMath.sin((float)buff.owner.getTime() * rotationSpeed + offset) * 10.0F - 5.0F;
                }).size((options, lifeTime, timeAlive, lifePercent) ->
                {
                    ActiveBuff currentBuff = buff.owner.buffManager.getBuff(SummonerBuffs.SummonBuffs.HEALGLYPH);
                    if (currentBuff != null)
                    {
                        int size = (int)(6.0F * ((float)currentBuff.getDurationLeft() / (float)currentBuff.getDuration())) + 10;
                        size += size % 2;
                        options.size(size, size);
                    }
                }).rotation((lifeTime, timeAlive, lifePercent) ->
                {
                    int direction = offset % 2.0F == 0.0F ? 0 : -1;
                    return (float)(180 * direction);
                }).onDied((pos) -> level.entityManager.addParticle(buff.owner.x + pos.x, buff.owner.y + pos.y, Particle.GType.IMPORTANT_COSMETIC).color(HealGlyphTrapEvent.particleColor).givesLight(HealGlyphTrapEvent.particleHue, 0.8F).sizeFades(8, 8).lifeTime(500).movesFriction(pos.x, -10.0F, 1.0F));
                particle.removeIf(() ->
                {
                    ActiveBuff currentBuff = buff.owner.buffManager.getBuff(SummonerBuffs.SummonBuffs.HEALGLYPH);
                    return currentBuff != buff;
                });
            }
        }
    }
}