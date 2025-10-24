package summonerexpansion.summonothers;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.summonothers.SummonerTechs.*;

public class SummonerRecipesMount
{
    public static void registerSummonRecipes()
    {
        Recipes.registerModRecipe(new Recipe(
                "magiccheese",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("cheese", 1),
                        new Ingredient("luckycape", 1),
                        new Ingredient("speedpotion", 5),
                        new Ingredient("voidshard", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "chieftainhat",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("brutesbattleaxe", 1),
                        new Ingredient("runeboundbackbones", 1),
                        new Ingredient("resistancepotion", 5)
                }
        ));
    }
}
