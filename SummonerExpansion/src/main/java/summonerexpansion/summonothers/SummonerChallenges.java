package summonerexpansion.summonothers;

import necesse.engine.journal.*;
import necesse.engine.registries.JournalChallengeRegistry;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;

public class SummonerChallenges
{
    public static LootTable SUMMON_FOREST_SURFACE_REWARD = new LootTable((new LootItemList(new LootItem("bannerofwater"))).setCustomListName("object", "bannerofwater"));
    public static int SUMMON_FOREST_SURFACE_CHALLENGES_ID;
    public static int WATERGRASS_COLLECT_ID;
    public static int LANTERN_PLACE_ID;
    public static int SWIM_COLLECT_ID;
    public static int BUCKET_CRAFT_ID;
    public static int DUCK_KILL_ID;

    public static void registerSummonChallenges()
    {
        WATERGRASS_COLLECT_ID = registerChallenge("watergrasscollected", new PickupItemsJournalChallenge(35, true, "watergrass"));
        SWIM_COLLECT_ID = registerChallenge("fincollected", new PickupItemsJournalChallenge(1, true, "fins"));
        LANTERN_PLACE_ID = registerChallenge("lanternplaced", new ObjectPlacedJournalChallenge("waterlantern"));
        BUCKET_CRAFT_ID = registerChallenge("bucketcrafted", new CraftItemJournalChallenge("bucket"));
        DUCK_KILL_ID = registerChallenge("duckkilled", new DefeatMobJournalChallenge("duck"));
        SUMMON_FOREST_SURFACE_CHALLENGES_ID = registerChallenge("summonforestsurface", (new MultiJournalChallenge(WATERGRASS_COLLECT_ID, SWIM_COLLECT_ID, LANTERN_PLACE_ID, BUCKET_CRAFT_ID, DUCK_KILL_ID)).setReward(SUMMON_FOREST_SURFACE_REWARD));
    }

    public static int registerChallenge(String stringID, JournalChallenge journalChallenge)
    {
        return JournalChallengeRegistry.registerChallenge(stringID, journalChallenge);
    }
}