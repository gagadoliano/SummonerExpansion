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
        new SummonerModifiers();
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
        SummonerPackets.registerSummonPackets();
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
        SummonerMissions.registerSummonMissions();
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
        SummonerRecipesArmor.registerSummonArmorRecipes();
        SummonerRecipesTrinket.registerSummonRecipes();
        SummonerRecipesMount.registerSummonRecipes();
        SummonerLoot.registerSummonLoot();

        Biome.defaultDeepCaveMobs
                .add(10, "horrorspiritmob");

        SwampBiome.surfaceMobs
                .addLimited(1, "woodmob", 1, 50*50);
    }
}