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
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

public class BookFrozen extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue(2, 0.0F);

    public BookFrozen()
    {
        super("frozendwarfminion", FollowPosition.WALK_CLOSE, 1F, 400);
        summonType = "summonedfrozendwarf";
        rarity = Rarity.COMMON;
        drawMaxSummons = false;
        canBeUsedForRaids = false;
        attackAnimTime.setBaseValue(400);
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 45.0F);
        maxSummons.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(5,6);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob){return maxSummons.getValue(getUpgradeTier(item));}

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        AttackingFollowingMob mob1 = (AttackingFollowingMob) MobRegistry.getMob("frozendwarfminion", level);
        summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bookfrozentip"));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}