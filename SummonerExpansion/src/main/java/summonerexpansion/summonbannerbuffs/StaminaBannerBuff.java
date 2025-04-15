package summonerexpansion.summonbannerbuffs;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.VicinityBuff;

public class StaminaBannerBuff extends VicinityBuff
{
    public StaminaBannerBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.STAMINA_CAPACITY, 0.40F);
        buff.setModifier(BuffModifiers.STAMINA_REGEN, 0.10F);
        buff.setModifier(BuffModifiers.STAMINA_USAGE, -0.10F);
    }

    public void updateLocalDisplayName() {
        this.displayName = new LocalMessage("item", this.getStringID());
    }
}