package summonerexpansion.codes.registries;

import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.lootTable.lootItem.RotationLootItem;
import necesse.inventory.lootTable.presets.*;

public class RegistryChestLoot
{
    // Chest
    public static LootTable trainingPlainBarrel1 = new LootTable();
    public static LootTable trainingPlainBarrel2 = new LootTable();
    public static LootTable sandLibraryBookcase = new LootTable();
    public static LootTable smallHorrorChest = new LootTable();
    public static LootTable smallHorrorChestExtra = new LootTable();
    public static LootTable beehiveChest = new LootTable();
    public static LootTable druidFisher = new LootTable();
    public static LootTable druidChest = new LootTable();

    // Pedal
    public static LootTable woodenIdolPedal = new LootTable();

    public static void registerLoot()
    {
        // Mod chest table
        trainingPlainBarrel1.items.addAll(
                new LootItemList(
                        new ChanceLootItem(0.80f, "goblinsword"),
                        new ChanceLootItem(0.60f, "enchantingscroll"),
                        new ChanceLootItem(0.40f, "leatherglove"),
                        ChanceLootItem.between(0.50f, "ironbar", 1, 10),
                        ChanceLootItem.between(0.20f, "healthpotion", 1, 10),
                        ChanceLootItem.between(0.20f, "hardboiledegg", 1, 10),
                        ChanceLootItem.between(0.20f, "friedegg", 1, 10)
                )
        );
        trainingPlainBarrel2.items.addAll(
                new LootItemList(
                        new ChanceLootItem(0.60f, "enchantingscroll"),
                        ChanceLootItem.between(0.50f, "rottenbread", 1, 5),
                        ChanceLootItem.between(0.20f, "clothscraps", 1, 5),
                        ChanceLootItem.between(0.20f, "bread", 1, 5),
                        ChanceLootItem.between(0.20f, "torch", 1, 15),
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
        smallHorrorChestExtra.items.addAll(
                new LootItemList(
                        ChanceLootItem.between(0.80f, "purehorror", 1, 15),
                        new ChanceLootItem(0.10f, "horrorshield"),
                        new ChanceLootItem(0.10f, "shadowhorrorcape"),
                        new ChanceLootItem(0.10f, "shadowhorrorhood"),
                        new ChanceLootItem(0.10f, "shadowhorrormantle"),
                        new ChanceLootItem(0.10f, "shadowhorrorboots"),
                        new ChanceLootItem(0.10f, "horrorcultmap")
                )
        );
        druidFisher.items.add(
                new LootItemList(
                        new LootItem("swampfish"),
                        ChanceLootItem.between(0.80f, "swamplarva", 1, 10),
                        new ChanceLootItem(0.80f, "fishingpotion"),
                        new ChanceLootItem(0.80f, "carp"),
                        new ChanceLootItem(0.80f, "salmon"),
                        new ChanceLootItem(0.80f, "gobfish"),
                        new ChanceLootItem(0.80f, "halffish"),
                        new ChanceLootItem(0.02f, "overgrownfishingrod")
                )
        );
        beehiveChest.items.addAll(
                new LootItemList(
                        new ChanceLootItem(0.80f, "apiaryframe"),
                        new ChanceLootItem(0.50f, "yellowflowerpatch"),
                        new ChanceLootItem(0.50f, "redflowerpatch"),
                        new ChanceLootItem(0.50f, "purpleflowerpatch"),
                        new ChanceLootItem(0.50f, "blueflowerpatch"),
                        new ChanceLootItem(0.50f, "whiteflowerpatch"),
                        new ChanceLootItem(0.40f, "honeybee"),
                        ChanceLootItem.between(0.80f, "honey", 1, 10),
                        ChanceLootItem.between(0.40f, "honey", 1, 10),
                        ChanceLootItem.between(0.60f, "honey", 1, 10),
                        ChanceLootItem.between(0.20f, "honey", 1, 10)
                )
        );
        sandLibraryBookcase.items.addAll(
                new LootItemList(
                        new LootItem("book"),
                        new ChanceLootItem(0.50f, "recallscroll"),
                        new ChanceLootItem(0.40f, "enchantingscroll"),
                        new ChanceLootItem(0.25f, "ancientstatue"),
                        new ChanceLootItem(0.05f, "prophecyslab"),
                        ChanceLootItem.between(0.80f, "quartz", 1, 5),
                        ChanceLootItem.between(0.80f, "mapfragment", 1, 5),
                        ChanceLootItem.between(0.40f, "bone", 1, 5),
                        ChanceLootItem.between(0.80f, "book", 1, 5),
                        ChanceLootItem.between(0.40f, "book", 1, 5),
                        ChanceLootItem.between(0.20f, "book", 1, 5)
                )
        );

        // Pedals
        woodenIdolPedal.items.add(
                new LootItemList(
                        new LootItem("woodenidol")
                )
        );

        // Vanilla chest table

        // Surface
        StartChestLootTable.instance.items.add(new LootItem("druidhousemap"));
        SurfaceRuinsChestLootTable.secondItem.add(new ChanceLootItem(0.01f, "magiccoppertools"));
        AnglerChestLootTable.instance.items.add(new ChanceLootItem(0.02f, ""));
        BlacksmithChestLootTable.ores.add(ChanceLootItem.between(0.08f, "titaniumore", 1, 20));
        CarpenterChestLootTable.instance.items.add(ChanceLootItem.between(0.08f, "ancientlog", 1, 50));
        FarmerChestLootTable.instance.items.add(ChanceLootItem.between(0.01f, "ancienttreesapling", 1, 10));
        FloatingPirateChestLootTable.extra.add(new ChanceLootItem(0.02f, "timeworncoins"));
        PirateChestLootTable.extra.add(new ChanceLootItem(0.02f, "timeworncoins"));

        // Cave
        CaveChestLootTable.basicMainItems.items.add(new ChanceLootItem(0.02f, "magiccopperlamp"));
        CaveChestLootTable.plainsMainItems.items.add(new ChanceLootItem(0.01f, "royalhive"));
        BearBarrelLootTable.instance.items.add(ChanceLootItem.between(0.01f, "fossilhoney", 1, 10));
        AbandonedMineChestLootTable.potions.addAll(
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
        WitchCrateLootTable.potions.add(
                new LootItemList(
                        ChanceLootItem.between(0.20f, "minioncritchancepotion", 1, 5),
                        ChanceLootItem.between(0.20f, "minioncritpotion", 1, 5),
                        ChanceLootItem.between(0.20f, "minionspeedpotion", 1, 5),
                        ChanceLootItem.between(0.20f, "minionrangepotion", 1, 5),
                        ChanceLootItem.between(0.10f, "minioncloserangepotion", 1, 5),
                        ChanceLootItem.between(0.10f, "minionattackspeedpotion", 1, 5)
                )
        );
        DeepCaveChestLootTable.basicMainItems.items.add(new ChanceLootItem(0.02f, "magictungstenlamp"));
        FishianBarrelLootTable.mainItems.items.add(new ChanceLootItem(0.50f, "fishianeggs"));
        TempleChestLootTable.mainItems.items.add(new ChanceLootItem(0.01f, "sandwormstaff"));
    }
}