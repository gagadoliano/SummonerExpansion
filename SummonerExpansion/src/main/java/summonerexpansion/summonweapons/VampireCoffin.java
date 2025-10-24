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

public class VampireCoffin extends SummonToolItem
{
    public VampireCoffin()
    {
        super("coffinsentry", FollowPosition.PYRAMID, 1F, 1600, SummonWeaponsLootTable.summonWeapons);
        rarity = Rarity.RARE;
        attackDamage.setBaseValue(60.0F).setUpgradedValue(1, 80.0F);
        canBeUsedForRaids = false;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "sentrytip"));
        tooltips.add(Localization.translate("itemtooltip", "coffinsentrytip"));
        if (perspective.buffManager.hasBuff("bloodplatecowlsetbonus"))
        {
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "coffinsentrytip2"), new Color(180, 15, 50)));
        }
        return tooltips;
    }
}