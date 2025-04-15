package summonerexpansion.summonbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.SimplePotionBuff;

public class MinionFarmBuff extends SimplePotionBuff
{
    public MinionFarmBuff()
    {
        canCancel = true;
        isVisible = true;
        shouldSave = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.SPEED, -0.80f);
        activeBuff.setModifier(BuffModifiers.ITEM_PICKUP_RANGE, 50f);
        activeBuff.setModifier(BuffModifiers.MOB_SPAWN_RATE, 0.50f);
        activeBuff.setModifier(BuffModifiers.SUMMONS_SPEED, 0.40f);
        activeBuff.setModifier(BuffModifiers.SUMMONS_TARGET_RANGE, 0.40f);
    }
}
