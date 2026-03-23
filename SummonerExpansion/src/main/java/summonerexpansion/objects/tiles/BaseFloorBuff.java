package summonerexpansion.objects.tiles;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class BaseFloorBuff extends Buff
{
    protected ModifierValue<?>[] modifiers;

    public BaseFloorBuff(ModifierValue<?>... modifiers)
    {
        canCancel = false;
        shouldSave = false;
        isImportant = true;
        isVisible = false;
        this.modifiers = modifiers;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        for(ModifierValue<?> modifier : this.modifiers)
        {
            modifier.apply(buff);
        }
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }
}