package summonerexpansion.items.equips.allweapons.meleesummon;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.lootTable.presets.CloseRangeWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.codes.registry.SummonerBuffs;

public class DruidLeatherClaw extends DruidClaw implements ItemInteractAction
{
    public DruidLeatherClaw(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, CloseRangeWeaponsLootTable.closeRangeWeapons);
        rarity = rarityTier;
        attackAnimTime.setBaseValue(300);
        attackDamage.setBaseValue(10.0F).setUpgradedValue(1.0F, 100F);
        attackRange.setBaseValue(100);
        knockback.setBaseValue(10);
        maxDashStacks.setBaseValue(3).setUpgradedValue(1, 5);
        dashRange.setBaseValue(100).setUpgradedValue(1, 300);
        debuffDuration.setBaseValue(10f).setUpgradedValue(1, 30f);
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        target.buffManager.addBuff(new ActiveBuff(SummonerBuffs.SummonerDebuffs.CLAWLEATHERWEAK, target, debuffDuration.getValue(getUpgradeTier(item)), attacker), true);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "druidleatherclawtip"));
        return tooltips;
    }
}