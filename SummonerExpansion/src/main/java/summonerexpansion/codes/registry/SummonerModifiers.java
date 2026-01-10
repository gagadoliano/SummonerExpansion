package summonerexpansion.codes.registry;

import necesse.engine.modifiers.Modifier;
import necesse.engine.modifiers.ModifierLimiter;
import necesse.entity.mobs.buffs.BuffModifiers;

public class SummonerModifiers
{
    public static final Modifier<Boolean> EMITS_SUMMON_LIGHT;

    public static final Modifier<Float> TRANSFORMATION_SPEED;
    public static final Modifier<Float> SENTRY_ATTACK_SPEED;

    static
    {
        EMITS_SUMMON_LIGHT = new Modifier<>(BuffModifiers.LIST, "emitsummonlight", false, false, Modifier.OR_APPEND, Modifier.BOOL_PARSER("emitsummonlight"), null);

        TRANSFORMATION_SPEED = new Modifier<>(BuffModifiers.LIST, "transformationspeed", 1.0F, 0.0F, Modifier.FLOAT_ADD_APPEND, (v) -> Math.max(0.0F, v), Modifier.NORMAL_PERC_PARSER("transformationspeed"), ModifierLimiter.NORMAL_PERC_LIMITER("transformationspeed"));
        SENTRY_ATTACK_SPEED = new Modifier<>(BuffModifiers.LIST, "sentryattackspeed", 1.0F, 0.0F, Modifier.FLOAT_ADD_APPEND, (v) -> Math.max(0.0F, v), Modifier.NORMAL_PERC_PARSER("sentryattackspeed"), ModifierLimiter.NORMAL_PERC_LIMITER("sentryattackspeed"));
    }
}