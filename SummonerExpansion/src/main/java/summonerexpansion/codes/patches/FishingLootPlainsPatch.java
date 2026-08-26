package summonerexpansion.codes.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.plains.PlainsBiome;
import net.bytebuddy.asm.Advice;

import java.util.function.Predicate;

@ModMethodPatch(target = PlainsBiome.class, name = "getFishingLootTable", arguments = {FishingSpot.class})
public class FishingLootPlainsPatch
{
    @Advice.OnMethodExit
    static void onExit(@Advice.Argument(0) FishingSpot Spot, @Advice.Return(readOnly = false) FishingLootTable Loot)
    {
        if (Spot.tile.level.isBasicCaveLevel())
        {
            Loot = new FishingLootTable()
                    .addAll(Biome.defaultCaveFish)
                    .addWater(40, "runicfish")
                    .addWater(10, "essenceofprolonging")
                    .addWater(10, "essenceofperspective")
                    .add(100, LavaTile(), "runicfish");
        }
        else if (Spot.tile.level.isDeepCaveLevel())
        {
            Loot = new FishingLootTable()
                    .addAll(Biome.defaultCaveFish)
                    .addWater(10, "amber")
                    .addWater(1, "beehivemap")
                    .add(100, LavaTile(), "runicfish");
        }
    }

    public static Predicate<FishingSpot> LavaTile()
    {
        return Target -> Target.tile.tile.getStringID().equals("lavatile");
    }
}