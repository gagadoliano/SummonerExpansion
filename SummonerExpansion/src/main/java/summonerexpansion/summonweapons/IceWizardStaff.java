package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;

public class IceWizardStaff extends SummonToolItem
{
    public IceWizardStaff()
    {
        super("icewizardminion", FollowPosition.WALK_CLOSE, 1F, 800);
        rarity = Rarity.COMMON;
        attackDamage.setBaseValue(32.0F).setUpgradedValue(1, 50.0F);
        canBeUsedForRaids = false;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "icewizardtip"));
        return tooltips;
    }
}