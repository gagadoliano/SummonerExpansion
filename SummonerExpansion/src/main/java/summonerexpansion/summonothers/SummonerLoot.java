package summonerexpansion.summonothers;

import necesse.entity.mobs.hostile.EnchantedCrawlingZombieMob;
import necesse.entity.mobs.hostile.EnchantedZombieArcherMob;
import necesse.entity.mobs.hostile.EnchantedZombieMob;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;

public class SummonerLoot
{
    public static void registerSummonLoot()
    {
        // Chests
        LootTablePresets.basicCaveChest.items.add(
                new ChanceLootItem(0.02f, "magiccopperlamp")
        );

        LootTablePresets.plainsCaveChest.items.add(
                new ChanceLootItem(0.01f, "royalhive")
        );

        LootTablePresets.templeChest.items.add(
                new ChanceLootItem(0.01f, "sandwormstaff")
        );

        LootTablePresets.alchemistChest.items.addAll(
                new LootItemList(
                        ChanceLootItem.between(0.08f, "minioncritpotion", 1, 3),
                        ChanceLootItem.between(0.01f, "minionfarmpotion", 1, 3),
                        ChanceLootItem.between(0.08f, "minionrangepotion", 1, 3),
                        ChanceLootItem.between(0.08f, "minionspeedpotion", 1, 3),
                        ChanceLootItem.between(0.01f, "minioncloserangepotion", 1, 3),
                        ChanceLootItem.between(0.08f, "minioncritchancepotion", 1, 3),
                        ChanceLootItem.between(0.01f, "minionattackspeedpotion", 1, 3)
                )
        );

        // Drops
        EnchantedZombieMob.lootTable.items.add(
                new ChanceLootItem(0.05F, "spoiledfood", 4)
        );

        EnchantedZombieArcherMob.lootTable.items.add(
                new ChanceLootItem(0.05F, "spoiledfood", 4)
        );

        EnchantedCrawlingZombieMob.lootTable.items.add(
                new ChanceLootItem(0.05F, "spoiledfood", 4)
        );
    }
}