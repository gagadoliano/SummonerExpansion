package summonerexpansion.summonrecipes;

import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.codes.summonregistry.SummonerTechs.*;

public class SummonerRecipesTrinket
{
    public static void registerSummonRecipes()
    {
        // Summon table
        Recipes.registerModRecipe(new Recipe(
                "mesmercharm",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("mesmertablet", 1),
                        new Ingredient("zephyrcharm", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "cactusemblem",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("cactussapling", 25),
                        new Ingredient("thornspotion", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "demonicpolarclaw",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("polarclaw", 1),
                        new Ingredient("demonclaw", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frozenassassinscowl",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("assassinscowl", 1),
                        new Ingredient("frozenwave", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "essenceofcompanionship",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("companionlocket", 1),
                        new Ingredient("essenceofprolonging", 1),
                        new Ingredient("essenceofperspective", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "inducingsatchel",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("explorersatchel", 1),
                        new Ingredient("inducingamulet", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorcape",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("vampiresgift", 1),
                        new Ingredient("purehorror", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bonepile",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("bonehilt", 1),
                        new Ingredient("skull", 1),
                        new Ingredient("ectoplasm", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "hystericalmirror",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("scryingmirror", 1),
                        new Ingredient("hysteriatablet", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spelltablet",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("spellstone", 1),
                        new Ingredient("hysteriatablet", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "challengerarmorpiece",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("manica", 1),
                        new Ingredient("claygauntlet", 1),
                        new Ingredient("challengerspauldron", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summonergambit",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("summonfoci", 1),
                        new Ingredient("foolsgambit", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "mesmersatchel",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("mesmercharm", 1),
                        new Ingredient("inducingsatchel", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "calmminerslantern",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("calmingminersbouquet", 1),
                        new Ingredient("minersprosthetic", 1),
                        new Ingredient("diggingclaw", 1),
                        new Ingredient("toolbox", 1),
                        new Ingredient("willowisplantern", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "balancedsummonerboard",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("balancedfrostfirefoci", 1),
                        new Ingredient("spiritboard", 1),
                        new Ingredient("summonersbestiary", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "transplantedheart",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("clockworkheart", 1),
                        new Ingredient("frozensoul", 1),
                        new Ingredient("lifependant", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "scryingmagicianscard",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("hystericalmirror", 1),
                        new Ingredient("spelltablet", 1),
                        new Ingredient("scryingcards", 1),
                        new Ingredient("forbiddenspellbook", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frenzystonering",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("bloodstonering", 1),
                        new Ingredient("frenzyorb", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frenzyhorrorcape",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("frenzystonering", 1),
                        new Ingredient("shadowhorrorcape", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "necroticclaw",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("demonicpolarclaw", 1),
                        new Ingredient("bonepile", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "necromancerarmor",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("frozenassassinscowl", 1),
                        new Ingredient("frenzyhorrorcape", 1),
                        new Ingredient("necroticclaw", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "littleangel",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("summonergambit", 1),
                        new Ingredient("spiritgreaves", 1),
                        new Ingredient("secondwindcharm", 1),
                        new Ingredient("blinkscepter", 1),
                        new Ingredient("ancientrelics", 1)
                }
        ));
    }
}
