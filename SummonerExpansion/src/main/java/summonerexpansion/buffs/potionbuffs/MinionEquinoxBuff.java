package summonerexpansion.buffs.potionbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.SimplePotionBuff;

public class MinionEquinoxBuff extends SimplePotionBuff
{
    public boolean nightDebuff;

    public MinionEquinoxBuff()
    {
        canCancel = true;
        isVisible = true;
        shouldSave = true;
    }

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        if (nightDebuff)
        {
            activeBuff.setModifier(BuffModifiers.ARMOR , 0.10F);
            activeBuff.setModifier(BuffModifiers.MAX_HEALTH , 0.10F);
        }
        else
        {
            activeBuff.setModifier(BuffModifiers.SUMMON_DAMAGE , 0.10F);
            activeBuff.setModifier(BuffModifiers.SPEED , 0.10F);
        }
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        nightDebuff = buff.owner.getLevel().getWorldEntity().isNight();
    }
}