package summonerexpansion.codes.recipes;

import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.codes.registries.RegistryTechs.*;

public class RegistryRecipeMounts
{
    public static void registerRecipes()
    {
        RegistryRecipeMounts.registerTier1();
        RegistryRecipeMounts.registerTier2();
        RegistryRecipeMounts.registerTier3();
        RegistryRecipeMounts.registerTier4();
    }

    public static void registerTier1()
    {
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

        Recipes.registerModRecipe(new Recipe(
                "sharkdivingmask",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("fins", 1),
                        new Ingredient("sharkmask", 1),
                        new Ingredient("sharkcostumeshirt", 1),
                        new Ingredient("sharkcostumeboots", 1)
                }
        ));
    }

    public static void registerTier2()
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
                "frozendwarfbeard",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("icejavelin", 100),
                        new Ingredient("frostshard", 25),
                        new Ingredient("frozenheart", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "cavespidergoo",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("cavespidergland", 50),
                        new Ingredient("spiderclaw", 2),
                        new Ingredient("royalegg", 1)
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

    public static void registerTier3()
    {
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

        Recipes.registerModRecipe(new Recipe(
                "snowwolftail",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("leather", 25),
                        new Ingredient("glacialshard", 15),
                        new Ingredient("bone", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "forestspectorhands",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("spiriturn", 1),
                        new Ingredient("amber", 25),
                        new Ingredient("basalt", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "swampdwellerbow",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("druidsgreatbow", 1),
                        new Ingredient("decayingleaf", 1),
                        new Ingredient("reeds", 25)
                }
        ));
    }

    public static void registerTier4()
    {
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