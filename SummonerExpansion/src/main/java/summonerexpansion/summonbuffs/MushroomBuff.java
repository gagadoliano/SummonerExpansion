package summonerexpansion.summonbuffs;

import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.level.maps.Level;

import java.util.function.BiConsumer;

public class MushroomBuff extends Buff
{
    public boolean killedMob = false;
    public int killedcooldown = 0;

    public MushroomBuff()
    {
        shouldSave = false;
        canCancel = false;
        isVisible = true;
    }

    @Override
    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber) {}

    public void serverTick(ActiveBuff buff)
    {
        if (killedMob)
        {
            killedcooldown++;
            if (killedcooldown >= 120)
            {
                killedMob = false;
                killedcooldown = 0;
            }
        }
    }

    @Override
    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if ((event.target.removed() || event.target.getHealth() <= 0) && !killedMob && event.target.isHostile && buff.owner.isInCombat())
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            Level level = buff.owner.getLevel();
            AttackingFollowingMob mob = (AttackingFollowingMob)MobRegistry.getMob("mushroomminion", level);
            attackerMob.serverFollowersManager.addFollower("mushroombuff", mob, FollowPosition.WALK_CLOSE, "mushroombuff", 1.0F, (p) -> 10, null, false);
            mob.getLevel().entityManager.addMob(mob, buff.owner.x, buff.owner.y);
            killedMob = true;
        }
    }

    public boolean shouldDrawDuration(ActiveBuff buff) { return false; }
}