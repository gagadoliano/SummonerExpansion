package summonerexpansion;

import necesse.engine.modLoader.annotations.ModEntry;
import summonerexpansion.codes.registries.*;
import summonerexpansion.codes.recipes.*;

@ModEntry
public class SummonerExpansion
{
    public void preInit()
    {
        new RegistrySummonModifiers();
    }

    public void init()
    {
        RegistryPackets.registerPackets();
        RegistryEvents.registerEvents();
        RegistryPerks.registerPerks();
        RegistryBuffs.registerWeaponBuffs();
        RegistryBuffs.registerBannerBuffs();
        RegistryDebuffs.registerWeaponDebuffs();
        RegistryDebuffs.registerGenericDebuffs();
        RegistryEnchantments.registerSummonEnchantments();
        RegistryChallenges.registerChallenges();
        RegistryTechs.registerTechs();
        RegistrySettlers.registerSettlers();
        RegistryPresets.registerPresets();
        RegistryTiles.registerTiles();
        RegistryWalls.registerSummonObjects();
        RegistryObjects.registerObjects();
        RegistryEntityObjects.registerObjects();
        RegistryMapIcon.registerMapIcons();
        RegistryMobs.registerMobs();
        RegistryMinions.registerMinions();
        RegistryItems.registerItems();
        RegistryWeapons.registerItems();
        RegistryArmors.registerItems();
        RegistryTrinkets.registerItems();
        RegistryMounts.registerItems();
        RegistryProjectiles.registerProjs();
        RegistryMissions.registerMissions();
    }

    public void initResources()
    {
        RegistryMobTextures.initResources();
        RegistryMinionTextures.initResources();
        RegistryParticlesTextures.initResources();
    }

    public void postInit()
    {
        RegistrySpawn.registerSpawn();
        RegistryChestLoot.registerLoot();
        RegistryJournal.registerJournal();
        RegistryLootBag.registerLoot();
        RegistryDropLoot.registerLoot();
        RegistryFishLoot.registerLoot();
        RegistryRecipeWeapons.registerRecipes();
        RegistryRecipeArmors.registerRecipes();
        RegistryRecipeTrinkets.registerRecipes();
        RegistryRecipeMounts.registerRecipes();
        RegistryRecipeObjects.registerRecipes();
        RegistryRecipeConverter.registerRecipes();
        RegistryRecipeFoods.registerRecipes();
        RegistryRecipePotions.registerRecipes();
        RegistryRecipeOthers.registerRecipes();
    }
}