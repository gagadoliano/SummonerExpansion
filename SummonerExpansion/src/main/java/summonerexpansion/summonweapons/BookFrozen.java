package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

import java.awt.*;

public class BookFrozen extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue(2, 0.0F);

    public BookFrozen()
    {
        super("frozendwarfminion", FollowPosition.WALK_CLOSE, 1F, 400, SummonWeaponsLootTable.summonWeapons);
        summonType = "summonedfrozendwarf";
        rarity = Rarity.COMMON;
        drawMaxSummons = false;
        canBeUsedForRaids = true;
        attackAnimTime.setBaseValue(400);
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 45.0F);
        maxSummons.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(10,8);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob){return maxSummons.getValue(getUpgradeTier(item));}

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bookfrozentip"));
        if (perspective.buffManager.hasBuff("frostcrownsetbonus"))
        {
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "bookfrozentip2"), new Color(87, 189, 216)));
        }
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}