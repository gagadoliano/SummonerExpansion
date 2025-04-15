package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;

public class MagicTungstenLamp extends SummonToolItem
{
    public MagicTungstenLamp()
    {
        super("lampminiontungsten", FollowPosition.FLYING, 1f, 800);
        rarity = Rarity.RARE;
        attackDamage.setBaseValue(25.0F).setUpgradedValue(1, 45.0F);
        canBeUsedForRaids = false;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "magictungstenlamptip"));
        return tooltips;
    }
}