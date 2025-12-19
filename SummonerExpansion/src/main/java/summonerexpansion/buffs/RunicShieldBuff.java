package summonerexpansion.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class RunicShieldBuff extends Buff
{
    public RunicShieldBuff()
    {
        canCancel = false;
        isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.ARMOR_FLAT, 2);
    }

    public int getStackSize(ActiveBuff buff) {
        return 12;
    }
}