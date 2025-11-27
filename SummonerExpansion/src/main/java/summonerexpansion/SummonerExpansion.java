package summonerexpansion;

import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.swamp.SwampBiome;
import summonerexpansion.codes.summonregistry.*;
import necesse.engine.modLoader.annotations.ModEntry;
import summonerexpansion.summonrecipes.*;

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
        SummonerObjects.registerSummonObjects();
        SummonerWeapons.registerSummonWeapons();
        SummonerEquips.registerSummonEquips();
        SummonerEvents.registerSummonEvents();
        SummonerItems.registerSummonItems();
        SummonerTiles.registerSummonTiles();
        SummonerTechs.registerSummonTechs();
        SummonerBuffs.registerSummonBuffs();
        SummonerPerks.registerSummonPerks();
        SummonerMobs.registerSummonMobs();
        SummonerFoes.registerSummonFoes();
        SummonerPresets.registerSummonPresets();
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
        SummonerRecipesWeapon.registerSummonRecipesAnvil();
        SummonerRecipesWeapon.registerSummonRecipesBookcase();
        SummonerRecipesWeapon.registerSummonRecipesWorkstation();
        SummonerRecipesWeapon.registerSummonRecipesSummontable();
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