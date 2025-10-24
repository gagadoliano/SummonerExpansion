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

public class VampireWings extends SummonToolItem
{
    public VampireWings()
    {
        super("vampireminion", FollowPosition.WALK_CLOSE, 1F, 400, SummonWeaponsLootTable.summonWeapons);
        rarity = Rarity.UNCOMMON;
        attackDamage.setBaseValue(20.0F).setUpgradedValue(1, 65.0F);
        canBeUsedForRaids = true;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "vampirewingstip"));
        if (perspective.buffManager.hasBuff("bloodplatecowlsetbonus"))
        {
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "vampirewingstip2"), new Color(180, 15, 50)));
        }
        return tooltips;
    }
}