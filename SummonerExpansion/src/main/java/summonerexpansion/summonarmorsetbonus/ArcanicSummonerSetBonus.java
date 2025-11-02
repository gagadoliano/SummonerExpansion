package summonerexpansion.summonarmorsetbonus;

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
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import summonerexpansion.summonminions.SetArcanicPylonSentry;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class ArcanicSummonerSetBonus  extends SetBonusBuff implements BuffAbility
{
    public FloatUpgradeValue pylonDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(40F).setUpgradedValue(1F, 40F);
    public FloatUpgradeValue summonDamage = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 1.00F);
    public IntUpgradeValue minionDuration = (new IntUpgradeValue()).setBaseValue(300).setUpgradedValue(1F, 400).setUpgradedValue(10F, 1200);

    public ArcanicSummonerSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        if (buff.owner.buffManager.hasBuff(BuffRegistry.Debuffs.MANA_EXHAUSTION))
        {
            buff.setModifier(BuffModifiers.SUMMON_DAMAGE, summonDamage.getValue(buff.getUpgradeTier()));
        }
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        float cooldown = 75F;

        if (player.isServer())
        {
            GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, pylonDamage.getValue(buff.getUpgradeTier()));
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            SetArcanicPylonSentry mob = new SetArcanicPylonSentry();
            attackerMob.serverFollowersManager.addFollower("arcanicsummonbuff", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1F, 1, null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(mob, buff.owner.getLevel(), attackerMob.x, attackerMob.y);
            mob.updateDamage(damage);
            mob.lifeTime = minionDuration.getValue(buff.getUpgradeTier());
            mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("arcanicsummonhelmet"), CheckSlotType.HELMET);
            attackerMob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
        }
        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("arcanicsummoncooldown"), player, cooldown, null), false);
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.buffManager.hasBuff(BuffRegistry.getBuff("arcanicsummoncooldown"));
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "arcanicsummonsettip"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = pylonDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip minionDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "arcanicsummonsettip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = pylonDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }
            minionDamageTip.setCompareValue(compareDamage);
        }
        list.add(minionDamageTip);
    }
}
