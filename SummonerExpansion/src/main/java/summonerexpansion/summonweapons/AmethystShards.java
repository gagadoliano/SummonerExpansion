package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;

public class AmethystShards extends SummonToolItem
{
    public AmethystShards()
    {
        super("golemamethystminion", FollowPosition.WALK_CLOSE, 1F, 1600);
        rarity = Rarity.COMMON;
        attackDamage.setBaseValue(120.0F).setUpgradedValue(1, 140.0F);
        canBeUsedForRaids = false;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "amethystshardtip"));
        return tooltips;
    }
}