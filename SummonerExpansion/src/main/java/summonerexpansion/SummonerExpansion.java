package summonerexpansion;

import necesse.level.maps.biomes.Biome;
import summonerexpansion.summonobjects.*;
import summonerexpansion.summonothers.*;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;

@ModEntry
public class SummonerExpansion
{
    public void init()
    {
        // Registry
        SummonerEnchantments.registerSummonEnchantments();
        SummonerChallenges.registerSummonChallenges();
        SummonerProjectiles.registerSummonProjs();
        SummonerSettlers.registerSummonSettlers();
        SummonerJournal.registerSummonerJournal();
        SummonerObjects.registerSummonObjects();
        SummonerWeapons.registerSummonWeapons();
        SummonerEquips.registerSummonEquips();
        SummonerItems.registerSummonItems();
        SummonerTechs.registerSummonTechs();
        SummonerBuffs.registerSummonBuffs();
        SummonerMobs.registerSummonMobs();

        // Events
        LevelEventRegistry.registerEvent("snowmanexplosionlevelevent", SnowmanExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("mosquitobowevent", MosquitoBowEvent.class);

        // Duo Objects
        SummoningTableDuo.registerSummoningTable();
        DemonicSummoningTableDuo.registerDemonicSummoningTable();
        TungstenSummoningTableDuo.registerTungstenSummoningTable();
        FallenSummoningTableDuo.registerFallenSummoningTable();
    }

    public void initResources()
    {
        SummonerTextures.initResources();
    }

    public void postInit()
    {
        SummonerRecipes.registerSummonRecipes();
        SummonerLoot.registerSummonLoot();

        Biome.defaultDeepCaveMobs
                .add(10, "horrorspiritmob");
    }
}