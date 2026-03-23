package summonerexpansion.buffs.debuffs;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class BaseStackingDebuff extends Buff
{
    protected ModifierValue<?>[] modifiers;
    protected int stackSize;
    protected Color particleColor;

    public BaseStackingDebuff(Color particleColor, int stackSize, ModifierValue<?>... modifiers)
    {
        isVisible = true;
        canCancel = false;
        shouldSave = false;
        isImportant = true;
        this.modifiers = modifiers;
        this.stackSize = stackSize;
        this.particleColor = particleColor;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        for(ModifierValue<?> modifier : this.modifiers)
        {
            modifier.apply(buff);
        }
    }

    public void clientTick(ActiveBuff buff)
    {
        if (buff.owner.isVisible() && particleColor != null)
        {
            Mob owner = buff.owner;
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(particleColor).height(16.0F);
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return stackSize;
    }

    public boolean overridesStackDuration() {
        return true;
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }
}