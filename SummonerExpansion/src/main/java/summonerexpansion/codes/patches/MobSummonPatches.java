package summonerexpansion.codes.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import net.bytebuddy.asm.Advice.OnMethodExit;
import net.bytebuddy.asm.Advice.This;
import summonerexpansion.codes.registry.SummonerModifiers;

@ModMethodPatch(target = Mob.class, arguments = {}, name = "clientTick")
public class MobSummonPatches
{
    @OnMethodExit
    static void onExit(@This Mob mob)
    {
        if (mob.buffManager.getModifier(SummonerModifiers.EMITS_SUMMON_LIGHT))
        {
            mob.getLevel().lightManager.refreshParticleLightFloat(mob.x, mob.y, 150);
        }
    }
}