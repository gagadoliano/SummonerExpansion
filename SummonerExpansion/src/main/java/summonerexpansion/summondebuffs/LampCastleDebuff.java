package summonerexpansion.summondebuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;

public class LampCastleDebuff extends Buff
{
    public boolean nightDebuff;

    public LampCastleDebuff()
    {
        canCancel = false;
        isImportant = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        if (nightDebuff)
        {
            activeBuff.setModifier(BuffModifiers.POISON_DAMAGE_FLAT , 16.0F);
            activeBuff.setModifier(BuffModifiers.SLOW , 0.30F);
        }
        else
        {
            activeBuff.setModifier(BuffModifiers.POISON_DAMAGE_FLAT , 8.0F);
            activeBuff.setModifier(BuffModifiers.SLOW , 0.10F);
        }
    }

    public void clientTick(ActiveBuff buff)
    {
        if (buff.owner.isVisible())
        {
            Mob owner = buff.owner;
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(166, 204, 52)).givesLight(0.0F, 0.5F).height(16.0F);
        }
    }

    @Override
    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        if (buff.owner.getLevel().getWorldEntity().isNight())
        {
            nightDebuff = true;
        }
        else
        {
            nightDebuff = false;
        }
    }
}