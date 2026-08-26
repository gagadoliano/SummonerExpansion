package summonerexpansion.codes.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.swamp.SwampBiome;
import net.bytebuddy.asm.Advice;

import java.util.function.Predicate;

@ModMethodPatch(target = SwampBiome.class, name = "getFishingLootTable", arguments = {FishingSpot.class})
public class FishingLootSwampPatch
{
    @Advice.OnMethodExit
    static void onExit(@Advice.Argument(0) FishingSpot Spot, @Advice.Return(readOnly = false) FishingLootTable Loot)
    {
        if (Spot.tile.level.isBasicCaveLevel())
        {
            Loot = new FishingLootTable()
                    .addAll(Biome.defaultCaveFish)
                    .addWater(80, "swampstone")
                    .addWater(60, "ivyore")
                    .addWater(40, "frogleg")
                    .addWater(20, "swampsludge")
                    .addWater(10, "spikedfossil")
                    .addWater(5, "sunkenchest")
                    .addWater(1, "stuffedswampslug")
                    .add(100, LavaTile(), "titaniumore");
        }
        else if (Spot.tile.level.isDeepCaveLevel())
        {
            Loot = new FishingLootTable()
                    .addAll(Biome.defaultCaveFish)
                    .addWater(50, "sunkenchest")
                    .addWater(10, "stuffedswampslug")
                    .add(100, LavaTile(), "titaniumore");
        }
    }

    public static Predicate<FishingSpot> LavaTile()
    {
        return Target -> Target.tile.tile.getStringID().equals("lavatile");
    }
}