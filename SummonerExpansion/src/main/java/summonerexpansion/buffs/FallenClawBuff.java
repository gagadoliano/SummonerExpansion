package summonerexpansion.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class FallenClawBuff extends Buff
{
    public FallenClawBuff()
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