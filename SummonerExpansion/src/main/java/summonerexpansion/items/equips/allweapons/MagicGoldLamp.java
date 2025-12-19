package summonerexpansion.items.equips.allweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class MagicGoldLamp extends SummonToolItem
{
    public IntUpgradeValue goldlampSpace = new IntUpgradeValue(2, 0.0F).setUpgradedValue(5,1);

    public MagicGoldLamp(int enchantCost, Item.Rarity rarityTier)
    {
        super("lampminiongold", FollowPosition.CIRCLE_FAR, 2f, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(10.0F).setUpgradedValue(1, 40.0F);
        goldlampSpace.setBaseValue(2).setUpgradedValue(5.0F, 1);
        canBeUsedForRaids = true;
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    public float getSummonSpaceTaken(InventoryItem item, ItemAttackerMob attackerMob)
    {
        return goldlampSpace.getValue(getUpgradeTier(item));
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "magicgoldlamptip"));
        tooltips.add(Localization.translate("itemtooltip", "minionspacetakentip", "amount", (int) getSummonSpaceTaken(item, perspective)));
        return tooltips;
    }
}