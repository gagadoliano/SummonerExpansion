package summonerexpansion.codes.registries;

import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;

public class RegistryLootBag
{
    public static LootTable sunkenChestLoot = new LootTable();

    public static void registerLoot()
    {
        sunkenChestLoot.items.addAll(
                new LootItemList(
                        ChanceLootItem.between(1.00f, "coin", 1, 100),
                        ChanceLootItem.between(0.80f, "titaniumore", 1, 10),
                        ChanceLootItem.between(0.60f, "swampsludge", 1, 10),
                        ChanceLootItem.between(0.40f, "swamplarva", 1, 4),
                        ChanceLootItem.between(0.30f, "mapfragment", 1, 10),
                        ChanceLootItem.between(0.20f, "enchantingscroll", 1, 2),
                        ChanceLootItem.between(0.15f, "brokencoppertool", 1, 5),
                        ChanceLootItem.between(0.10f, "brokenirontool", 1, 5),
                        ChanceLootItem.between(0.05f, "recallscroll", 1, 10),
                        ChanceLootItem.between(0.02f, "stinkflask", 1, 1)
                )
        );
    }
}
