package summonerexpansion.allpresets.Snow;

import necesse.engine.util.GameRandom;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.maps.presets.Preset;

public class ChristmasShrinePreset extends Preset
{
    public ChristmasShrinePreset(GameRandom random)
    {
        super("eNq1VcFOwzAM_ZV8QA5bu8E49MR24DQ0kDigHtLg0UBIkGPUA-LfSZtWRNXWrVqRHMvxe0leXLe9320eNo_Zd6VeqMxueAnqtSQfkNJwt3bZc3rNmTO2qhOcLWZh5sgaKFDJ9722FvOG37LjUfP7uRjrj1N4n3eKM4Sfyxuj55x7Ddcn57Z4A0lN8T0qFHK2ulpyVqCtTAECpcBPIE9OEs60MARoOJunKz-VJSpHH8JJFOQf2HwxW0ZZQoDuhLD_hdYoO4gEPY3GEB5aGZAgMsSHNmxZkR-pZYTlHC0JUtZcWJ-gMo3i2NLI9_JJt-QYmow8a9Tt6xd5O11_DPbNn_sPwjQWKrKbpieGLe1sKmiqClRC6zVIi21j3Gr_FcoIv4CTKDQcwWQdbqkEfFLo_w57oR38_AKz3k-F");
        addInventory(new LootTable(LootTablePresets.christmasChestLootTable), random, 3, 3);
        addInventory(new LootTable(LootTablePresets.christmasChestLootTable), random, 3, 4);
        addInventory(new LootTable(LootTablePresets.christmasChestLootTable), random, 3, 5);
        addInventory(new LootTable(LootTablePresets.christmasChestLootTable), random, 5, 3);
        addInventory(new LootTable(LootTablePresets.christmasChestLootTable), random, 5, 4);
        addInventory(new LootTable(LootTablePresets.christmasChestLootTable), random, 5, 5);
        addInventory(new LootTable(LootTablePresets.christmasChestLootTable), random, 4, 5);
        addHuman("guardhuman", 5, 6, (humanMob) -> {
            humanMob.equipmentInventory.clearInventory();
            InventoryItem hat = new InventoryItem("snowhood", 1);
            humanMob.equipmentInventory.setItem(0, hat);
            InventoryItem shirt = new InventoryItem("snowcloak", 1);
            humanMob.equipmentInventory.setItem(1, shirt);
            InventoryItem shoes = new InventoryItem("snowboots", 1);
            humanMob.equipmentInventory.setItem(2, shoes);
            InventoryItem weapon = new InventoryItem("frostspear", 1);
            humanMob.equipmentInventory.setItem(6, weapon);
        }, random);
    }
}
