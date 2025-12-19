package summonerexpansion.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class WoodToolBuff extends Buff
{
    public WoodToolBuff()
    {
        canCancel = true;
        shouldSave = false;
        isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MINING_RANGE, 0.10F);
        buff.setModifier(BuffModifiers.MINING_SPEED, 0.01F);
    }

    public int getStackSize(ActiveBuff buff) {
        return 20;
    }
}