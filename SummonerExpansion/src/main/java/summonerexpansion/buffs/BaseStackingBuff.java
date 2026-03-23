package summonerexpansion.buffs;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class BaseStackingBuff extends Buff
{
    protected ModifierValue<?>[] modifiers;
    protected int stackSize;

    public BaseStackingBuff(int stackSize, ModifierValue<?>... modifiers)
    {
        isVisible = true;
        canCancel = false;
        shouldSave = false;
        isImportant = true;
        this.modifiers = modifiers;
        this.stackSize = stackSize;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        for(ModifierValue<?> modifier : this.modifiers)
        {
            modifier.apply(buff);
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return stackSize;
    }

    public boolean overridesStackDuration() {
        return true;
    }
}