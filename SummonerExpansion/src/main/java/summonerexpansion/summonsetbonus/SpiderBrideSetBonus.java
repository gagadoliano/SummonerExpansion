package summonerexpansion.summonsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;

import java.awt.geom.Point2D;

public class SpiderBrideSetBonus extends SetBonusBuff implements BuffAbility
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 35);

    public SpiderBrideSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setMaxModifier(BuffModifiers.SLOW, 0.0F);
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        float cooldown = 120.0F;

        if (player.isServer())
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("spiderbrideminion", buff.owner.getLevel());
            attackerMob.serverFollowersManager.addFollower("spiderbridebuff", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 1, null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(mob, buff.owner.getLevel(), attackerMob.x, attackerMob.y);
            mob.updateDamage(damage);
            mob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
        }

        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("spiderbridecooldown"), player, cooldown, null), false);
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.buffManager.hasBuff(BuffRegistry.getBuff("spiderbridecooldown"));
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "spiderbridesettip"));
        return tooltips;
    }
}