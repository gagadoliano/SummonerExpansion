package summonerexpansion.buffs.debuffs;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import summonerexpansion.codes.registry.SummonerBuffs;

public class PrimordialClawDebuff extends Buff
{
    public PrimordialClawDebuff()
    {
        canCancel = false;
        isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {

    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        if (buff.owner.buffManager.getStacks(this) >= 5)
        {
            if (!event.wasPrevented && buff.owner.isServer() && event.damageType == DamageTypeRegistry.SUMMON)
            {
                buff.owner.addBuff(new ActiveBuff(SummonerBuffs.SummonerDebuffs.SUMMON_GENERIC_INCREASEDOT, buff.owner, 10F, event.target), true);
                buff.owner.buffManager.removeBuff(this, true);
            }
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return 5;
    }
}