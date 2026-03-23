package summonerexpansion.buffs;

import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.VicinityBuff;

public class BaseBannerBuff extends VicinityBuff
{
    private final ModifierValue<?>[] modifiers;

    public BaseBannerBuff(ModifierValue<?>... modifiers)
    {
        this.modifiers = modifiers;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        for(ModifierValue<?> modifier : this.modifiers)
        {
            modifier.apply(buff);
        }
    }

    public void updateLocalDisplayName() {
        this.displayName = new LocalMessage("item", this.getStringID());
    }
}