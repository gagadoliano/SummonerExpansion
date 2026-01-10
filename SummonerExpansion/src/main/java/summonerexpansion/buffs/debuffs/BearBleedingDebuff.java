package summonerexpansion.buffs.debuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.BleedDebuff;

public class BearBleedingDebuff extends BleedDebuff
{
    protected float getDamagePerSecond(ActiveBuff buff) {
        return 5.0F;
    }

    @Override
    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(BuffModifiers.BLEED_DAMAGE_FLAT, getDamagePerSecond(buff));
        buff.setModifier(BuffModifiers.ALL_DAMAGE , -0.20F);
    }
}