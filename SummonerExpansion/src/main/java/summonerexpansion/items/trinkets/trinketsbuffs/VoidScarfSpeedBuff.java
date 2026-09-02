package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class VoidScarfSpeedBuff extends Buff
{
    public VoidScarfSpeedBuff()
    {
        shouldSave = false;
        isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SPEED, 0.50F);
    }
}