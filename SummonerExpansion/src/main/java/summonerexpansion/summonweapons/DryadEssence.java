package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.SpacerGameTooltip;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;

import java.awt.*;

public class DryadEssence extends SummonToolItem
{
    private final int dryadHauntedStacksOnHit = 1;

    public DryadEssence()
    {
        super("spiritghoulminion", FollowPosition.WALK_CLOSE, 1F, 800);
        rarity = Rarity.COMMON;
        attackDamage.setBaseValue(45.0F).setUpgradedValue(1, 90.0F);
        canBeUsedForRaids = false;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "dryadessencetip"));
        tooltips.add(new SpacerGameTooltip(5));
        tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "dryadhauntweapontip", "value", 1), new Color(30, 177, 143), 400));
        return tooltips;
    }
}