package summonerexpansion.summonarmorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

import java.awt.geom.Point2D;

public class GhostCaptainSetBonus extends SetBonusBuff implements BuffAbility
{
    public FloatUpgradeValue ghostCaptainDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(50F).setUpgradedValue(1.0F, 50.0F);

    public GhostCaptainSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        float cooldown = 75F;

        if (player.isServer())
        {
            GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, ghostCaptainDamage.getValue(buff.getUpgradeTier()));
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("ghostcaptainsminion", buff.owner.getLevel());
            attackerMob.serverFollowersManager.addFollower("ghostcaptainsbuff", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1F, 1, null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(mob, buff.owner.getLevel(), attackerMob.x, attackerMob.y);
            mob.updateDamage(damage);
            mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("ghostcaptainshat"), CheckSlotType.HELMET);
            mob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
        }

        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("ghostcaptainscooldown"), player, cooldown, null), false);
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.buffManager.hasBuff(BuffRegistry.getBuff("ghostcaptainscooldown"));
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "ghostcaptainssettip"));
        return tooltips;
    }
}