package summonerexpansion.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class HorrorSwordStackBuff extends Buff
{
    public HorrorSwordStackBuff()
    {
        isVisible = true;
        canCancel = false;
        shouldSave = false;
        isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.02f);
    }

    public int getStackSize(ActiveBuff buff) {
        return 100;
    }

    public boolean overridesStackDuration() {
        return true;
    }
}