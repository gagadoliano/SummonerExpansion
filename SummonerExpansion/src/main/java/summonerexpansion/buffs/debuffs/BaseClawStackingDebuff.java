package summonerexpansion.buffs.debuffs;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class BaseClawStackingDebuff extends Buff
{
    public String clawDebuff;
    public int stackCount = 0;

    public BaseClawStackingDebuff(String clawDebuff)
    {
        canCancel = false;
        isImportant = true;
        this.clawDebuff = clawDebuff;
    }

    public BaseClawStackingDebuff(String clawDebuff, int stackCount)
    {
        canCancel = false;
        isImportant = true;
        this.clawDebuff = clawDebuff;
        this.stackCount = stackCount;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {

    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        if (buff.owner.buffManager.getStacks(this) >= 5)
        {
            if (buff.owner.isServer() && !event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON)
            {
                ActiveBuff ab = new ActiveBuff(clawDebuff, buff.owner, 10F, null);
                if (stackCount > 0)
                {
                    buff.owner.buffManager.addBuff(ab, true).setStacks(stackCount, 10, null);
                    buff.owner.buffManager.removeBuff(this, true);
                }
                else
                {
                    buff.owner.addBuff(ab, true);
                    buff.owner.buffManager.removeBuff(this, true);
                }
            }
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return 5;
    }
}