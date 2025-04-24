package summonerexpansion.summonothers;

import necesse.engine.journal.*;
import necesse.engine.registries.JournalChallengeRegistry;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.recipe.Tech;

import static necesse.engine.registries.RecipeTechRegistry.registerTech;

public class SummonerTechs
{
    public static Tech SUMMONTABLECRAFT;
    public static Tech SUMMONTABLECRAFT2;
    public static Tech SUMMONTABLECRAFT3;
    public static Tech SUMMONTABLECRAFT4;
    public static Tech SUMMONBOOKCRAFT;

    public static void registerSummonTechs()
    {
        // Workbenches
        SUMMONTABLECRAFT = registerTech("summontablecraft", "summontablecraftitem");
        SUMMONTABLECRAFT2 = registerTech("summontablecraft2", "summontablecraftitem2");
        SUMMONTABLECRAFT3 = registerTech("summontablecraft3", "summontablecraftitem3");
        SUMMONTABLECRAFT4 = registerTech("summontablecraft4", "summontablecraftitem4");
        SUMMONBOOKCRAFT = registerTech("summonbookcraft", "summonbookcraftitem");
    }
}