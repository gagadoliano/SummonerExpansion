package summonerexpansion.buffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTexture.GameTexture;

import java.awt.*;

public class SunflowerBuffT5 extends Buff
{
    public SunflowerBuffT5()
    {
        canCancel = false;
        shouldSave = false;
        isImportant = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 0.50F);
        activeBuff.setModifier(BuffModifiers.HEALTH_REGEN_FLAT, 1F);
    }

    public void clientTick(ActiveBuff buff)
    {
        if (buff.owner.isVisible())
        {
            Mob owner = buff.owner;
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 2.0F, owner.dy / 2.0F).color(new Color(226, 177, 0)).givesLight(40F, 0.3F).height(16.0F);
        }
    }

    @Override
    public void loadTextures()
    {
        super.loadTextures();
        iconTexture = GameTexture.fromFile("buffs/sunflowerbuff");
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }
}