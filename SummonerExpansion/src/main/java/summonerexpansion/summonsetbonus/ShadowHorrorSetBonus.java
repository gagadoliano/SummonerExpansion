package summonerexpansion.summonsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public class ShadowHorrorSetBonus extends SetBonusBuff
{
    public FloatUpgradeValue horrorDamage = (new FloatUpgradeValue(0.0F, 0.2F)).setBaseValue(50.0F).setUpgradedValue(1.0F, 60.0F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);

    public ShadowHorrorSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
    }

    public void tickEffect(ActiveBuff buff, Mob owner)
    {
        if (owner.getLevel().tickManager().getTotalTicks() % 2L == 0L)
        {
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(33, 35, 42)).height(16.0F);
        }
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        if (buff.owner.isItemAttacker && buff.owner.isInCombat())
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("horrorbabyminion");
            if (GameRandom.globalRandom.getChance(0.02F) && count <= 4)
            {
                Level level = buff.owner.getLevel();
                AttackingFollowingMob mob = (AttackingFollowingMob)MobRegistry.getMob("horrorbabyminion", level);
                attackerMob.serverFollowersManager.addFollower("horrorbabyminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, (p) -> 5, null, false);
                Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(mob, level, attackerMob.x, attackerMob.y);
                mob.updateDamage(new GameDamage(DamageTypeRegistry.SUMMON, horrorDamage.getValue(buff.getUpgradeTier())));
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("shadowhorrorhood"), CheckSlotType.HELMET);
                mob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
            }
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "horrorsettip"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}