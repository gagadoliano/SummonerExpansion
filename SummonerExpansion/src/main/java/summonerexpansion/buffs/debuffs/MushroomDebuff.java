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

public class MushroomDebuff extends Buff
{
    public MushroomDebuff()
    {
        isVisible = true;
        canCancel = false;
        shouldSave = false;
        isImportant = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.SLOW, 0.20f);
    }

    public void clientTick(ActiveBuff buff)
    {
        if (buff.owner.isVisible() && GameRandom.globalRandom.getChance((double)0.5F))
        {
            Mob owner = buff.owner;
            GameRandom clientRandom = GameRandom.globalRandom;
            owner.getLevel().entityManager.addParticle(owner.x + (float)clientRandom.getIntBetween(-10, 10), owner.y + (float)clientRandom.getIntBetween(-10, 10), Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(clientRandom.nextInt(4), 0, 12)).sizeFades(12, 24).height(1.0F).color(new Color(164, 151, 127));
        }
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }
}