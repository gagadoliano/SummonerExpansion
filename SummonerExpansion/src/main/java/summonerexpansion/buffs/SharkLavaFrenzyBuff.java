package summonerexpansion.buffs;

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

public class SharkLavaFrenzyBuff extends Buff
{
    public SharkLavaFrenzyBuff()
    {
        isImportant = true;
        isVisible = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        float modifier = 0.02F;
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, modifier);
        buff.setModifier(BuffModifiers.SPEED, modifier);
    }

    public int getStackSize(ActiveBuff buff) {
        return 10;
    }

    public void clientTick(ActiveBuff buff)
    {
        super.clientTick(buff);
        if (buff.owner.isVisible() && GameRandom.globalRandom.getChance(0.4))
        {
            Mob owner = buff.owner;
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)6.0F), owner.y + 23.0F + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.particles.sprite(0, 0, 8)).color(new Color(182, 12, 12)).dontRotate().height(32.0F).movesConstant(0.0F, -3.0F).fadesAlphaTimeToCustomAlpha(200, 200, 0.9F).size(new ParticleOption.DrawModifier() {
                public void modify(SharedTextureDrawOptions.Wrapper options, int lifeTime, int timeAlive, float lifePercent)
                {
                    options.size(10, 10);
                }
            }).lifeTime(500);
        }
    }
}