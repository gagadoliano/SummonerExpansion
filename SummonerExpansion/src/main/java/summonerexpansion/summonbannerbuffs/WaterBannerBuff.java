package summonerexpansion.summonbannerbuffs;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.VicinityBuff;

public class WaterBannerBuff extends VicinityBuff
{
    public WaterBannerBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.BIOME_VIEW_DISTANCE, 5);
        buff.setModifier(BuffModifiers.TRAVEL_DISTANCE, 5);
        buff.setModifier(BuffModifiers.WATER_WALKING, true);
    }

    public void updateLocalDisplayName() {
        displayName = new LocalMessage("object", getStringID());
    }
}