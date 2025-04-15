package summonerexpansion.summonbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class HoneyBuff extends Buff
{
    public HoneyBuff()
    {
        canCancel = true;
        isVisible = true;
        shouldSave = false;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN, 0.05f);
    }
}