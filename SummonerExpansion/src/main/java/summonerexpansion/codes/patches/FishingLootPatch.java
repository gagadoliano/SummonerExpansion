package summonerexpansion.codes.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.registries.BiomeRegistry;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import net.bytebuddy.asm.Advice;

import java.util.function.Predicate;

@ModMethodPatch(target = Biome.class, name = "getFishingLootTable", arguments = {FishingSpot.class})
public class FishingLootPatch
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This Biome Biome, @Advice.Argument(0) FishingSpot Spot, @Advice.Return(readOnly = false) FishingLootTable Loot)
    {
        if (Spot.tile.level.getIdentifier().isSurface())
        {
            Loot = (new FishingLootTable()).add(50, HoneyTile(), "fossilhoney");
        }
        else if (Spot.tile.level.getIdentifier().isCave())
        {
            Loot = (new FishingLootTable()).add(80, HoneyTile(), "fossilhoney");
        }
        else if (Spot.tile.level.getIdentifier().isCave() && Spot.tile.level.baseBiome == BiomeRegistry.PLAINS)
        {
            Loot = (new FishingLootTable()).addWater(50, "runicfish").add(10, LavaTile(), "runicfish");
        }
        else if (Spot.tile.level.getIdentifier().isCave() && Spot.tile.level.baseBiome == BiomeRegistry.SWAMP)
        {
            Loot = (new FishingLootTable()).addWater(5, "sunkenchest").addWater(1, "stuffedswampslug").add(10, LavaTile(), "titaniumore");
        }
        else if (Spot.tile.level.getIdentifier().isDeepCave())
        {
            Loot = (new FishingLootTable()).addWater(4, "shadowcreature").add(10, LavaTile(), "shadowcreature");
        }
    }

    public static Predicate<FishingSpot> LavaTile()
    {
        return Target -> Target.tile.tile.getStringID().equals("lavatile");
    }

    public static Predicate<FishingSpot> HoneyTile()
    {
        return Target -> Target.tile.tile.getStringID().equals("liquidhoneytile");
    }
}