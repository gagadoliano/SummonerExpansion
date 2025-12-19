package summonerexpansion.items.equips.allweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.SpacerGameTooltip;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

import java.awt.*;

public class DryadEssence extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue();

    public DryadEssence(int enchantCost, Item.Rarity rarityTier)
    {
        super("spiritghoulminion", FollowPosition.WALK_CLOSE, 3F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(45.0F).setUpgradedValue(1, 90.0F);
        canBeUsedForRaids = false;
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "dryadessencetip"));
        tooltips.add(Localization.translate("itemtooltip", "minionspacetakentip", "amount", (int) getSummonSpaceTaken(item, perspective)));
        tooltips.add(new SpacerGameTooltip(5));
        tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "dryadhauntweapontip", "value", 1), new Color(30, 177, 143), 400));
        return tooltips;
    }
}