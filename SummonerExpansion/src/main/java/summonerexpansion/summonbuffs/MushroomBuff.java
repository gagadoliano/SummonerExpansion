package summonerexpansion.summonbuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasKilledEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.gfx.GameResources;
import necesse.gfx.ThemeColorRegistry;
import necesse.gfx.drawOptions.texture.SharedTextureDrawOptions;

import java.awt.*;

public class MushroomBuff extends Buff
{
    public int mushValue = 0;
    public int mushTimer = 0;

    public MushroomBuff()
    {
        shouldSave = false;
        canCancel = false;
        isVisible = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber) {
    }

    @Override
    public void onHasKilledTarget(ActiveBuff buff, MobWasKilledEvent event)
    {
        if (mushValue <= 46)
        {
            mushValue += 5;
        }
    }

    @Override
    public void clientTick(ActiveBuff buff)
    {
        mushTimer++;
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, mushValue);
        if (mushTimer >= 120 && mushValue > 1)
        {
            mushValue -= 1;
            mushTimer = 0;
        }

        if (buff.owner.isVisible() && GameRandom.globalRandom.getChance((double)0.5F))
        {
            Mob owner = buff.owner;
            GameRandom clientRandom = GameRandom.globalRandom;
            owner.getLevel().entityManager.addParticle(owner.x + (float)clientRandom.getIntBetween(-10, 10), owner.y + (float)clientRandom.getIntBetween(-10, 10), Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(clientRandom.nextInt(4), 0, 12)).sizeFades(12, 24).height(1.0F).color(new Color(174, 161, 137));
        }
    }

    public boolean shouldDrawDuration(ActiveBuff buff) { return false; }
}