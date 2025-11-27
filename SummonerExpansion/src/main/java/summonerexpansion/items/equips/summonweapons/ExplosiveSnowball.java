package summonerexpansion.items.equips.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

import java.awt.*;

public class ExplosiveSnowball extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue(3, 0.0F);

    public ExplosiveSnowball()
    {
        super("explosivesnowmanminion", FollowPosition.PYRAMID, 1F, 200, SummonWeaponsLootTable.summonWeapons);
        summonType = "explosivesnowmanminion";
        rarity = Rarity.COMMON;
        drawMaxSummons = false;
        canBeUsedForRaids = true;
        attackDamage.setBaseValue(20.0F).setUpgradedValue(1, 40.0F);
        maxSummons.setBaseValue(3).setUpgradedValue(2, 4).setUpgradedValue(10, 9);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob) { return maxSummons.getValue(getUpgradeTier(item)); }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "explosivesnowballtip"));
        if (perspective == null)
        {
            return tooltips;
        }
        else if (perspective.buffManager.hasBuff("frostcrownsetbonus"))
        {
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "explosivesnowballtip2"), new Color(87, 189, 216)));
        }
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}