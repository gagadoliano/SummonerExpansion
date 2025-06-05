package summonerexpansion.summonbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class HorrorGlaiveStackBuff extends Buff
{
    public HorrorGlaiveStackBuff()
    {
        isVisible = true;
        canCancel = false;
        shouldSave = false;
        isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public int getStackSize(ActiveBuff buff) {
        return 100;
    }

    public boolean overridesStackDuration() {
        return true;
    }
}