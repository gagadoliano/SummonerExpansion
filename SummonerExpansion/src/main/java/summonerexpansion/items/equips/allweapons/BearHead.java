package summonerexpansion.items.equips.allweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

import java.awt.*;

public class BearHead extends SummonToolItem
{
    public BearHead(int enchantCost, Item.Rarity rarityTier)
    {
        super("bearminion", FollowPosition.WALK_CLOSE, 2F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 50.0F);
        canBeUsedForRaids = true;
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bearheadtip"));
        if (perspective == null)
        {
            return tooltips;
        }
        else if (perspective.buffManager.hasBuff("leathersummonersetbonus"))
        {
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "bearheadtip2"), new Color(206, 135, 70)));
        }
        tooltips.add(Localization.translate("itemtooltip", "minionspacetakentip", "amount", (int) getSummonSpaceTaken(item, perspective)));
        return tooltips;
    }
}