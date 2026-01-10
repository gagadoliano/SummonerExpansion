package summonerexpansion.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class PineWoodBuff extends Buff
{
    public PineWoodBuff()
    {
        canCancel = false;
        isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
    }

    public int getStackSize(ActiveBuff buff) {
        return 20;
    }

    public boolean overridesStackDuration() {
        return true;
    }
}
