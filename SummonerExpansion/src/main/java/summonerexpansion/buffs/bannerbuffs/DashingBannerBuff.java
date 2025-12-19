package summonerexpansion.buffs.bannerbuffs;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.VicinityBuff;

public class DashingBannerBuff extends VicinityBuff
{
    public DashingBannerBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.DASH_STACKS, 1);
        buff.setModifier(BuffModifiers.DASH_COOLDOWN, -0.10F);
    }

    public void updateLocalDisplayName() {
        this.displayName = new LocalMessage("item", this.getStringID());
    }
}