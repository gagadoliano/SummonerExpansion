package summonerexpansion.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

import java.awt.*;

public class CloudSpeedBuff extends Buff
{
    public CloudSpeedBuff()
    {
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.10F);
    }

    public int getStackSize(ActiveBuff buff) {
        return 20;
    }
}
