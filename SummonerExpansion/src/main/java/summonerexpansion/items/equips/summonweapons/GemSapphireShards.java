package summonerexpansion.items.equips.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class GemSapphireShards extends SummonToolItem
{
    public GemSapphireShards()
    {
        super("golemsapphireminion", FollowPosition.WALK_CLOSE, 1F, 1600, SummonWeaponsLootTable.summonWeapons);
        rarity = Rarity.COMMON;
        attackDamage.setBaseValue(80.0F).setUpgradedValue(1, 100.0F);
        canBeUsedForRaids = true;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "sapphireshardtip"));
        return tooltips;
    }
}