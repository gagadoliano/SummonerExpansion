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

import java.awt.*;

public class DruidDemonClaw extends DruidClaw implements ItemInteractAction
{
    public IntUpgradeValue maxSummonedMinions = new IntUpgradeValue(1, 0.0F);

    public DruidDemonClaw(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, CloseRangeWeaponsLootTable.closeRangeWeapons);
        rarity = rarityTier;
        attackAnimTime.setBaseValue(290);
        attackDamage.setBaseValue(20.0F).setUpgradedValue(1.0F, 100F);
        attackRange.setBaseValue(100);
        knockback.setBaseValue(20);
        maxDashStacks.setBaseValue(4).setUpgradedValue(1, 5);
        dashRange.setBaseValue(120).setUpgradedValue(1, 300);
        debuffDuration.setBaseValue(10f).setUpgradedValue(1, 30f);
        maxSummonedMinions.setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(5, 6);
        slashColor = new Color(121, 100, 186);
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);

        Buff demonBuff = SummonerBuffs.SummonBuffs.CLAWDEMON;
        attacker.buffManager.addBuff(new ActiveBuff(demonBuff, attacker, debuffDuration.getValue(getUpgradeTier(item)), null), true);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        InventoryItem out = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);

        Buff demonBuff = SummonerBuffs.SummonBuffs.CLAWDEMON;
        if (animAttack == 0 && attackerMob.isServer() && attackerMob.buffManager.getStacks(demonBuff) >= 5 && attackerMob.serverFollowersManager.getFollowerCount("clawdemonportalsentry") < maxSummonedMinions.getValue(getUpgradeTier(item)))
        {
            attackerMob.buffManager.removeBuff(demonBuff, true);
            ClawDemonPortalSentry mob = new ClawDemonPortalSentry();
            attackerMob.serverFollowersManager.addFollower("clawdemonportalsentry", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, maxSummonedMinions.getValue(getUpgradeTier(item)), null, false);
            mob.updateDamage(getAttackDamage(item).modFinalMultiplier(0.5F));
            mob.setEnchantment(getEnchantment(item));
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x, attackerMob.y);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "druiddemonclawtip"));
        return tooltips;
    }
}