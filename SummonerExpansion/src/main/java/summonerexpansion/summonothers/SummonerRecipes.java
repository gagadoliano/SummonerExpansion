package summonerexpansion.summonothers;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.summonothers.SummonerTechs.*;

public class SummonerRecipes
{
    public static void registerSummonRecipes()
    {
        // Banners
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

        // Materials
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

        // Objects
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

        // Plants
        Recipes.registerModRecipe(new Recipe(
                "overgrowthornssapling",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("thorns", 100),
                        new Ingredient("thornspotion", 1),
                        new Ingredient("fertilizer", 5)
                }
        ));

        //Tiles
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

        // Walls
        Recipes.registerModRecipe(new Recipe(
                "empoweredrubyrock",
                100,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("ruby", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        // Boss summon
        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorportal",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 5),
                        new Ingredient("mysteriousportal", 1)
                }
        ));

        // Stat increase
        Recipes.registerModRecipe(new Recipe(
                "fusedessences",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("shadowessence", 5),
                        new Ingredient("cryoessence", 5),
                        new Ingredient("bioessence", 5),
                        new Ingredient("primordialessence", 5)
                }
        ));

        // Food
        Recipes.registerModRecipe(new Recipe(
                "berrytrio",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("blueberry", 1),
                        new Ingredient("blackberry", 1),
                        new Ingredient("raspberry", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "rottenbread",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("bread", 1),
                        new Ingredient("spoiledfood", 4)
                }
        ));
    }
}