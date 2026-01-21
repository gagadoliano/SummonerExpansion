package summonerexpansion.buffs.debuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.texture.SharedTextureDrawOptions;

import java.awt.*;

public class SharkLavaDebuff extends Buff 
{
    public SharkLavaDebuff() 
    {
        isImportant = true;
        isVisible = true;
        canCancel = false;
    }

    protected float getDamagePerSecond(ActiveBuff buff)
    {
        float bleedDamagePerSec = buff.getGndData().getFloat("damagepersec");
        return bleedDamagePerSec != 0.0F ? bleedDamagePerSec : 1.0F;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.BLEED_DAMAGE_FLAT, getDamagePerSecond(buff));
        buff.setModifier(BuffModifiers.FIRE_DAMAGE_FLAT, getDamagePerSecond(buff));
    }

    public int getStackSize(ActiveBuff buff) {
        return 10;
    }

    public void clientTick(ActiveBuff buff)
    {
        super.clientTick(buff);
        if (buff.owner.isVisible() && GameRandom.globalRandom.getEveryXthChance(4))
        {
            Mob owner = buff.owner;
            float variance = 0.4F;
            float xDir = GameRandom.globalRandom.getFloatBetween(-variance, variance);
            float yVariance = GameRandom.globalRandom.getFloatBetween(10.0F, 20.0F);
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)5.0F), owner.y + 23.0F + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.particles.sprite(0, 0, 8)).color(new Color(182, 12, 12)).dontRotate().height(32.0F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                pos.x += xDir;
                float heightFactor = (float)(-Math.pow(0.6 - 1.6 * (double)lifePercent, 2.0F)) + 1.0F;
                pos.y = owner.y + 32.0F + yVariance - heightFactor * 25.0F;
            }).fadesAlphaTimeToCustomAlpha(200, 200, 0.9F).size((options, lifeTime, timeAlive, lifePercent) -> options.size(10, 10)).lifeTime(500);

            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)6.0F), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(226, 57, 0)).givesLight(0.0F, 0.5F).height(16.0F);
        }
    }
}