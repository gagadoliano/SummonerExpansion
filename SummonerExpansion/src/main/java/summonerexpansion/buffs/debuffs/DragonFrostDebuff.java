package summonerexpansion.buffs.debuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

import java.awt.*;

public class DragonFrostDebuff extends Buff
{
    public FloatUpgradeValue fireDamage = (new FloatUpgradeValue(0.0F, 0.2F)).setBaseValue(8.0F).setUpgradedValue(1.0F, 8.0F);

    public DragonFrostDebuff()
    {
        this.canCancel = false;
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.FROST_DAMAGE_FLAT, this.fireDamage.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1.04F);
    }

    public void clientTick(ActiveBuff buff)
    {
        if (buff.owner.isVisible())
        {
            Mob owner = buff.owner;
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)6.0F), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(39, 120, 223)).givesLight(0.0F, 0.5F).height(16.0F);
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return 10;
    }
}