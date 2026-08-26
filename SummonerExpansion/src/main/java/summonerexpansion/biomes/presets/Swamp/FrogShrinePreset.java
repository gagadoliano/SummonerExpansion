package summonerexpansion.biomes.presets.Swamp;

import necesse.engine.util.GameRandom;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.maps.presets.Preset;

import static summonerexpansion.codes.registries.RegistryChestLoot.frogPedal;
import static summonerexpansion.codes.registries.RegistryChestLoot.woodenIdolPedal;

public class FrogShrinePreset extends Preset
{
    public FrogShrinePreset(GameRandom random)
    {
        super("eNqtVk1PhDAQ_Sv9AT0AXRf3wMndg6c1auLB7KHLjlBTYVOqxBj_uy2spG5bKGoyNDDz3sz0MXzc3G7uNvfZR8sOssziC1wCK0qZxQRLxuF63WSPCUYtlSC0A6NFhFHT0pdjIWjTaN-ugyqgDv3nkXRmXIWTzmw2wcv0xZKA0Gj14IA3mPytu1-KMEPpiU4CqDNnIfzY4Xr_DLnsBl55KBMYxWSVmMOOESHL08PAaaXWSrWSLk-YEg4FaEdqOgqFxmi1vMQop2_AWVUcWHPk9F0VWKhsjPO6lQLgu4e-g774cNKVMZfU8kWWjQN80bGMkbOlINJ43U5ZM7vJMILuOgOl09lOEJmy2eHeo--Gh5p69-YkzNPDqYozr6GDo8oE2XvrTrt3jZm9R2ebZ7M6XIYNrUnS6w6LWlLJ6qp_Euba8HKZRJLgnGQOONzimWDi2W84-O9SjKiReHJOqncGIB4K8aci1qflB6z_R9n279crDlRkUrwCbinna8hrYYck3XPwxHJ9upUliAcm1K_PE-UNfH4BC3_QCQ==");
        addMob("frog", 3, 3, false);
        addMob("frog", 5, 6, false);
        addMob("frog", 7, 7, false);
        addMob("frog", 8, 9, false);
        addMob("frog", 6, 9, false);
        addMob("frog", 4, 4, false);
        addMob("frog", 4, 10, false);
        addMob("frog", 10, 9, false);
        addInventory(frogPedal, random, 7, 5);
    }
}