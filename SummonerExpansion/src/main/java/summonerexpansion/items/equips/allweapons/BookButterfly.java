package summonerexpansion.items.equips.allweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BiomeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.LevelIdentifier;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.critters.flyingbugs.ButterflyMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

public class BookButterfly extends SummonToolItem
{
    public BookButterfly (int enchantCost, Item.Rarity rarityTier)
    {
        super("butterflyredminion", FollowPosition.FLYING_CIRCLE_FAST, 1F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        canBeUsedForRaids = true;
        attackDamage.setBaseValue(10.0F).setUpgradedValue(1, 30.0F);
    }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        FlyingAttackingFollowingMob mob1;

        if (level.getBiome(attackerMob.getTileX(), attackerMob.getTileY()).equals(BiomeRegistry.FOREST))
        {
            mob1 = (FlyingAttackingFollowingMob) MobRegistry.getMob("butterflygreenminion", level);
            summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        }
        else if (level.getBiome(attackerMob.getTileX(), attackerMob.getTileY()).equals(BiomeRegistry.SNOW))
        {
            mob1 = (FlyingAttackingFollowingMob) MobRegistry.getMob("butterflyblueminion", level);
            summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        }
        else if (level.getBiome(attackerMob.getTileX(), attackerMob.getTileY()).equals(BiomeRegistry.PLAINS))
        {
            mob1 = (FlyingAttackingFollowingMob) MobRegistry.getMob("butterflyredminion", level);
            summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        }
        else
        {
            mob1 = (FlyingAttackingFollowingMob) MobRegistry.getMob("butterflygreenminion", level);
            summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        }
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bookbutterflytip"));
        return tooltips;
    }
}