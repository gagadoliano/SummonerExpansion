package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

public class MagicTools extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue(3, 0.0F);

    public MagicTools()
    {
        super("magicaxeminion", FollowPosition.FLYING_CIRCLE, 1F, 200);
        summonType = "magicaxeminion";
        rarity = Rarity.COMMON;
        drawMaxSummons = false;
        canBeUsedForRaids = false;
        knockback.setBaseValue(35);
        attackDamage.setBaseValue(5.0F).setUpgradedValue(1, 35.0F);
        maxSummons.setBaseValue(3).setUpgradedValue(1, 4).setUpgradedValue(3, 5).setUpgradedValue(5, 6);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob) { return maxSummons.getValue(getUpgradeTier(item)); }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        FlyingAttackingFollowingMob mob1 = (FlyingAttackingFollowingMob) MobRegistry.getMob("magicaxeminion", level);
        FlyingAttackingFollowingMob mob2 = (FlyingAttackingFollowingMob) MobRegistry.getMob("magicpickaxeminion", level);

        if (GameRandom.globalRandom.nextInt(2) == 1)
        {
            summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        }
        else
        {
            summonServerMob(attackerMob, mob2, x, y, attackHeight, item);
        }
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "magictoolstip"));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}