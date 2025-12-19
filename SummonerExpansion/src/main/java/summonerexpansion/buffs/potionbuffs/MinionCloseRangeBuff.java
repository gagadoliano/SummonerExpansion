package summonerexpansion.buffs.potionbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.SimplePotionBuff;

public class MinionCloseRangeBuff extends SimplePotionBuff
{
    public MinionCloseRangeBuff()
    {
        canCancel = true;
        isVisible = true;
        shouldSave = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.SUMMONS_TARGET_RANGE, -0.80f);
        activeBuff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.20f);
        activeBuff.setModifier(BuffModifiers.SUMMONS_SPEED, 0.20f);
    }
}
