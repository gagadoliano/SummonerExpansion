package summonerexpansion.codes.recipes;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.codes.registries.RegistryTechs.*;

public class RegistryRecipeObjects
{
    public static void registerRecipes()
    {
        RegistryRecipeObjects.registerTier1();
        RegistryRecipeObjects.registerTier2();
        RegistryRecipeObjects.registerTier3();
        RegistryRecipeObjects.registerTier4();
    }

    public static void registerTier1()
    {
        Recipes.registerModRecipe(new Recipe(
                "summoningtableduo",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("anylog", 35),
                        new Ingredient("clay", 15),
                        new Ingredient("healthpotion", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summoningtableduo",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("clay", 10),
                        new Ingredient("healthpotion", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summoningbookshelf",
                1,
                RecipeTechRegistry.CARPENTER,
                new Ingredient[]{
                        new Ingredient("anylog", 40),
                        new Ingredient("craftingguide", 1),
                        new Ingredient("book", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "tanktrainingdummy",
                1,
                RecipeTechRegistry.CARPENTER,
                new Ingredient[]{
                        new Ingredient("trainingdummy", 1),
                        new Ingredient("ironbar", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "apprendicestatue",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("anystone", 35),
                        new Ingredient("voidshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "beehivechest",
                1,
                RecipeTechRegistry.CARPENTER,
                new Ingredient[]{
                        new Ingredient("honey", 10),
                        new Ingredient("queenbee", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ancientwoodfence",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ancientlog", 2)
                }
        ).showAfter("banditfencegate"));

        Recipes.registerModRecipe(new Recipe(
                "ancientwoodfencegate",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ancientlog", 4)
                }
        ).showAfter("ancientwoodfence"));

        Recipes.registerModRecipe(new Recipe(
                "beehivefloor",
                50,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("honey", 10),
                }
        ));
    }

    public static void registerTier2()
    {
        Recipes.registerModRecipe(new Recipe(
                "demonicsummoningtableduo",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("clay", 10),
                        new Ingredient("healthpotion", 2),
                        new Ingredient("demonicbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summoningbookshelf",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("craftingguide", 1),
                        new Ingredient("book", 4)
                }
        ));


    }

    public static void registerTier3()
    {
        Recipes.registerModRecipe(new Recipe(
                "tungstensummoningtableduo",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("clay", 10),
                        new Ingredient("healthpotion", 2),
                        new Ingredient("tungstenbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "overgrowthornssapling",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("thorns", 50),
                        new Ingredient("thornspotion", 1),
                        new Ingredient("fertilizer", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "caveglowlamp",
                1,
                RecipeTechRegistry.TUNGSTEN_CARPENTER,
                new Ingredient[]{
                        new Ingredient("caveglow", 15),
                        new Ingredient("torch", 1)
                }
        ));


    }

    public static void registerTier4()
    {
        Recipes.registerModRecipe(new Recipe(
                "fallensummoningtableduo",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("clay", 10),
                        new Ingredient("healthpotion", 2),
                        new Ingredient("upgradeshard", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "arcanicconverter",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("arcanicmachinery", 5),
                        new Ingredient("electrifiedmana", 15),
                        new Ingredient("alchemyshard", 25)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ascendedwizardstatue",
                1,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("anystone", 75),
                        new Ingredient("ascendedshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sandtemplemonkstatue",
                1,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("sandstone", 25),
                        new Ingredient("sandtemplemonkhead", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "arcanicdisplay",
                1,
                RecipeTechRegistry.FALLEN_CARPENTER,
                new Ingredient[]{
                        new Ingredient("anystone", 10),
                        new Ingredient("electrifiedmana", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "arcanicchest",
                1,
                RecipeTechRegistry.FALLEN_CARPENTER,
                new Ingredient[]{
                        new Ingredient("anystone", 20),
                        new Ingredient("electrifiedmana", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredamethyst",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("amethyst", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empowerednewemerald",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("emerald", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empowerednewsapphire",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("sapphire", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empowerednewruby",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ruby", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredtopaz",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("topaz", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredemerald",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("emerald", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredsapphire",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("sapphire", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredruby",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ruby", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredsapphirerock",
                100,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("sapphire", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredamethystrock",
                100,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("amethyst", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredemeraldrock",
                100,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("emerald", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredtopazrock",
                100,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("topaz", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredrubyrock",
                100,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("ruby", 10),
                        new Ingredient("deepstone", 100)
                }
        ));
    }
}