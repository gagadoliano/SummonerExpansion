package summonerexpansion;

import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.swamp.SwampBiome;
import summonerexpansion.codes.registry.*;
import necesse.engine.modLoader.annotations.ModEntry;
import summonerexpansion.allrecipes.*;

@ModEntry
public class SummonerExpansion
{
    public void preInit()
    {
    }

    public void init()
    {
        // Registry
        SummonerEnchantments.registerSummonEnchantments();
        SummonerChallenges.registerSummonChallenges();
        SummonerProjectiles.registerSummonProjs();
        SummonerSettlers.registerSummonSettlers();
        SummonerMapIcon.registerSummonMapIcons();
        SummonerPresets.registerSummonPresets();
        SummonerObjects.registerSummonObjects();
        SummonerWeapons.registerSummonWeapons();
        SummonerEquips.registerSummonEquips();
        SummonerEvents.registerSummonEvents();
        SummonerBuffs.registerSummonDebuffs();
        SummonerBuffs.registerSummonBuffs();
        SummonerItems.registerSummonItems();
        SummonerTiles.registerSummonTiles();
        SummonerTechs.registerSummonTechs();
        SummonerPerks.registerSummonPerks();
        SummonerMobs.registerSummonMobs();
        SummonerFoes.registerSummonFoes();
    }

    public void initResources()
    {
        SummonerTextures.initResources();
    }

    public void postInit()
    {
        SummonerRecipes.registerSummonRecipes();
        SummonerRecipesPotion.registerSummonRecipes();
        SummonerJournal.registerSummonJournal();
        SummonerRecipesWeapon.registerAllSummonWeapons();
        SummonerRecipesArmor.registerSummonRecipes();
        SummonerRecipesTrinket.registerSummonRecipes();
        SummonerRecipesMount.registerSummonRecipes();
        SummonerLoot.registerSummonLoot();

        Biome.defaultDeepCaveMobs
                .add(10, "horrorspiritmob");

        SwampBiome.surfaceMobs
                .add(1, "woodmob");
    }
}