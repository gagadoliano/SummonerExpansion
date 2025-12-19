package summonerexpansion.buffs.debuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class DemonClawBuff extends Buff
{
    public DemonClawBuff()
    {
        canCancel = false;
        isImportant = true;
        isVisible = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {

    }

    public int getStackSize(ActiveBuff buff) {
        return 5;
    }
}