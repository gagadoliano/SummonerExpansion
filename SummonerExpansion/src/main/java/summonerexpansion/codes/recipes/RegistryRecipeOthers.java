package summonerexpansion.codes.recipes;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.codes.registries.RegistryTechs.*;

public class RegistryRecipeOthers
{
    public static void registerRecipes()
    {
        RegistryRecipeOthers.registerMaterials();
        RegistryRecipeOthers.registerMaps();
        RegistryRecipeOthers.registerFishing();
        RegistryRecipeOthers.registerStats();
        RegistryRecipeOthers.registerBanners();
        RegistryRecipeOthers.registerBoss();
    }

    public static void registerMaterials()
    {
        Recipes.registerModRecipe(new Recipe(
                "book",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("leather", 2),
                        new Ingredient("mapfragment", 5),
                        new Ingredient("voidshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "titaniumbar",
                1,
                RecipeTechRegistry.FORGE,
                new Ingredient[]{
                        new Ingredient("titaniumore", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "runestone",
                5,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("runicfish", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "purehorror",
                5,
                RecipeTechRegistry.TUNGSTEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("shadowcreature", 1)
                }
        ));
    }

    public static void registerMaps()
    {
        Recipes.registerModRecipe(new Recipe(
                "druidhousemap",
                1,
                RecipeTechRegistry.TUNGSTEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("mapfragment", 4),
                        new Ingredient("ancientlog", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "horrorcultmap",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("mapfragment", 4),
                        new Ingredient("purehorror", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "titaniumnodemap",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("mapfragment", 4),
                        new Ingredient("titaniumbar", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "beehivemap",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("mapfragment", 4),
                        new Ingredient("honey", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ancientforestmap",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("mapfragment", 4),
                        new Ingredient("ancientlog", 5)
                }
        ));
    }

    public static void registerFishing()
    {
        Recipes.registerModRecipe(new Recipe(
                "ancientwoodfishingrod",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ancientlog", 10),
                        new Ingredient("ironbar", 2),
                        new Ingredient("ancienttreesapling", 1)
                }
        ).showAfter("woodfishingrod"));

        Recipes.registerModRecipe(new Recipe(
                "myceliumworm",
                1,
                RecipeTechRegistry.CAVEGLOW_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("myceliumore", 1),
                        new Ingredient("wormbait", 5)
                }
        ));
    }

    public static void registerStats()
    {
        Recipes.registerModRecipe(new Recipe(
                "fusedessences",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ascendedshard", 5),
                        new Ingredient("shadowessence", 5),
                        new Ingredient("cryoessence", 5),
                        new Ingredient("bioessence", 5),
                        new Ingredient("primordialessence", 5),
                        new Ingredient("slimeessence", 5),
                        new Ingredient("bloodessence", 5),
                        new Ingredient("spideressence", 5)
                }
        ));
    }

    public static void registerBanners()
    {
        Recipes.registerModRecipe(new Recipe(
                "bannerofstamina",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("mackerel", 10)

                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofmana",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("manapotion", 10)

                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofdashing",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("zephyrcharm", 1)

                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofbouncing",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("swampgrassseed", 10)
                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofessence",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("essenceofperspective", 1)
                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofpicking",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("honey", 10)
                }
        ).showAfter("bannerofsummonspeed"));

        Recipes.registerModRecipe(new Recipe(
                "bannerofresilience",
                1,
                RecipeTechRegistry.DEMONIC_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wool", 30),
                        new Ingredient("friedegg", 10)
                }
        ).showAfter("bannerofsummonspeed"));
    }

    public static void registerBoss()
    {
        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorportal",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 5),
                        new Ingredient("mysteriousportal", 1)
                }
        ));
    }
}