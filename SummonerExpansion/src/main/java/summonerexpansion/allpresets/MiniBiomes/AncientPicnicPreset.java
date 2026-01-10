package summonerexpansion.allpresets.MiniBiomes;

import necesse.engine.util.GameRandom;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.maps.presets.Preset;

import java.util.concurrent.atomic.AtomicInteger;

import static summonerexpansion.codes.registry.SummonerLoot.smallHorrorChest;

public class AncientPicnicPreset extends Preset
{
    public AncientPicnicPreset(GameRandom random, AtomicInteger chestRotation)
    {
        super("eNqtkcFuwjAMhl8lD-BD17UbHHICDpyYtkk7TD24wZCwrEGOGYdp7742XTsE4oYUW3_8W5-t5Ol58bJ41d9HtxarS7DktlZ0AeI8LedRv08noLAxjhrZMsbYGVWy_8zbRQWh3pGRNDdrxzoGlU9LUJ_ExmIjsUbzsW-jqz-AiklOHlv5RT4c2CDvSUDdFWUx7i1MNLB7cjZ0ZBcnzUvwhP1PvX1erICDoLjQjOR8RJ1iQd2nnA_1_PTav-iqX3HmCVkLHwiO6P2cTOBLS7D2dMUznVyJJX5z3H7UBn2kn1_cG4nQ");
        width = 5;
        height = 4;
        addInventory(LootTablePresets.swampCrate, random, 0, 2);
        addInventory(LootTablePresets.deadMerchantsLoot, random, 4, 1);
    }
}