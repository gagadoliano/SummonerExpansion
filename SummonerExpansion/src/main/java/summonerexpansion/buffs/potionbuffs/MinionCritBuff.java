package summonerexpansion.buffs.potionbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.SimplePotionBuff;

public class MinionCritBuff extends SimplePotionBuff
{
    public MinionCritBuff()
    {
        canCancel = true;
        isVisible = true;
        shouldSave = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.50f);
    }
}
