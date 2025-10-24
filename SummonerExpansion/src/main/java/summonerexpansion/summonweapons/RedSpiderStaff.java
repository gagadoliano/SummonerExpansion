package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

import java.awt.*;

public class RedSpiderStaff extends SummonToolItem
{
    public RedSpiderStaff()
    {
        super("redspiderminion", FollowPosition.PYRAMID, 1F, 200, SummonWeaponsLootTable.summonWeapons);
        rarity = Rarity.COMMON;
        attackDamage.setBaseValue(14.0F).setUpgradedValue(1, 40.0F);
        canBeUsedForRaids = true;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "redspiderstafftip"));
        if (perspective.buffManager.hasBuff("redspidersetbonus"))
        {
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "redspiderstafftip2"), new Color(200, 15, 15)));
        }
        return tooltips;
    }
}