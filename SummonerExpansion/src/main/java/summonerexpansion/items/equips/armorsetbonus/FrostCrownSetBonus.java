package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.IcicleStaffProjectile;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.allprojs.CopperSetProj;

import java.util.LinkedList;

public class FrostCrownSetBonus extends SetBonusBuff
{
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
    public FloatUpgradeValue summonCritChance = (new FloatUpgradeValue()).setBaseValue(0).setUpgradedValue(1, 0.05F).setUpgradedValue(10, 0.20F);
    public FloatUpgradeValue summonCritDamage = (new FloatUpgradeValue()).setBaseValue(0).setUpgradedValue(1, 0.10F).setUpgradedValue(10, 0.50F);

    public FrostCrownSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.SUMMON_CRIT_CHANCE, summonCritChance.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.SUMMON_CRIT_DAMAGE, summonCritDamage.getValue(buff.getUpgradeTier()));
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON)
        {
            event.target.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.FREEZING, event.target, 5.0F, event.attacker), event.target.isServer());
        }
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, GNDItemMap attackMap)
    {
        super.onItemAttacked(buff, targetX, targetY, attackerMob, attackHeight, item, slot, animAttack, attackMap);
        Level level = buff.owner.getLevel();
        ToolItem toolItem = (ToolItem)item.item;
        if (level.isServer() && toolItem.getDamageType(item) == DamageTypeRegistry.SUMMON)
        {
            String shotTimeKey = "icicleshottime";
            long shotTime = buff.getGndData().getLong(shotTimeKey);
            float totalModifier = DamageTypeRegistry.SUMMON.calculateTotalAttackSpeedModifier(attackerMob);
            int cooldown = Math.round(750.0F * (1.0F / totalModifier));
            if (shotTime + (long)cooldown < level.getWorldEntity().getTime())
            {
                buff.getGndData().setLong(shotTimeKey, level.getWorldEntity().getTime());
                float velocity = 150.0F * buff.owner.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                IcicleStaffProjectile projectile = new IcicleStaffProjectile(level, buff.owner, buff.owner.x, buff.owner.y, (float)targetX, (float)targetY, velocity, 800, toolItem.getAttackDamage(item).modFinalMultiplier(0.25f), 0);
                level.entityManager.projectiles.add(projectile);
            }
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "frostcrownsettip"));
        if (summonCritChance.getValue(ab.getUpgradeTier()) > 0)
        {
            tooltips.add(Localization.translate("itemtooltip", "frostcrownsettip2"));
        }
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}