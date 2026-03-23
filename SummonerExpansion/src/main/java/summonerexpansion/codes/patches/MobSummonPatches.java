package summonerexpansion.codes.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.Mob;
import net.bytebuddy.asm.Advice;

import static summonerexpansion.codes.registries.RegistrySummonModifiers.EMITS_SUMMON_LIGHT;

@ModMethodPatch(target = Mob.class, arguments = {}, name = "clientTick")
public class MobSummonPatches
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This Mob mob)
    {
        if (mob.buffManager.getModifier(EMITS_SUMMON_LIGHT))
        {
            mob.getLevel().lightManager.refreshParticleLightFloat(mob.x, mob.y, 150);
        }
    }
}