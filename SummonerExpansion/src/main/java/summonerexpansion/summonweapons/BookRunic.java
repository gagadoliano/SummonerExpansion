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

public class BookRunic extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue(2, 0.0F);

    public BookRunic()
    {
        super("runicshieldminion", FollowPosition.FLYING_CIRCLE, 1F, 200, SummonWeaponsLootTable.summonWeapons);
        summonType = "summonedrunicshield";
        rarity = Rarity.COMMON;
        drawMaxSummons = false;
        canBeUsedForRaids = true;
        attackAnimTime.setBaseValue(400);
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 65.0F);
        maxSummons.setBaseValue(2).setUpgradedValue(1,4).setUpgradedValue(10,8);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob)
    {
        return maxSummons.getValue(getUpgradeTier(item));
    }

    public int getItemAttackerStoppingDistance(ItemAttackerMob mob, InventoryItem item, int attackRange) {
        return 128;
    }

    public int getItemAttackerRunAwayDistance(ItemAttackerMob attackerMob, InventoryItem item) {
        return 96;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bookrunictip", "amount", getStackSize()));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", getMaxSummons(item, perspective)));
        return tooltips;
    }
}