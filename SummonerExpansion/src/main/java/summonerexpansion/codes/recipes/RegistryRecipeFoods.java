package summonerexpansion.codes.recipes;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

public class RegistryRecipeFoods
{
    public static void registerRecipes()
    {
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

        Recipes.registerModRecipe(new Recipe(
                "cookedworm",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("wormbait", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "cookedswampworm",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("swamplarva", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "orcasugar",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("tinyorca", 1),
                        new Ingredient("sugar", 3)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "rayjam",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("inspectorclouseau", 1),
                        new Ingredient("sugar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "livingsharksoup",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("sharkscales", 4),
                        new Ingredient("sharktooth", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "fullpicnicbasket",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("bread", 1),
                        new Ingredient("cabbage", 2),
                        new Ingredient("apple", 4),
                        new Ingredient("blueberry", 6)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "enchantedgoblet",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("voidshard", 1),
                        new Ingredient("goldchalice", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "runicmeal",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("runicfish", 1),
                        new Ingredient("carrot", 2),
                        new Ingredient("potato", 3),
                        new Ingredient("cabbage", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "waterzombiesoup",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("waterzombie", 1),
                        new Ingredient("spoiledfood", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "icecube",
                1,
                RecipeTechRegistry.COOKING_POT,
                new Ingredient[]{
                        new Ingredient("snowstone", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "caveglowjam",
                1,
                RecipeTechRegistry.COOKING_STATION,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("caveglow", 2),
                        new Ingredient("sugar", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "pigbanquet",
                1,
                RecipeTechRegistry.COOKING_STATION,
                new Ingredient[]{
                        new Ingredient("rawpork", 12)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spicycoffee",
                1,
                RecipeTechRegistry.COOKING_STATION,
                new Ingredient[]{
                        new Ingredient("groundcoffee", 6),
                        new Ingredient("chilipepper", 2),
                        new Ingredient("milk", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "dragondonut",
                1,
                RecipeTechRegistry.COOKING_STATION,
                new Ingredient[]{
                        new Ingredient("primordialessence", 2),
                        new Ingredient("donut", 1)
                }
        ));
    }
}