package summonerexpansion.summonothers;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.summonothers.SummonerTechs.*;

public class SummonerRecipesArmor
{
    public static void registerSummonRecipes()
    {
        // Summon table
        Recipes.registerModRecipe(new Recipe(
                "leathersummonerhood",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("leather", 10),
                        new Ingredient("beef", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "leathershirt",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("leather", 14),
                        new Ingredient("beef", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "leatherboots",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("leather", 6),
                        new Ingredient("beef", 3)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "copperminerhat",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("copperbar", 10),
                        new Ingredient("torch", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "copperchestplate",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("copperbar", 12)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "copperboots",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("copperbar", 6)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "redspiderhelmet",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("spiderhelmet", 1),
                        new Ingredient("redflowerpatch", 12),
                        new Ingredient("firemone", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "redspiderchestplate",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("spiderchestplate", 1),
                        new Ingredient("redflowerpatch", 15),
                        new Ingredient("firemone", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "redspiderboots",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("spiderboots", 1),
                        new Ingredient("redflowerpatch", 10),
                        new Ingredient("firemone", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frostcrown",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("frostshard", 5),
                        new Ingredient("goldcrown", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frostchestplate",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("frostshard", 6),
                        new Ingredient("goldchestplate", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "frostboots",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("frostshard", 4),
                        new Ingredient("goldboots", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bloodplatemask",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("bloodplatecowl", 1),
                        new Ingredient("batwing", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bloodplatechestplate",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("demonicbar", 5),
                        new Ingredient("batwing", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bloodplateboots",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("demonicbar", 5),
                        new Ingredient("batwing", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summonplaguemask",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("wool", 10),
                        new Ingredient("ivybar", 2),
                        new Ingredient("voidshard", 1),
                        new Ingredient("glassbottle", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summonplaguerobe",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("wool", 10),
                        new Ingredient("ivybar", 2),
                        new Ingredient("voidshard", 1),
                        new Ingredient("glassbottle", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "summonplagueboots",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("wool", 10),
                        new Ingredient("ivybar", 2),
                        new Ingredient("voidshard", 1),
                        new Ingredient("glassbottle", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhelmet",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ectoplasm", 12),
                        new Ingredient("obsidian", 10),
                        new Ingredient("bone", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowmantle",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ectoplasm", 8),
                        new Ingredient("bone", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ectoplasm", 10),
                        new Ingredient("bone", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorhood",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("wool", 12),
                        new Ingredient("leather", 10),
                        new Ingredient("purehorror", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhorrormantle",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("wool", 12),
                        new Ingredient("leather", 10),
                        new Ingredient("purehorror", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("wool", 12),
                        new Ingredient("leather", 10),
                        new Ingredient("purehorror", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spiderbridehelmet",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("silk", 10),
                        new Ingredient("myceliumbar", 10),
                        new Ingredient("cavespidergland", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spiderbridechest",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("silk", 15),
                        new Ingredient("myceliumbar", 10),
                        new Ingredient("cavespidergland", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spiderbrideboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("silk", 10),
                        new Ingredient("myceliumbar", 10),
                        new Ingredient("cavespidergland", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "agedsummonerhelmet",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("agedchampionhelmet", 1),
                        new Ingredient("agedchampionsword", 1),
                        new Ingredient("agedchampionshield", 1)
                }
        ));

        // Anvil
        Recipes.registerModRecipe(new Recipe(
                "copperminerhat",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("copperbar", 15),
                        new Ingredient("torch", 25)
                }
        ).showBefore("copperhelmet"));

        Recipes.registerModRecipe(new Recipe(
                "leathersummonerhood",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("leather", 15),
                        new Ingredient("beef", 2)
                }
        ).showAfter("leatherhood"));

        Recipes.registerModRecipe(new Recipe(
                "bloodplatemask",
                1,
                RecipeTechRegistry.DEMONIC_ANVIL,
                new Ingredient[]{
                        new Ingredient("demonicbar", 8),
                        new Ingredient("batwing", 12)
                }
        ).showAfter("bloodplatecowl"));

    }
}
