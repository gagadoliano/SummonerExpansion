package summonerexpansion.items.mounts.buffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;

public class ForestSpiritDebuff extends Buff
{
    public ForestSpiritDebuff()
    {
        canCancel = false;
        isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.POISON_DAMAGE_FLAT, 1.0F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, -0.01F);
        buff.setModifier(BuffModifiers.SPEED, -0.01F);
    }

    public void clientTick(ActiveBuff buff)
    {
        super.clientTick(buff);
        Mob owner = buff.owner;
        if (owner.isVisible() && GameRandom.globalRandom.nextInt(2) == 0)
        {
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)6.0F), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(105, 236, 172)).height(16.0F);
        }
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
    }

    public int getStackSize(ActiveBuff buff) {
        return 50;
    }
}
