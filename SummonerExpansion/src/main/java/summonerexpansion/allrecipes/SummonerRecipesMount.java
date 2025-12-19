package summonerexpansion.allrecipes;

import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.codes.registry.SummonerTechs.*;

public class SummonerRecipesMount
{
    public static void registerSummonRecipes()
    {
        // T1
        Recipes.registerModRecipe(new Recipe(
                "zombiearrow",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("stonearrow", 100),
                        new Ingredient("spoiledfood", 5),
                        new Ingredient("brokencoppertool", 1),
                        new Ingredient("brokenirontool", 1)
                }
        ));

        // T2
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

        // T3
        Recipes.registerModRecipe(new Recipe(
                "bouncingbone",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("bone", 125),
                        new Ingredient("ectoplasm", 15),
                        new Ingredient("lifequartz", 25)
                }
        ));

        // T4
        Recipes.registerModRecipe(new Recipe(
                "cryptfangs",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("bloodgrimoire", 1),
                        new Ingredient("bloodclaw", 1),
                        new Ingredient("thecrimsonsky", 1),
                        new Ingredient("bloodstonering", 1)
                }
        ));
    }
}