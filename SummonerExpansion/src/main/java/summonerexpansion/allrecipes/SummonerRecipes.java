package summonerexpansion.allrecipes;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.codes.registry.SummonerTechs.*;

public class SummonerRecipes
{
    public static void registerSummonRecipes()
    {
        SummonerRecipes.registerSummonObjectRecipes();
        SummonerRecipes.registerSummonBannerRecipes();
        SummonerRecipes.registerSummonTilesRecipes();
        SummonerRecipes.registerSummonWallRecipes();
        SummonerRecipes.registerSummonMaterialRecipes();
        SummonerRecipes.registerSummonBossRecipes();
        SummonerRecipes.registerSummonStatRecipes();
        SummonerRecipes.registerSummonFoodRecipes();
        SummonerRecipes.registerSummonConverterRecipes();
    }

    public static void registerSummonObjectRecipes()
    {
        // Crafting
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
                "demonicsummoningtableduo",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("clay", 10),
                        new Ingredient("healthpotion", 2),
                        new Ingredient("demonicbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "tungstensummoningtableduo",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("clay", 10),
                        new Ingredient("healthpotion", 2),
                        new Ingredient("tungstenbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "fallensummoningtableduo",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("clay", 10),
                        new Ingredient("healthpotion", 2),
                        new Ingredient("upgradeshard", 10)
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

        Recipes.registerModRecipe(new Recipe(
                "summoningbookshelf",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("anylog", 20),
                        new Ingredient("craftingguide", 1),
                        new Ingredient("book", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "arcanicconverter",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("arcanicmachinery", 5),
                        new Ingredient("electrifiedmana", 15),
                        new Ingredient("alchemyshard", 25)
                }
        ));

        // Dummy
        Recipes.registerModRecipe(new Recipe(
                "tanktrainingdummy",
                1,
                RecipeTechRegistry.CARPENTER,
                new Ingredient[]{
                        new Ingredient("trainingdummy", 1),
                        new Ingredient("ironbar", 100)
                }
        ));

        // Plants
        Recipes.registerModRecipe(new Recipe(
                "overgrowthornssapling",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("thorns", 50),
                        new Ingredient("thornspotion", 1),
                        new Ingredient("fertilizer", 5)
                }
        ));

        // Lights
        Recipes.registerModRecipe(new Recipe(
                "caveglowlamp",
                1,
                RecipeTechRegistry.TUNGSTEN_CARPENTER,
                new Ingredient[]{
                        new Ingredient("caveglow", 15),
                        new Ingredient("torch", 1)
                }
        ));

        // Statues
        Recipes.registerModRecipe(new Recipe(
                "apprendicestatue",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("anystone", 35),
                        new Ingredient("voidshard", 1)
                }
        ));
        Recipes.registerModRecipe(new Recipe(
                "ascendedwizardstatue",
                1,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("anystone", 75),
                        new Ingredient("ascendedshard", 1)
                }
        ));

        // Furniture
        Recipes.registerModRecipe(new Recipe(
                "arcanicdisplay",
                1,
                RecipeTechRegistry.FALLEN_CARPENTER,
                new Ingredient[]{
                        new Ingredient("anystone", 10),
                        new Ingredient("electrifiedmana", 1)
                }
        ));
    }

    public static void registerSummonBannerRecipes()
    {
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
    }

    public static void registerSummonTilesRecipes()
    {
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
                "empowerednewemerald",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("emerald", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empowerednewsapphire",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("sapphire", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empowerednewruby",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ruby", 10),
                        new Ingredient("deepstone", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "empoweredtopaz",
                10,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("topaz", 10),
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
    }

    public static void registerSummonWallRecipes()
    {
        Recipes.registerModRecipe(new Recipe(
                "empoweredrubyrock",
                100,
                RecipeTechRegistry.FALLEN_LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("ruby", 10),
                        new Ingredient("deepstone", 100)
                }
        ));
    }

    public static void registerSummonMaterialRecipes()
    {
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

        Recipes.registerModRecipe(new Recipe(
                "myceliumworm",
                1,
                RecipeTechRegistry.CAVEGLOW_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("myceliumore", 1),
                        new Ingredient("wormbait", 5)
                }
        ));
    }

    public static void registerSummonBossRecipes()
    {
        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorportal",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 5),
                        new Ingredient("mysteriousportal", 1)
                }
        ));
    }

    public static void registerSummonStatRecipes()
    {
        Recipes.registerModRecipe(new Recipe(
                "fusedessences",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("shadowessence", 5),
                        new Ingredient("cryoessence", 5),
                        new Ingredient("bioessence", 5),
                        new Ingredient("primordialessence", 5),
                        new Ingredient("slimeessence", 5),
                        new Ingredient("bloodessence", 5),
                        new Ingredient("spideressence", 5)
                }
        ));
    }

    public static void registerSummonFoodRecipes()
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
    }

    public static void registerSummonConverterRecipes()
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
                "magicteapot",
                1,
                SUMMONCONVERTER,
                new Ingredient[]{
                        new Ingredient("teapot", 1)
                }
        ));

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
    }
}