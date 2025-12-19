package summonerexpansion.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class GoblinSwordStackBuff extends Buff
{
    public GoblinSwordStackBuff()
    {
        isVisible = true;
        canCancel = false;
        shouldSave = false;
        isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public int getStackSize(ActiveBuff buff) {
        return 50;
    }

    public boolean overridesStackDuration() {
        return true;
    }
}