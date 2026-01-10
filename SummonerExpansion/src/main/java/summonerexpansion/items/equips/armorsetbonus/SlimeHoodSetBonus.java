package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
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
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.LinkedList;

public class SlimeHoodSetBonus extends SetBonusBuff implements BuffAbility
{
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(2).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
    public FloatUpgradeValue summonDMG = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.15F).setUpgradedValue(10, 0.15F);
    public FloatUpgradeValue minionDamage = (new FloatUpgradeValue()).setBaseValue(20F).setUpgradedValue(1, 20F).setUpgradedValue(10, 60F);

    public SlimeHoodSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, summonDMG.getValue(buff.getUpgradeTier()));
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        Mob owner = buff.owner;
        float active = 5.0F;
        float cooldown = 60.0F;
        owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.SLIME_SET_COOLDOWN, owner, cooldown, null), false);
        owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.SLIME_DOME_ACTIVE, owner, active, null), false);
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.buffManager.hasBuff(BuffRegistry.Debuffs.SLIME_SET_COOLDOWN.getID());
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        if (buff.owner.isItemAttacker)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob) buff.owner;
            Level level = buff.owner.getLevel();
            float count1 = attackerMob.serverFollowersManager.getFollowerCount("summonedslimemagebuff");
            GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, minionDamage.getValue(buff.getUpgradeTier()));
            if (count1 <= 0.0F)
            {
                AttackingFollowingMob mob1 = (AttackingFollowingMob) MobRegistry.getMob("slimemageminion", level);
                attackerMob.serverFollowersManager.addFollower("summonedslimemagebuff", mob1, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 1, null, false);
                mob1.updateDamage(damage);
                mob1.setRemoveWhenNotInInventory(ItemRegistry.getItem("slimehood"), CheckSlotType.HELMET);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob1, (float)spawnPoint.x, (float)spawnPoint.y);
            }

            float count2 = attackerMob.serverFollowersManager.getFollowerCount("summonedslimewarriorbuff");
            if (count2 <= 0.0F)
            {
                AttackingFollowingMob mob2 = (AttackingFollowingMob) MobRegistry.getMob("slimewarriorminion", level);
                attackerMob.serverFollowersManager.addFollower("summonedslimewarriorbuff", mob2, FollowPosition.SLIME_CIRCLE_MOVEMENT, "summonedmob", 1.0F, 1, null, false);
                mob2.updateDamage(damage);
                mob2.setRemoveWhenNotInInventory(ItemRegistry.getItem("slimehood"), CheckSlotType.HELMET);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob2, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "slimehoodsettip"));
        return tooltips;
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
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "slimehoodsettip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
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