package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

public class ExplosiveSnowball extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue(3, 0.0F);

    public ExplosiveSnowball()
    {
        super("explosivesnowmanminion", FollowPosition.PYRAMID, 1F, 200);
        summonType = "explosivesnowmanminion";
        rarity = Rarity.COMMON;
        drawMaxSummons = false;
        canBeUsedForRaids = false;
        attackDamage.setBaseValue(20.0F).setUpgradedValue(1, 40.0F);
        maxSummons.setBaseValue(3).setUpgradedValue(2, 4).setUpgradedValue(4, 5).setUpgradedValue(5, 6);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob) { return maxSummons.getValue(getUpgradeTier(item)); }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "explosivesnowballtip"));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}