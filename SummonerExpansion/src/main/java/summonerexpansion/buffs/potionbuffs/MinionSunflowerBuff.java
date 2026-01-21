package summonerexpansion.buffs.potionbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.SimplePotionBuff;
import summonerexpansion.codes.registry.SummonerModifiers;

public class MinionSunflowerBuff extends SimplePotionBuff
{
    public MinionSunflowerBuff()
    {
        canCancel = true;
        isVisible = true;
        shouldSave = true;
    }

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(SummonerModifiers.SENTRY_ATTACK_SPEED, 0.20f);
        activeBuff.setModifier(BuffModifiers.STAMINA_CAPACITY, 0.50f);
        activeBuff.setMaxModifier(BuffModifiers.BLINDNESS, 0f);
    }
}