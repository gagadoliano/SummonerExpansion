package summonerexpansion.items.equips.allweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class GoldPitchfork extends SummonToolItem
{
    public GoldPitchfork(int enchantCost, Item.Rarity rarityTier)
    {
        super("farmerminion", FollowPosition.WALK_CLOSE, 2F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 120.0F);
        canBeUsedForRaids = true;
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "goldpitchforktip"));
        tooltips.add(Localization.translate("itemtooltip", "minionspacetakentip", "amount", (int)getSummonSpaceTaken(item, perspective)));
        return tooltips;
    }
}