package summonerexpansion.codes.recipes;

import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.codes.registries.RegistryTechs.SUMMONCONVERTER;

public class RegistryRecipeConverter
{
    public static void registerRecipes()
    {
        Recipes.registerModRecipe(new Recipe(
                "upgradeshard",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("voidshard", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "voidshard",
                5,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("upgradeshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ectoplasm",
                5,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("purehorror", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "purehorror",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("ectoplasm", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "electrifiedmana",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("ravenfeather", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ravenfeather",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("electrifiedmana", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "cavespidergland",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("silk", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "silk",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("cavespidergland", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "mimicchest",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("storagebox", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "nightsteelbar",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("spideritebar", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spideritebar",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("nightsteelbar", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "swampslime",
                5,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("slimeessence", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "livingash",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("phoenixfeather", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "phoenixfeather",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("livingash", 2)
                }
        ));

        //Armor

        Recipes.registerModRecipe(new Recipe(
                "battlechefboots",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("battlechefhat", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "battlechefchestplate",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("battlechefboots", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "battlechefhat",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("battlechefchestplate", 1)
                }
        ));

        //Trinkets

        Recipes.registerModRecipe(new Recipe(
                "essenceofperspective",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("essenceofprolonging", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "essenceofprolonging",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("essenceofperspective", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ancientfeather",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("inefficientfeather", 1)
                }
        ));

        //Traps

        Recipes.registerModRecipe(new Recipe(
                "glyphtrapbounce",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("glyphtrapreversedamage", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "glyphtrapchicken",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("glyphtrapbounce", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "glyphtrapreversedamage",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("glyphtrapchicken", 1)
                }
        ));

        //Potions

        Recipes.registerModRecipe(new Recipe(
                "healthpotion",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("manapotion", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "manapotion",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("healthpotion", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "greaterhealthpotion",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("greatermanapotion", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "greatermanapotion",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("greaterhealthpotion", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "superiorhealthpotion",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("superiormanapotion", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "superiormanapotion",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("superiorhealthpotion", 1)
                }
        ));

        //Upgrades

        Recipes.registerModRecipe(new Recipe(
                "magicteapot",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("teapot", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sailorsummonhat",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("sailorhat", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sailorsummonshirt",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("sailorshirt", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sailorsummonshoes",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("sailorshoes", 1)
                }
        ));
    }
}