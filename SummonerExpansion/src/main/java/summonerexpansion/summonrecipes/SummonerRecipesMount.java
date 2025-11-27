package summonerexpansion.summonrecipes;

import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.codes.summonregistry.SummonerTechs.*;

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

        Recipes.registerModRecipe(new Recipe(
                "minivultureegg",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("vulturemask", 1),
                        new Ingredient("ancientfeather", 1),
                        new Ingredient("egg", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "cavelingminecart",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("minecart", 1),
                        new Ingredient("stone", 250),
                        new Ingredient("itemattractor", 1)
                }
        ));
    }
}
