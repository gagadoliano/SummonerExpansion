package summonerexpansion.codes.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.forest.ForestBiome;
import net.bytebuddy.asm.Advice;

import java.util.function.Predicate;

@ModMethodPatch(target = ForestBiome.class, name = "getFishingLootTable", arguments = {FishingSpot.class})
public class FishingLootForestPatch
{
    @Advice.OnMethodExit
    static void onExit(@Advice.Argument(0) FishingSpot Spot, @Advice.Return(readOnly = false) FishingLootTable Loot)
    {
        if (Spot.tile.level.isBasicCaveLevel())
        {
            Loot = new FishingLootTable()
                    .addAll(Biome.defaultCaveFish)
                    .addWater(50, "cavespidergland")
                    .addWater(20, "brokencoppertool")
                    .addWater(20, "brokenirontool")
                    .addWater(1, "shadowcreature")
                    .add(50, LavaTile(), "shadowcreature")
                    .add(1, LavaTile(), "dragonegg")
                    .add(100, HoneyTile(), "honey")
                    .add(100, HoneyTile(), "fossilhoney")
                    .add(1, HoneyTile(), "beehivechest");
        }
        else if (Spot.tile.level.isDeepCaveLevel())
        {
            Loot = new FishingLootTable()
                    .addAll(Biome.defaultCaveFish)
                    .addWater(50, "bone")
                    .add(100, LavaTile(), "shadowcreature")
                    .add(50, LavaTile(), "phoenixfeather")
                    .add(50, LavaTile(), "livingash")
                    .add(10, LavaTile(), "firestone")
                    .add(100, HoneyTile(), "honey")
                    .add(100, HoneyTile(), "fossilhoney")
                    .add(1, HoneyTile(), "beehivechest");
        }
        else
        {
            Loot = new FishingLootTable()
                    .addAll(ForestBiome.forestSurfaceFish)
                    .add(1, LavaTile(), "shadowcreature")
                    .add(100, HoneyTile(), "honey")
                    .add(100, HoneyTile(), "fossilhoney")
                    .add(1, HoneyTile(), "beehivechest");
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