package summonerexpansion.summonothers;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.summonothers.SummonerTechs.*;

public class SummonerRecipesWeapon
{
    public static void registerSummonRecipes()
    {
        // Workstation
        Recipes.registerModRecipe(new Recipe(
                "magictools",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("woodpickaxe", 1),
                        new Ingredient("woodaxe", 1),
                        new Ingredient("anysapling", 4)
                }
        ));

        // Anvil
        Recipes.registerModRecipe(new Recipe(
                "magiccopperlamp",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("copperbar", 2),
                        new Ingredient("oillantern", 1),
                        new Ingredient("torch", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "magicgoldlamp",
                1,
                RecipeTechRegistry.DEMONIC_ANVIL,
                new Ingredient[]{
                        new Ingredient("goldbar", 6),
                        new Ingredient("oillantern", 1),
                        new Ingredient("torch", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "magictungstenlamp",
                1,
                RecipeTechRegistry.TUNGSTEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("tungstenbar", 10),
                        new Ingredient("oillantern", 1),
                        new Ingredient("torch", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "magicdungeoncandelabra",
                1,
                RecipeTechRegistry.DEMONIC_ANVIL,
                new Ingredient[]{
                        new Ingredient("dungeoncandelabra", 1),
                        new Ingredient("magicstilts", 1),
                        new Ingredient("voidshard", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "magiccastlecandelabra",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("spidercastlecandelabra", 1),
                        new Ingredient("spideritearrow", 50)
                }
        ));

        // Bookcase
        Recipes.registerModRecipe(new Recipe(
                "bookbee",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("honeybee", 6),
                        new Ingredient("apiaryframe", 4),
                        new Ingredient("book", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bookmagma",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("fireresistancepotion", 5),
                        new Ingredient("torch", 100),
                        new Ingredient("book", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bookfrozen",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("icejavelin", 100),
                        new Ingredient("frostpiercer", 1),
                        new Ingredient("book", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bookrunic",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("runestone", 25),
                        new Ingredient("book", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bookmushroom",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("mushroom", 50),
                        new Ingredient("spoiledfood", 20),
                        new Ingredient("revivalpotion", 2),
                        new Ingredient("book", 1)
                }
        ));

        // Summon table weapons
        // T1
        Recipes.registerModRecipe(new Recipe(
                "magictools",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("woodpickaxe", 1),
                        new Ingredient("woodaxe", 1),
                        new Ingredient("anysapling", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "redspiderstaff",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("spiderstaff", 1),
                        new Ingredient("redflowerpatch", 10),
                        new Ingredient("firemone", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "royalhive",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("queenbee", 1),
                        new Ingredient("honey", 30)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bearhead",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("honey", 5),
                        new Ingredient("furfish", 15),
                        new Ingredient("grizzlycub", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "polarhead",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("polarclaw", 1),
                        new Ingredient("icefish", 10),
                        new Ingredient("wool", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "enchantedbrainonastick",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("brainonastick", 1),
                        new Ingredient("spoiledfood", 10),
                        new Ingredient("voidshard", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "cactusstaff",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("sandtile", 10),
                        new Ingredient("thorns", 25),
                        new Ingredient("cactussapling", 50)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "explosivesnowball",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("dynamitestick", 1),
                        new Ingredient("snowmantrainingdummy", 1),
                        new Ingredient("snowball", 100)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sunflowerstaff",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("woodstaff", 1),
                        new Ingredient("sunflower", 10),
                        new Ingredient("fertilizer", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "iceblossomstaff",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("woodstaff", 1),
                        new Ingredient("iceblossom", 10),
                        new Ingredient("fertilizer", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "firemonestaff",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("woodstaff", 1),
                        new Ingredient("firemone", 10),
                        new Ingredient("fertilizer", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "wormbucket",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("bucket", 1),
                        new Ingredient("wormbait", 35),
                        new Ingredient("mackerel", 2),
                        new Ingredient("herring", 2),
                        new Ingredient("salmon", 2),
                        new Ingredient("trout", 2),
                        new Ingredient("carp", 2)
                }
        ));

        // T2
        Recipes.registerModRecipe(new Recipe(
                "vampirewings",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("batwing", 10),
                        new Ingredient("healthregenpotion", 1),
                        new Ingredient("demonicbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "goldpitchfork",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("copperpitchfork", 1),
                        new Ingredient("rope", 5),
                        new Ingredient("goldbar", 25),
                        new Ingredient("wheat", 50)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "runebonestaff",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("runeboundscepter", 1),
                        new Ingredient("runestone", 10),
                        new Ingredient("clothscraps", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "icewizardstaff",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("voidmissile", 1),
                        new Ingredient("voidshard", 12),
                        new Ingredient("frostshard", 6)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "goblinsword",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("brokenirontool", 5),
                        new Ingredient("ironhelmet", 1),
                        new Ingredient("ironchestplate", 1),
                        new Ingredient("ironboots", 1)
                }
        ));

        // T3
        Recipes.registerModRecipe(new Recipe(
                "horrorscythe",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 25),
                        new Ingredient("farmingscythe", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "horrorglaive",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 25),
                        new Ingredient("frostaxe", 1),
                        new Ingredient("glacialaxe", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "horrorsword",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 12),
                        new Ingredient("woodsword", 1),
                        new Ingredient("coppersword", 1),
                        new Ingredient("ironsword", 1),
                        new Ingredient("goldsword", 1),
                        new Ingredient("frostsword", 1),
                        new Ingredient("demonicsword", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "fishianspear",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("bamboo", 25),
                        new Ingredient("seashell", 5),
                        new Ingredient("seasnail", 5),
                        new Ingredient("seastar", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "mosquitobow",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("spoiledfood", 30),
                        new Ingredient("myceliumore", 12),
                        new Ingredient("deepswampstone", 35),
                        new Ingredient("reeds", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "caveglowstaff",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("caveglow", 5),
                        new Ingredient("tungstenore", 15),
                        new Ingredient("fertilizer", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sandwormstaff",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("wormcarapace", 100),
                        new Ingredient("ancientfossilore", 25)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "dryadessence",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("essenceofprolonging", 1),
                        new Ingredient("essenceofperspective", 1),
                        new Ingredient("dryadlog", 100)
                }
        ));

        // T4
        Recipes.registerModRecipe(new Recipe(
                "vampirecoffin",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("cryptstone", 125),
                        new Ingredient("vampirewings", 1),
                        new Ingredient("bloodvolley", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "gemrubyshards",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ruby", 20),
                        new Ingredient("pearlescentdiamond", 5),
                        new Ingredient("omnicrystal", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "gemsapphireshards",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("sapphire", 20),
                        new Ingredient("pearlescentdiamond", 5),
                        new Ingredient("omnicrystal", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "gememeraldshards",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("emerald", 20),
                        new Ingredient("pearlescentdiamond", 5),
                        new Ingredient("omnicrystal", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "gemamethystshards",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("amethyst", 20),
                        new Ingredient("pearlescentdiamond", 5),
                        new Ingredient("omnicrystal", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "gemtopazshards",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("topaz", 20),
                        new Ingredient("pearlescentdiamond", 5),
                        new Ingredient("omnicrystal", 5)
                }
        ));
    }
}
