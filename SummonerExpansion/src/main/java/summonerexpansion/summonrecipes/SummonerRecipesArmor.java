package summonerexpansion.summonrecipes;

import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static summonerexpansion.codes.summonregistry.SummonerTechs.*;

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
                "spiderhelmet",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("cavespidergland", 6),
                        new Ingredient("clay", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spiderchestplate",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("cavespidergland", 9),
                        new Ingredient("clay", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spiderboots",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("cavespidergland", 6),
                        new Ingredient("clay", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "goldcrown",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("goldbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "goldchestplate",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("goldbar", 12)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "goldboots",
                1,
                SUMMONTABLECRAFT,
                new Ingredient[]{
                        new Ingredient("goldbar", 10)
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
                "voidmask",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("voidshard", 5),
                        new Ingredient("wool", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "voidrobe",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("voidshard", 5),
                        new Ingredient("wool", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "voidboots",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("voidshard", 5),
                        new Ingredient("wool", 10)
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
                "arachnidhelmet",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("redspiderhelmet", 1),
                        new Ingredient("frostshard", 1),
                        new Ingredient("cavespidergland", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "arachnidchestplate",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("redspiderchestplate", 1),
                        new Ingredient("frostshard", 4),
                        new Ingredient("cavespidergland", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "arachnidlegs",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("redspiderboots", 1),
                        new Ingredient("frostshard", 2),
                        new Ingredient("cavespidergland", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "runiccrown",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("runestone", 3),
                        new Ingredient("clothscraps", 3)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "runicchestplate",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("runestone", 9),
                        new Ingredient("clothscraps", 9)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "runicboots",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("runestone", 6),
                        new Ingredient("clothscraps", 6)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ivycirclet",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("ivybar", 4),
                        new Ingredient("swampsludge", 4),
                        new Ingredient("voidshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ivychestplate",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("ivybar", 10),
                        new Ingredient("swampsludge", 8),
                        new Ingredient("voidshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ivyboots",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("ivybar", 6),
                        new Ingredient("swampsludge", 4),
                        new Ingredient("voidshard", 1)
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
                "pharaohsmask",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("ancientstatue", 1),
                        new Ingredient("rice", 10),
                        new Ingredient("amethyst", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "pharaohsrobe",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("runicchestplate", 1),
                        new Ingredient("clothrobe", 10),
                        new Ingredient("clothscraps", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "pharaohssandals",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("runicboots", 1),
                        new Ingredient("clothboots", 10),
                        new Ingredient("clothscraps", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "quartzcrown",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("quartz", 4),
                        new Ingredient("sandstone", 40)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "quartzchestplate",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("quartz", 10),
                        new Ingredient("sandstone", 60)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "quartzboots",
                1,
                SUMMONTABLECRAFT2,
                new Ingredient[]{
                        new Ingredient("quartz", 6),
                        new Ingredient("sandstone", 50)
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
                "soulseedcrown",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ivycirclet", 1),
                        new Ingredient("caveglow", 10),
                        new Ingredient("obsidian", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulseedchestplate",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ivychestplate", 1),
                        new Ingredient("caveglow", 10),
                        new Ingredient("obsidian", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulseedboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ivyboots", 1),
                        new Ingredient("caveglow", 10),
                        new Ingredient("obsidian", 4)
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
                "glacialcirclet",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("glacialbar", 12),
                        new Ingredient("glacialshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "glacialchestplate",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("glacialbar", 12),
                        new Ingredient("glacialshard", 3)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "glacialboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("glacialbar", 12),
                        new Ingredient("glacialshard", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "cryowitchhat",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("witchhat", 1),
                        new Ingredient("glacialbar", 12),
                        new Ingredient("glacialshard", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "cryowitchrobe",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("witchrobe", 1),
                        new Ingredient("glacialbar", 12),
                        new Ingredient("glacialshard", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "cryowitchshoes",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("witchshoes", 1),
                        new Ingredient("glacialbar", 12),
                        new Ingredient("glacialshard", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "dryadcrown",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("dryadlog", 15),
                        new Ingredient("amber", 5),
                        new Ingredient("dryadsapling", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "dryadchestplate",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("dryadlog", 20),
                        new Ingredient("amber", 5),
                        new Ingredient("dryadsapling", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "dryadboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("dryadlog", 10),
                        new Ingredient("amber", 5),
                        new Ingredient("dryadsapling", 2)
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
                "myceliumscarf",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("myceliumbar", 10),
                        new Ingredient("decayingleaf", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "myceliumchestplate",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("myceliumbar", 10),
                        new Ingredient("decayingleaf", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "myceliumboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("myceliumbar", 10),
                        new Ingredient("decayingleaf", 1)
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

        Recipes.registerModRecipe(new Recipe(
                "ancientfossilmask",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ancientfossilbar", 8),
                        new Ingredient("wormcarapace", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ancientfossilchestplate",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ancientfossilbar", 10),
                        new Ingredient("wormcarapace", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ancientfossilboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("ancientfossilbar", 8),
                        new Ingredient("wormcarapace", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sharpshootersummonhat",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("gunslingerhat", 1),
                        new Ingredient("ancientfossilbar", 6),
                        new Ingredient("wormcarapace", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sharpshootercoat",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("gunslingervest", 1),
                        new Ingredient("ancientfossilbar", 10),
                        new Ingredient("wormcarapace", 12)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "sharpshooterboots",
                1,
                SUMMONTABLECRAFT3,
                new Ingredient[]{
                        new Ingredient("gunslingerboots", 1),
                        new Ingredient("ancientfossilbar", 8),
                        new Ingredient("wormcarapace", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ghostcaptainshat",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("captainshat", 1),
                        new Ingredient("handcannon", 1),
                        new Ingredient("spareboatparts", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ghostcaptainsshirt",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("captainsshirt", 1),
                        new Ingredient("cannonball", 50),
                        new Ingredient("ectoplasm", 25)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ghostcaptainsboots",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("captainsboots", 1),
                        new Ingredient("ironbomb", 50),
                        new Ingredient("ammobox", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "nightsteelcirclet",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("nightsteelbar", 10),
                        new Ingredient("phantomdust", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "nightsteelchestplate",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("nightsteelbar", 10),
                        new Ingredient("phantomdust", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "nightsteelboots",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("nightsteelbar", 10),
                        new Ingredient("phantomdust", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spideritecrown",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("spideritebar", 10),
                        new Ingredient("spidervenom", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spideritechestplate",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("spideritebar", 10),
                        new Ingredient("spidervenom", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spideritegreaves",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("spideritebar", 10),
                        new Ingredient("spidervenom", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "rubycrown",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ruby", 10),
                        new Ingredient("pearlescentdiamond", 10),
                        new Ingredient("omnicrystal", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalchestplate",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ruby", 10),
                        new Ingredient("pearlescentdiamond", 10),
                        new Ingredient("omnicrystal", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalboots",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ruby", 10),
                        new Ingredient("pearlescentdiamond", 10),
                        new Ingredient("omnicrystal", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ravenlordsummonmask",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("vulturemask", 1),
                        new Ingredient("ravenfeather", 15),
                        new Ingredient("anytier2essence", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ravenlordschestplate",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ravenfeather", 15),
                        new Ingredient("anytier2essence", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ravenlordsboots",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("ravenfeather", 15),
                        new Ingredient("anytier2essence", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "arcanicsummonhelmet",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("electrifiedmana", 15),
                        new Ingredient("omnicrystal", 15),
                        new Ingredient("pearlescentdiamond", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "arcanicchestplate",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("electrifiedmana", 15),
                        new Ingredient("omnicrystal", 15),
                        new Ingredient("pearlescentdiamond", 5)
                }
        ));


        Recipes.registerModRecipe(new Recipe(
                "arcanicboots",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("electrifiedmana", 15),
                        new Ingredient("omnicrystal", 15),
                        new Ingredient("pearlescentdiamond", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "chefsummonerhat",
                1,
                SUMMONTABLECRAFT4,
                new Ingredient[]{
                        new Ingredient("chefsspecial", 1),
                        new Ingredient("anytier2essence", 15)
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
        ).showAfter("copperhelmet"));

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
                "frostcrown",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("frostshard", 15),
                        new Ingredient("goldcrown", 1)
                }
        ).showAfter("frosthat"));

        Recipes.registerModRecipe(new Recipe(
                "bloodplatemask",
                1,
                RecipeTechRegistry.DEMONIC_ANVIL,
                new Ingredient[]{
                        new Ingredient("demonicbar", 8),
                        new Ingredient("batwing", 12)
                }
        ).showAfter("bloodplatecowl"));

        Recipes.registerModRecipe(new Recipe(
                "pharaohsmask",
                1,
                RecipeTechRegistry.DEMONIC_ANVIL,
                new Ingredient[]{
                        new Ingredient("runiccrown", 1),
                        new Ingredient("clothhat", 1),
                        new Ingredient("amethyst", 5)
                }
        ).showAfter("pharaohsheaddress"));

        Recipes.registerModRecipe(new Recipe(
                "shadowhelmet",
                1,
                RecipeTechRegistry.TUNGSTEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ectoplasm", 12),
                        new Ingredient("bone", 10)
                }
        ).showAfter("shadowhood"));

        Recipes.registerModRecipe(new Recipe(
                "sharpshootersummonhat",
                1,
                RecipeTechRegistry.TUNGSTEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ancientfossilbar", 10),
                        new Ingredient("wormcarapace", 5)
                }
        ).showAfter("sharpshooterhat"));

        Recipes.registerModRecipe(new Recipe(
                "ravenlordsummonmask",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ravenfeather", 18),
                        new Ingredient("anytier2essence", 14)
                }
        ).showAfter("ravenlordsheaddress"));

        Recipes.registerModRecipe(new Recipe(
                "arcanicsummonhelmet",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("electrifiedmana", 18),
                        new Ingredient("omnicrystal", 14)
                }
        ).showAfter("arcanichelmet"));
    }
}
