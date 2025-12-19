package summonerexpansion.items.equips.allweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

import java.awt.*;

public class IceWizardStaff extends SummonToolItem
{
    public IceWizardStaff(int enchantCost, Item.Rarity rarityTier)
    {
        super("icewizardminion", FollowPosition.WALK_CLOSE, 1F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(32.0F).setUpgradedValue(1, 50.0F);
        canBeUsedForRaids = true;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "icewizardtip"));
        if (perspective == null)
        {
            return tooltips;
        }
        else if (perspective.buffManager.hasBuff("frostcrownsetbonus"))
        {
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "icewizardtip2"), new Color(87, 189, 216)));
        }
        return tooltips;
    }
}