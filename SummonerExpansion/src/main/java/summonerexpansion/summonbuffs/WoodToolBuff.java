package summonerexpansion.summonbuffs;

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
        buff.setMinModifier(BuffModifiers.MINING_RANGE, 1F);
        buff.setMinModifier(BuffModifiers.MINING_SPEED, 0.10F);
    }

    public int getStackSize(ActiveBuff buff) {
        return 8;
    }
}