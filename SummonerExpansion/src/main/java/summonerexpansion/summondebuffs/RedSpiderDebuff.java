package summonerexpansion.summondebuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;

import java.awt.*;

public class RedSpiderDebuff extends Buff
{
    public RedSpiderDebuff()
    {
        canCancel = false;
        isImportant = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.POISON_DAMAGE , 1F);
    }

    public void clientTick(ActiveBuff buff)
    {
        if (buff.owner.isVisible())
        {
            buff.owner.getLevel().entityManager.addParticle(buff.owner.x + (float)GameRandom.globalRandom.getIntBetween(-12, 12), buff.owner.y + (float)GameRandom.globalRandom.getIntBetween(-12, 12), Particle.GType.COSMETIC).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.nextInt(5), 0, 12)).sizeFadesInAndOut(4, 12, 0.3F).movesConstant(buff.owner.dx / 2.0F + (float)GameRandom.globalRandom.getIntBetween(-3, 3), buff.owner.dy / 2.0F + (float)GameRandom.globalRandom.getIntBetween(3, 3)).color(new Color(98, 0, 0)).heightMoves(10.0F, (float)GameRandom.globalRandom.getIntBetween(30, 40));
        }
    }
}