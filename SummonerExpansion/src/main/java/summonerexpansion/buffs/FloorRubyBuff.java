package summonerexpansion.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class FloorRubyBuff extends Buff
{
    public FloorRubyBuff()
    {
        canCancel = false;
        shouldSave = false;
        isImportant = true;
        isVisible = false;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.10F);
        activeBuff.setModifier(BuffModifiers.SUMMONS_SPEED, 0.10F);
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }
}