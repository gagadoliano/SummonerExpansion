package summonerexpansion.items.mounts.buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

import static summonerexpansion.codes.registries.RegistrySummonModifiers.TRANSFORMATION_SPEED;

public class MountChiefBuff extends Buff
{
    public MountChiefBuff()
    {
        isVisible = false;
        canCancel = false;
        shouldSave = false;
    }

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setMaxModifier(BuffModifiers.SLOW, 0f);
        activeBuff.setMaxModifier(BuffModifiers.KNOCKBACK_INCOMING_MOD, 0f);
        activeBuff.setModifier(BuffModifiers.ARMOR_FLAT, 10);
        activeBuff.setModifier(BuffModifiers.ARMOR, 0.10f);
        activeBuff.setModifier(TRANSFORMATION_SPEED, 0.50f);
    }
}