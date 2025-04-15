package summonerexpansion.summonbuffs;

import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.function.BiConsumer;

public class RunicShieldBuff extends Buff
{
    public RunicShieldBuff()
    {
        isVisible = true;
        canCancel = false;
        isImportant = true;
        sortByDuration = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        updateModifiers(buff);
    }

    public void onStacksUpdated(ActiveBuff buff, ActiveBuff other)
    {
        super.onStacksUpdated(buff, other);
        this.updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        int shieldStacks = buff.getStacks();
        float shieldValue = (float) shieldStacks / 10;
        buff.setMinModifier(BuffModifiers.ARMOR, shieldValue);
    }

    public int getStackSize(ActiveBuff buff) {
        return 12;
    }

    public boolean overridesStackDuration() {
        return true;
    }
}