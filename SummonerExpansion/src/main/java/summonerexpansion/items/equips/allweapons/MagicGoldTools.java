package summonerexpansion.items.equips.allweapons;

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
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

public class MagicGoldTools extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue(3, 0.0F);

    public MagicGoldTools(int enchantCost, Item.Rarity rarityTier)
    {
        super("goldaxeminion", FollowPosition.FLYING_CIRCLE, 1F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        summonType = "summonedmagictoolminion";
        rarity = rarityTier;
        drawMaxSummons = false;
        canBeUsedForRaids = true;
        knockback.setBaseValue(42);
        attackDamage.setBaseValue(12.0F).setUpgradedValue(1, 50.0F);
        maxSummons.setBaseValue(3).setUpgradedValue(1, 4).setUpgradedValue(10, 9);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob) {return maxSummons.getValue(getUpgradeTier(item));}

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        FlyingAttackingFollowingMob mob1 = (FlyingAttackingFollowingMob) MobRegistry.getMob("goldaxeminion", level);
        FlyingAttackingFollowingMob mob2 = (FlyingAttackingFollowingMob) MobRegistry.getMob("goldpickminion", level);

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
        tooltips.add(Localization.translate("itemtooltip", "goldtoolstip"));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}