package summonerexpansion.buffs.bannerbuffs;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.VicinityBuff;

public class EssenceBannerBuff extends VicinityBuff
{
    public EssenceBannerBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.LIFE_ESSENCE_DURATION, 2.00F);
    }

    public void updateLocalDisplayName() {
        this.displayName = new LocalMessage("item", this.getStringID());
    }
}