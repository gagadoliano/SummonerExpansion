package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class SummonPlagueSetBonus extends SetBonusBuff
{
    public int summonTimer = 0;
    public FloatUpgradeValue minionDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(30F).setUpgradedValue(1.0F, 30F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1F, 2);

    public SummonPlagueSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.POTION_DURATION, 2F);
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        if (buff.owner.isItemAttacker && buff.owner.isInCombat())
        {
            summonTimer++;
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("summonedmousebuff");
            if (summonTimer >= 600 && count <= 7)
            {
                GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, minionDamage.getValue(buff.getUpgradeTier()));
                Level level = buff.owner.getLevel();
                AttackingFollowingMob mob = (AttackingFollowingMob)MobRegistry.getMob("mouseminion", level);
                attackerMob.serverFollowersManager.addFollower("summonedmousebuff", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, (p) -> 8, null, false);
                Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(mob, level, attackerMob.x, attackerMob.y);
                mob.updateDamage(damage);
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("summonplaguemask"), CheckSlotType.HELMET);
                mob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
                summonTimer = 0;
            }
        }
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = minionDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip minionDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "plaguesettip")).append("\n").append(new LocalMessage("itemtooltip", "plaguesettip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = minionDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }
            minionDamageTip.setCompareValue(compareDamage);
        }
        list.add(minionDamageTip);
    }
}
