package summonerexpansion.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class ClawVultureBuff extends Buff
{
    public ClawVultureBuff()
    {
        canCancel = false;
        isVisible = true;
        shouldSave = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        if (activeBuff.owner.buffManager.getStacks(this) >= 6)
        {
            activeBuff.setModifier(BuffModifiers.SPEED, 0.10f);
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return 6;
    }
}