package summonerexpansion.summonbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class SapphireFloorBuff extends Buff
{
    public SapphireFloorBuff()
    {
        canCancel = false;
        shouldSave = false;
        isImportant = true;
        isVisible = false;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.SUMMON_CRIT_CHANCE, 0.10F);
        activeBuff.setModifier(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.10F);
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }
}