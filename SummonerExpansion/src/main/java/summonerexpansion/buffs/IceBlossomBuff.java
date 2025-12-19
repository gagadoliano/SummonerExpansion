package summonerexpansion.buffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;

public class IceBlossomBuff extends Buff
{
    public IceBlossomBuff()
    {
        canCancel = false;
        shouldSave = false;
        isImportant = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.ARMOR_FLAT, 5);
    }

    public void clientTick(ActiveBuff buff)
    {
        if (buff.owner.isVisible())
        {
            Mob owner = buff.owner;
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 2.0F, owner.dy / 2.0F).color(new Color(36, 174, 214)).givesLight(40F, 0.3F).height(16.0F);
        }
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }
}