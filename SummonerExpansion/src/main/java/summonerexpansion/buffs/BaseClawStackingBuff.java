package summonerexpansion.buffs;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class BaseClawStackingBuff extends Buff
{
    protected ModifierValue<?>[] modifiers;

    public BaseClawStackingBuff(ModifierValue<?>... modifiers)
    {
        isVisible = true;
        canCancel = false;
        shouldSave = false;
        isImportant = true;
        this.modifiers = modifiers;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        for(ModifierValue<?> modifier : this.modifiers)
        {
            modifier.apply(buff);
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return 5;
    }

    public boolean overridesStackDuration() {
        return true;
    }
}