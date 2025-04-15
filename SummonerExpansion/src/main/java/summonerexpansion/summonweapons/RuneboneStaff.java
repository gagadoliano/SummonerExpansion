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
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

public class RuneboneStaff extends SummonToolItem
{
    public IntUpgradeValue rangedBoneChance = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(2, 4).setUpgradedValue(3, 6).setUpgradedValue(4, 8).setUpgradedValue(5, 10);

    public RuneboneStaff()
    {
        super("runemeleeminion", FollowPosition.WALK_CLOSE, 1F, 800);
        rarity = Rarity.UNCOMMON;
        attackDamage.setBaseValue(23.0F).setUpgradedValue(1, 40.0F);
        rangedBoneChance.setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(2, 4).setUpgradedValue(3, 6).setUpgradedValue(4, 8).setUpgradedValue(5, 10);
        canBeUsedForRaids = false;
    }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        AttackingFollowingMob mob1 = (AttackingFollowingMob) MobRegistry.getMob("runerangedminion", level);
        AttackingFollowingMob mob2 = (AttackingFollowingMob) MobRegistry.getMob("runemeleeminion", level);

        if (GameRandom.globalRandom.nextInt(20) <= rangedBoneChance.getValue(getUpgradeTier(item)))
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
        tooltips.add(Localization.translate("itemtooltip", "runebonestafftip"));
        return tooltips;
    }
}