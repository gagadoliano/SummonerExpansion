package summonerexpansion.codes.registry;

import necesse.entity.mobs.hostile.*;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.lootTable.lootItem.RotationLootItem;
import necesse.inventory.lootTable.presets.*;

public class SummonerLoot
{
    public static LootTable trainingPlainBarrel1 = new LootTable();
    public static LootTable trainingPlainBarrel2 = new LootTable();
    public static LootTable smallHorrorChest = new LootTable();
    public static LootTable druidDresser = new LootTable();
    public static LootTable druidFisher = new LootTable();
    public static LootTable woodenIdolPedal = new LootTable();

    public static void registerSummonLoot()
    {
        // Mod chest table
        trainingPlainBarrel1.items.addAll(
                new LootItemList(
                        new ChanceLootItem(0.80f, "goblinsword"),
                        new ChanceLootItem(0.60f, "enchantingscroll"),
                        new ChanceLootItem(0.40f, "leatherglove"),
                        ChanceLootItem.between(0.20f, "ironbar", 1, 10),
                        ChanceLootItem.between(0.20f, "healthpotion", 1, 10)
                )
        );
        trainingPlainBarrel2.items.addAll(
                new LootItemList(
                        ChanceLootItem.between(0.50f, "rottenbread", 1, 5),
                        ChanceLootItem.between(0.20f, "bread", 1, 5),
                        ChanceLootItem.between(0.20f, "egg", 1, 10)
                )
        );
        smallHorrorChest.items.add(
                RotationLootItem.privateLootRotation(
                        new LootItem("horrorglaive"),
                        new LootItem("horrorscythe"),
                        new LootItem("horrorsword"),
                        new LootItem("shadowhorrorportal")
                )
        );
        druidDresser.items.addAll(
                new LootItemList(
                        new LootItem("hunterhoodmask"),
                        new LootItem("huntershirt"),
                        new LootItem("hunterboots")
                )
        );
        druidFisher.items.add(
                new LootItemList(
                        new LootItem("swampfish")
                )
        );
        woodenIdolPedal.items.add(
                new LootItemList(
                        new LootItem("woodenidol")
                )
        );

        // Vanilla chest table
        CaveChestLootTable.basicMainItems.items.add(new ChanceLootItem(0.02f, "magiccopperlamp"));

        CaveChestLootTable.plainsMainItems.items.add(new ChanceLootItem(0.01f, "royalhive"));

        TempleChestLootTable.mainItems.items.add(new ChanceLootItem(0.01f, "sandwormstaff"));

        LootTablePresets.alchemistChest.items.addAll(
                new LootItemList(
                        ChanceLootItem.between(0.08f, "minioncritpotion", 1, 3),
                        ChanceLootItem.between(0.08f, "minionrangepotion", 1, 3),
                        ChanceLootItem.between(0.08f, "minionspeedpotion", 1, 3),
                        ChanceLootItem.between(0.08f, "minioncritchancepotion", 1, 3),
                        ChanceLootItem.between(0.01f, "minionfarmpotion", 1, 3),
                        ChanceLootItem.between(0.01f, "minioncloserangepotion", 1, 3),
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