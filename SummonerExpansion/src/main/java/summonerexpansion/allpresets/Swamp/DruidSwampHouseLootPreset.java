package summonerexpansion.allpresets.Swamp;

import necesse.engine.util.GameRandom;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.maps.presets.Preset;
import summonerexpansion.mobs.summonmobs.DruidHumanMob;

public class DruidSwampHouseLootPreset extends Preset
{
    public DruidSwampHouseLootPreset(GameRandom random)
    {
        super("eNrtV8lu2zAQ_RV9AA9cJEc9-NTkkFOKtEAPhQ-0xFhKZVEgGQhF0X8vF4mmREtx0DSXBBgT1PDNwjcztvzl_ubrzbft774uVbVFCFSsPlRqizBQdcNur-X2xwYksqfH7iColEYLEoIGnVS8ZQ8N52JnDRz8cjGOxs-zgFXgZv3snxHnEokSWgNNwf8DeWmWa7IDfP_ICmUrD0FCawGSfJOCpGFUVUwUVHRMaaRR0baUBe3q9iAVVTVvMUiyVJ_0ddPwfs9KrcghAUnXUGUaB-sgPW2aQpuaTkLm0KGNGiT4UwaSo45T0VbJPS1-dvpjgN5tqdtNx8mwV9RS-_-lQRhPutWY5d5_3Za81yrvSAmmU7jKdcSOK8VK7aVVmqqM-KyKikllNFej5sjLp4YKRffmAhnJT1DL1oacY8YQQwJiRqIdzS7xYTWUDEs6eQxAMNx4hKXXVovMPIX-57HsbeFgCIfnwAyOMvjLh53Fz3ShxSnBNLZIZ5Do6uRMlMkyubkh3QBtMa2JbSTbjJb4mfGJqfUl5DE_sWSbz2WWRioYEgIvCgOjBG1f2a6zOxhpFogISrGkgzsguOtK131OiBW3QUHVw1Ns9zgAhwAU4UMAXljhMixOAI1R8Cjo3KnXeCVeCAdHJ_7uswzdBkXXmQkKwuER7PfeDwkynwl0P6F3wddCKGOfvGeZcvDGjLjy3J-ZHN8B77w8JBrjNyuNeXm4ZgUXw_h81q8rYqvEEwP2hzo8iyr3Ia8wmpAsPj0_WL5Ey-P1Ia87oC8qUGHG6c68_3-vhf6X90Abyf78BXopylE=");
        addMob("druidhuman", 3, 3, DruidHumanMob.class, (druid) -> druid.setHome(druid.getTileX(), druid.getTileY()));
        addInventory(LootTablePresets.swampCrate, random, 3, 2);
        addInventory(LootTablePresets.swampCrate, random, 7, 2);
        addInventory(LootTablePresets.deadMerchantsLoot, random, 6, 6);
    }
}