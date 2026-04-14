package summonerexpansion;

import necesse.engine.modLoader.annotations.ModEntry;
import necesse.gfx.gameTexture.GameTexture;
import summonerexpansion.codes.registries.*;
import summonerexpansion.codes.recipes.*;

import java.util.Map;

@ModEntry
public class SummonerExpansion
{
    public void preInit()
    {
        new RegistrySummonModifiers();
    }

    public void init()
    {
        RegistryTiles.registerTiles();
        RegistryObjects.registerObjects();
        RegistryEntityObjects.registerObjects();
        RegistryWalls.registerSummonObjects();
        RegistryBuffs.registerBannerBuffs();
        RegistryBuffs.registerWeaponBuffs();
        RegistryDebuffs.registerWeaponDebuffs();
        RegistryDebuffs.registerGenericDebuffs();
        RegistryTechs.registerTechs();
        RegistryItems.registerItems();
        RegistryWeapons.registerItems();
        RegistryArmors.registerItems();
        RegistryTrinkets.registerItems();
        RegistryMounts.registerItems();
        RegistryEnchantments.registerSummonEnchantments();
        RegistryMobs.registerMobs();
        RegistryMinions.registerMinions();
        RegistrySettlers.registerSettlers();
        RegistryProjectiles.registerProjs();
        RegistryEvents.registerEvents();
        RegistryPerks.registerPerks();
        RegistryChallenges.registerChallenges();
        RegistryMapIcon.registerMapIcons();
        RegistryPresets.registerPresets();
        RegistryPackets.registerPackets();
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