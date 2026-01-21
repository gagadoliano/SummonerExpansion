package summonerexpansion.buffs;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class XmasTreeBuff extends Buff
{
    public XmasTreeBuff()
    {
        canCancel = false;
        shouldSave = false;
        isImportant = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.EMITS_LIGHT, true);
        activeBuff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1.20F);
    }

    public void clientTick(ActiveBuff buff)
    {
        super.clientTick(buff);
        if (buff.owner.isVisible())
        {
            Mob owner = buff.owner;
            AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float distance = 20.0F;
            owner.getLevel().entityManager.addParticle(owner.x + GameMath.sin(currentAngle.get()) * distance, owner.y + GameMath.cos(currentAngle.get()) * distance * 0.75F, Particle.GType.CRITICAL).color(new Color(255, 221, 0)).height(0.0F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                float angle = currentAngle.accumulateAndGet(delta * 50.0F / 250.0F, Float::sum);
                float distY = distance * 0.75F;
                pos.x = owner.x + GameMath.sin(angle) * distance;
                pos.y = owner.y + GameMath.cos(angle) * distY * 0.75F;
            }).lifeTime(500).sizeFades(16, 24);
        }

    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }
}