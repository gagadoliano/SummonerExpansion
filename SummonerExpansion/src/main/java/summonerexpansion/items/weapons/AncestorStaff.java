package summonerexpansion.items.weapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AncestorFollowingMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

public class AncestorStaff extends SummonToolItem
{
    public IntUpgradeValue rangedChance = (new IntUpgradeValue()).setBaseValue(5);
    public IntUpgradeValue maxSummon = (new IntUpgradeValue()).setBaseValue(2);

    public AncestorStaff(int enchantCost, Item.Rarity rarityTier)
    {
        super("ancestorknight", FollowPosition.WALK_CLOSE, 1F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        summonType = "ancestorknight";
        rarity = rarityTier;
        attackDamage.setBaseValue(35.0F).setUpgradedValue(1, 50.0F);
        rangedChance.setBaseValue(5).setUpgradedValue(1, 10).setUpgradedValue(10, 50);
        maxSummon.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(10, 8);
        canBeUsedForRaids = false;
    }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        AncestorFollowingMob mob1 = (AncestorFollowingMob) MobRegistry.getMob("ancestormage", level);
        AncestorFollowingMob mob2 = (AncestorFollowingMob) MobRegistry.getMob("ancestorknight", level);

        if (GameRandom.globalRandom.nextInt(100) <= rangedChance.getValue(getUpgradeTier(item)))
        {
            summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        }
        else
        {
            summonServerMob(attackerMob, mob2, x, y, attackHeight, item);
        }
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob) {return maxSummon.getValue(getUpgradeTier(item));}

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/weapons/" + getStringID());
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "ancestorstafftip"));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}