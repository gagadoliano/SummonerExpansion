package summonerexpansion.summonothers;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.summonothers.SummonerTechs.*;

public class SummonerRecipes
{
    public static void registerSummonRecipes()
    {
        // Workstation items
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

        // Anvil items
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
                "bookmushroom",
                1,
                SUMMONBOOKCRAFT,
                new Ingredient[]{
                        new Ingredient("mushroom", 25),
                        new Ingredient("spoiledfood", 10),
                        new Ingredient("book", 1)
                }
        ));

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
                        new Ingredient("wool", 15)
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

        // Summon table trinkets
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

        // Summon table armors
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
                "bloodplatemask",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("bloodplatecowl", 1),
                        new Ingredient("batwing", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bloodplatechestplate",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("demonicbar", 5),
                        new Ingredient("batwing", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bloodplateboots",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("demonicbar", 5),
                        new Ingredient("batwing", 10)
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

        // Mounts
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

        // Objects
        Recipes.registerModRecipe(new Recipe(
                "tanktrainingdummy",
                1,
                RecipeTechRegistry.CARPENTER,
                new Ingredient[]{
                        new Ingredient("trainingdummy", 1),
                        new Ingredient("ironbar", 100)
                }
        ));

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
                "overgrowthornssapling",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("thorns", 100),
                        new Ingredient("thornspotion", 1),
                        new Ingredient("fertilizer", 5)
                }
        ));

        //Tiles
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

        // Boss summon
        Recipes.registerModRecipe(new Recipe(
                "shadowhorrorportal",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("purehorror", 5),
                        new Ingredient("mysteriousportal", 1)
                }
        ));

        // Stat items
        Recipes.registerModRecipe(new Recipe(
                "fusedessences",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("shadowessence", 5),
                        new Ingredient("cryoessence", 5),
                        new Ingredient("bioessence", 5),
                        new Ingredient("primordialessence", 5)
                }
        ));

        // Food
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

        // Potions
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