package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.AuraBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.ThemeColorRegistry;
import necesse.gfx.gameTooltips.ListGameTooltips;

import java.awt.*;

public class SporeBagBuff extends AuraBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 35);

    public SporeBagBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.PROJECTILE_VELOCITY, -0.50F);
        buff.setModifier(BuffModifiers.PROJECTILE_PIERCES, 2);
        particleMaxHeight = 2;
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        if (!event.wasPrevented && buff.owner.isServer())
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("sporebagminion", buff.owner.getLevel());
            attackerMob.serverFollowersManager.addFollower("sporebagminionbuff", mob, FollowPosition.FLYING_CIRCLE, "summonedmob", 1F, 3, null, false);
            mob.updateDamage(damage);
            mob.getLevel().entityManager.addMob(mob, buff.owner.x, buff.owner.y);
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        return super.getTooltip(ab, blackboard);
    }

    public boolean shouldDrawDuration(ActiveBuff buff)
    {
        return false;
    }

    public Color getParticleColor() {
        return ThemeColorRegistry.PURPLE.getRandomColor();
    }
}