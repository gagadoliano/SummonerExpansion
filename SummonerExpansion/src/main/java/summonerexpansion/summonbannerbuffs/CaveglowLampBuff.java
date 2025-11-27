package summonerexpansion.summonbannerbuffs;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.VicinityBuff;

public class CaveglowLampBuff extends VicinityBuff
{
    public CaveglowLampBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.EMITS_LIGHT, true);
        buff.setModifier(BuffModifiers.MINING_RANGE, 2f);
        buff.setModifier(BuffModifiers.SUMMONS_TARGET_RANGE, 0.20f);
    }

    public void updateLocalDisplayName() {
        displayName = new LocalMessage("object", getStringID());
    }
}