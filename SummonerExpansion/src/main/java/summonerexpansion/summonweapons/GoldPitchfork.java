package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;

public class GoldPitchfork extends SummonToolItem
{
    public GoldPitchfork()
    {
        super("farmerminion", FollowPosition.WALK_CLOSE, 2F, 800);
        rarity = Rarity.RARE;
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 120.0F);
        canBeUsedForRaids = false;
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