package summonerexpansion.buffs;

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

public class BaseSentryStackingBuff extends Buff
{
    protected ModifierValue<?>[] modifiers;
    protected int stackSize;
    protected Color particleColor;

    public BaseSentryStackingBuff(Color particleColor, int stackSize, ModifierValue<?>... modifiers)
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
        if (buff.owner.isVisible())
        {
            Mob owner = buff.owner;
            AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float distance = 20.0F;
            owner.getLevel().entityManager.addParticle(owner.x + GameMath.sin(currentAngle.get()) * distance, owner.y + GameMath.cos(currentAngle.get()) * distance * 0.75F, Particle.GType.CRITICAL).color(particleColor).height(0.0F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                float angle = currentAngle.accumulateAndGet(delta * 50.0F / 250.0F, Float::sum);
                float distY = distance * 0.75F;
                pos.x = owner.x + GameMath.sin(angle) * distance;
                pos.y = owner.y + GameMath.cos(angle) * distY * 0.75F;
            }).lifeTime(500).sizeFades(16, 24);
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