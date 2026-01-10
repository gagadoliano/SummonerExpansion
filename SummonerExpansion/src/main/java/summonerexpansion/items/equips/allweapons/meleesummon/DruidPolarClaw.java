package summonerexpansion.items.equips.allweapons.meleesummon;

import necesse.engine.localization.Localization;
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
import summonerexpansion.items.equips.allweapons.basesummon.DruidClaw;

public class DruidPolarClaw extends DruidClaw implements ItemInteractAction
{
    public DruidPolarClaw(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, CloseRangeWeaponsLootTable.closeRangeWeapons);
        rarity = rarityTier;
        attackAnimTime.setBaseValue(295);
        attackDamage.setBaseValue(15.0F).setUpgradedValue(1.0F, 100F);
        attackRange.setBaseValue(100);
        knockback.setBaseValue(15);
        maxDashStacks.setBaseValue(3).setUpgradedValue(1, 5);
        dashRange.setBaseValue(110).setUpgradedValue(1, 300);
        debuffDuration.setBaseValue(10f).setUpgradedValue(1, 30f);
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        target.buffManager.addBuff(new ActiveBuff(SummonerBuffs.SummonerDebuffs.CLAWPOLARSLOW, target, debuffDuration.getValue(getUpgradeTier(item)), attacker), true);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "druidpolarclawtip"));
        return tooltips;
    }
}