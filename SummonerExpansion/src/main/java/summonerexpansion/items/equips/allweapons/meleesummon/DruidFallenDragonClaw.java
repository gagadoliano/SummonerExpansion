package summonerexpansion.items.equips.allweapons.meleesummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.CloseRangeWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.codes.registry.SummonerBuffs;
import summonerexpansion.mobs.summonminions.meleeminions.ClawDemonPortalSentry;
import summonerexpansion.mobs.summonminions.wormminions.FallenDragonHeadMinion;

import java.awt.*;

public class DruidFallenDragonClaw extends DruidClaw implements ItemInteractAction
{
    public IntUpgradeValue maxSummonedMinions = new IntUpgradeValue(1, 0.0F);

    public DruidFallenDragonClaw(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, CloseRangeWeaponsLootTable.closeRangeWeapons);
        rarity = rarityTier;
        attackAnimTime.setBaseValue(260);
        attackDamage.setBaseValue(80.0F).setUpgradedValue(1.0F, 100F);
        attackRange.setBaseValue(100);
        knockback.setBaseValue(50);
        maxDashStacks.setBaseValue(5).setUpgradedValue(1, 5);
        dashRange.setBaseValue(220).setUpgradedValue(1, 300);
        debuffDuration.setBaseValue(10f).setUpgradedValue(1, 30f);
        maxSummonedMinions.setBaseValue(1).setUpgradedValue(1, 1).setUpgradedValue(10, 4);
        slashColor = new Color(0, 64, 81);
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        Buff fallenBuff = SummonerBuffs.SummonBuffs.CLAWFALLEN;
        attacker.buffManager.addBuff(new ActiveBuff(fallenBuff, attacker, debuffDuration.getValue(getUpgradeTier(item)), null), true);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        InventoryItem out = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);

        Buff fallenBuff = SummonerBuffs.SummonBuffs.CLAWFALLEN;
        if (animAttack == 0 && attackerMob.isServer() && attackerMob.buffManager.getStacks(fallenBuff) >= 5 && attackerMob.serverFollowersManager.getFollowerCount("fallendragonsummoned") < maxSummonedMinions.getValue(getUpgradeTier(item)))
        {
            attackerMob.buffManager.removeBuff(fallenBuff, true);
            FallenDragonHeadMinion mob = new FallenDragonHeadMinion();
            attackerMob.serverFollowersManager.addFollower("fallendragonsummoned", mob, FollowPosition.WIDE_CIRCLE_MOVEMENT, "summonedmob", 1.0F, maxSummonedMinions.getValue(getUpgradeTier(item)), null, false);
            mob.updateDamage(getAttackDamage(item).modFinalMultiplier(0.4F));
            mob.setEnchantment(getEnchantment(item));
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x, attackerMob.y);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "druidfallendragonclawtip", "amount", maxSummonedMinions.getValue(getUpgradeTier(item))));
        return tooltips;
    }
}