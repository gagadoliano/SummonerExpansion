package summonerexpansion.summondebuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;

public class LampGoldDebuff extends Buff
{
    public LampGoldDebuff()
    {
        canCancel = false;
        isImportant = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.FIRE_DAMAGE_FLAT , 5.0F);
    }

    public void clientTick(ActiveBuff buff)
    {
        if (buff.owner.isVisible())
        {
            Mob owner = buff.owner;
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(226, 170, 0)).givesLight(0.0F, 0.5F).height(16.0F);
        }
    }
}