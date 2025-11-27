package summonerexpansion.items.equips.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class CactusStaff extends SummonToolItem
{
    public CactusStaff()
    {
        super("cactusminion", FollowPosition.WALK_CLOSE, 1F, 200, SummonWeaponsLootTable.summonWeapons);
        rarity = Rarity.UNCOMMON;
        attackDamage.setBaseValue(12.0F).setUpgradedValue(1, 50.0F);
        canBeUsedForRaids = true;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "cactusstafftip"));
        return tooltips;
    }
}