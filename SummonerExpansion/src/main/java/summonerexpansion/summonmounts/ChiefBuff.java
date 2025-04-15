package summonerexpansion.summonmounts;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class ChiefBuff extends Buff
{
    public ChiefBuff()
    {
        isVisible = false;
        canCancel = false;
        shouldSave = false;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.SLOW, 0f);
        activeBuff.setModifier(BuffModifiers.SPEED, 0.25f);
        activeBuff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0.10f);
        activeBuff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 0.10f);
        activeBuff.setModifier(BuffModifiers.KNOCKBACK_INCOMING_MOD, 0f);
    }
}