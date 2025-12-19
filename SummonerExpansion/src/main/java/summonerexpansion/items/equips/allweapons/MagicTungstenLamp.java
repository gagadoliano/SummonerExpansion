package summonerexpansion.items.equips.allweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class MagicTungstenLamp extends SummonToolItem
{
    public MagicTungstenLamp(int enchantCost, Item.Rarity rarityTier)
    {
        super("lampminiontungsten", FollowPosition.CIRCLE_FAR, 1f, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(25.0F).setUpgradedValue(1, 45.0F);
        canBeUsedForRaids = true;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "magictungstenlamptip"));
        return tooltips;
    }
}