package summonerexpansion.items.equips.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class SandWormStaff extends SummonToolItem
{
    public SandWormStaff()
    {
        super("sandwormheadminion", FollowPosition.FLYING_CIRCLE_FAST, 2F, 800, SummonWeaponsLootTable.summonWeapons);
        rarity = Rarity.RARE;
        attackDamage.setBaseValue(50.0F).setUpgradedValue(1, 150.0F);
        canBeUsedForRaids = true;
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "sandwormstafftip"));
        tooltips.add(Localization.translate("itemtooltip", "minionspacetakentip", "amount", (int) getSummonSpaceTaken(item, perspective)));
        return tooltips;
    }
}