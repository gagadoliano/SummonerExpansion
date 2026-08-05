package summonerexpansion.codes.personalities;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.level.maps.levelData.settlementData.settler.personalities.SimpleQoLModifierSettlerPersonality;

public class DruidicSettlerPersonality extends SimpleQoLModifierSettlerPersonality
{
    public DruidicSettlerPersonality(HumanMob mob)
    {
        super(mob);
        modifiers.add(new SimpleQoLModifierSettlerPersonality.QoLModifierValue(1, 2, false, new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, 0.05F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.10F)));
        modifiers.add(new SimpleQoLModifierSettlerPersonality.QoLModifierValue(3, 4, false, new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, 0.10F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.15F)));
        modifiers.add(new SimpleQoLModifierSettlerPersonality.QoLModifierValue(5, -1, false, new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, 0.15F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.20F), new ModifierValue<>(BuffModifiers.ATTACK_MOVEMENT_MOD, 0.50F)));
        modifiers.add(new SimpleQoLModifierSettlerPersonality.QoLModifierValue(6, -1, true, new ModifierValue<>(BuffModifiers.SPEED, 0.10F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.05F)));
    }
}