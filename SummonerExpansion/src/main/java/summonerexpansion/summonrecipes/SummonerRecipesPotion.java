package summonerexpansion.summonrecipes;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

public class SummonerRecipesPotion
{
    public static void registerSummonRecipes()
    {
        Recipes.registerModRecipe(new Recipe(
                "minioncritchancepotion",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("blueberry", 5),
                        new Ingredient("thorns", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minioncritpotion",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("carp", 1),
                        new Ingredient("thorns", 6)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minionspeedpotion",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("sugar", 2),
                        new Ingredient("batwing", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minionrangepotion",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("carrot", 1),
                        new Ingredient("sunflower", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minioncloserangepotion",
                1,
                RecipeTechRegistry.CAVEGLOW_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("copperbar", 1),
                        new Ingredient("halffish", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minionfarmpotion",
                1,
                RecipeTechRegistry.CAVEGLOW_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("caveglow", 1),
                        new Ingredient("wheat", 5),
                        new Ingredient("cavespidergland", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "minionattackspeedpotion",
                1,
                RecipeTechRegistry.CAVEGLOW_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("glassbottle", 1),
                        new Ingredient("ectoplasm", 2),
                        new Ingredient("blackcoffee", 1)
                }
        ));
    }
}