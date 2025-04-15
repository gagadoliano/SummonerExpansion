package summonerexpansion.summonbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class WoodBuff extends Buff
{
    public WoodBuff()
    {
        canCancel = true;
        isVisible = true;
        shouldSave = false;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.MINING_RANGE, 2F);
        activeBuff.setModifier(BuffModifiers.MINING_SPEED, 0.20f);
    }
}