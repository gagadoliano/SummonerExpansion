package summonerexpansion.items.potions;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.SimplePotionBuff;

public class BasePotionBuff extends SimplePotionBuff
{
    private final ModifierValue<?>[] modifiers;

    public BasePotionBuff(ModifierValue<?>... modifiers)
    {
        canCancel = true;
        isVisible = true;
        shouldSave = true;
        this.modifiers = modifiers;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        for(ModifierValue<?> modifier : this.modifiers)
        {
            modifier.apply(buff);
        }
    }
}