package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class BookMushroom extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue(1, 0.0F);

    public BookMushroom()
    {
        super("mushroomsentry", FollowPosition.WALK_CLOSE, 1F, 200, SummonWeaponsLootTable.summonWeapons);
        summonType = "summonedmushroom";
        rarity = Rarity.COMMON;
        drawMaxSummons = false;
        canBeUsedForRaids = false;
        attackDamage.setBaseValue(20F).setUpgradedValue(1, 80.0F);
        maxSummons.setBaseValue(1).setUpgradedValue(2, 2).setUpgradedValue(10,5);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob){return maxSummons.getValue(getUpgradeTier(item));}

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bookmushroomtip"));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}