package summonerexpansion.buffs.debuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;

import java.awt.*;

public class SwampFlaskDebuff extends Buff
{
    public SwampFlaskDebuff()
    {
        this.shouldSave = true;
        this.isImportant = true;
        this.canCancel = false;
    }

    protected float getDamagePerSecond(ActiveBuff buff)
    {
        float bleedDamagePerSec = buff.getGndData().getFloat("damagepersec");
        return bleedDamagePerSec != 0.0F ? bleedDamagePerSec : 10.0F;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.POISON_DAMAGE_FLAT, this.getDamagePerSecond(buff));
    }

    public static Color getSwampFlaskParticleColor()
    {
        return GameRandom.globalRandom.getOneOf(new Color(150, 154, 38), new Color(97, 115, 8), new Color(47, 79, 8), new Color(156, 160, 40), new Color(73, 93, 0));
    }

    public void clientTick(ActiveBuff buff)
    {
        super.clientTick(buff);
        Mob owner = buff.owner;
        if (owner.isVisible())
        {
            owner.getLevel().entityManager.addParticle(owner.x + (float)GameRandom.globalRandom.getIntBetween(-12, 12), owner.y + (float)GameRandom.globalRandom.getIntBetween(-12, 12), Particle.GType.COSMETIC).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.nextInt(5), 0, 12)).sizeFadesInAndOut(4, 12, 0.3F).movesConstant(owner.dx / 2.0F + (float)GameRandom.globalRandom.getIntBetween(-3, 3), owner.dy / 2.0F + (float)GameRandom.globalRandom.getIntBetween(3, 3)).color(getSwampFlaskParticleColor()).heightMoves(10.0F, (float)GameRandom.globalRandom.getIntBetween(30, 40));
        }
    }
}