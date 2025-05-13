package summonerexpansion.summonothers;

import necesse.engine.journal.*;
import necesse.engine.registries.JournalChallengeRegistry;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import summonerexpansion.summonchallenges.*;

public class SummonerChallenges
{
    public static LootTable SUMMON_FOREST_SURFACE_REWARD = new LootTable((new LootItemList(new LootItem("bannerofwater"))).setCustomListName("object", "bannerofwater"));
    public static LootTable SUMMON_FOREST_CAVE_REWARD = new LootTable(new LootItem("giantbeet"));
    public static LootTable SUMMON_SNOW_SURFACE_REWARD = new LootTable(new LootItem("xmastreescepter"));

    public static int SUMMON_FOREST_SURFACE_CHALLENGES_ID;
    public static int SUMMON_FOREST_CAVES_CHALLENGES_ID;
    public static int SUMMON_SNOW_SURFACE_CHALLENGES_ID;

    public static int WATERGRASS_COLLECT_ID;
    public static int LANTERN_PLACE_ID;
    public static int SWIM_COLLECT_ID;
    public static int BUCKET_CRAFT_ID;
    public static int DUCK_KILL_ID;
    public static int BEET_KILL_ID;
    public static int BEET_PLACE_ID;
    public static int SEED_COLLECT_ID;
    public static int SNOWMAN_COLLECT_ID;
    public static int XMASWREATH_COLLECT_ID;
    public static int XMASTREE_PLACE_ID;
    public static int PRESENT_COLLECT_ID;
    public static int COOKIE_EAT_ID;

    public static void registerSummonChallenges()
    {
        //Forest
        WATERGRASS_COLLECT_ID = registerChallenge("watergrasscollected", new PickupItemsJournalChallenge(35, true, "watergrass"));
        SWIM_COLLECT_ID = registerChallenge("fincollected", new ItemObtainedJournalChallenge("fins"));
        LANTERN_PLACE_ID = registerChallenge("lanternplaced", new ObjectsPlacedJournalChallenge(5, "waterlantern"));
        BUCKET_CRAFT_ID = registerChallenge("bucketcrafted", new CraftItemJournalChallenge("bucket"));
        DUCK_KILL_ID = registerChallenge("duckkilled", new DuckKillJournalChallenge());
        SUMMON_FOREST_SURFACE_CHALLENGES_ID = registerChallenge("summonforestsurface", (new MultiJournalChallenge(WATERGRASS_COLLECT_ID, SWIM_COLLECT_ID, LANTERN_PLACE_ID, BUCKET_CRAFT_ID, DUCK_KILL_ID)).setReward(SUMMON_FOREST_SURFACE_REWARD));

        //Forest cave
        BEET_KILL_ID = registerChallenge("beetskilled", new BeetKillJournalChallenge());
        BEET_PLACE_ID = registerChallenge("beetplanted", new BeetPlaceJournalChallenge());
        SEED_COLLECT_ID = registerChallenge("seedcollected", new PickupItemsJournalChallenge(50, true, "beetseed", "cabbageseed", "cornseed", "tomatoseed", "wheatseed"));
        SUMMON_FOREST_CAVES_CHALLENGES_ID = registerChallenge("summonforestcave", (new MultiJournalChallenge(BEET_KILL_ID, BEET_PLACE_ID, SEED_COLLECT_ID)).setReward(SUMMON_FOREST_CAVE_REWARD));

        //Forest deep cave

        //Snow
        SNOWMAN_COLLECT_ID = registerChallenge("snowmancollected", new CraftItemJournalChallenge("snowmantrainingdummy"));
        XMASWREATH_COLLECT_ID = registerChallenge("xmaswreathcollected", new ItemObtainedJournalChallenge( "christmaswreath"));
        XMASTREE_PLACE_ID = registerChallenge("xmastreeplaced", new ObjectPlacedJournalChallenge("christmastree"));
        PRESENT_COLLECT_ID = registerChallenge("presentcollected", new PickupItemsJournalChallenge(10, false, "greenpresent", "bluepresent", "redpresent", "yellowpresent"));
        COOKIE_EAT_ID = registerChallenge("cookieeated", new FoodConsumedJournalChallenge("cookies"));
        SUMMON_SNOW_SURFACE_CHALLENGES_ID = registerChallenge("summonsnowsurface", (new MultiJournalChallenge(SNOWMAN_COLLECT_ID, XMASWREATH_COLLECT_ID, XMASTREE_PLACE_ID, PRESENT_COLLECT_ID, COOKIE_EAT_ID)).setReward(SUMMON_SNOW_SURFACE_REWARD));

        //Snow cave

        //Snow deep cave

        //Plains

        //Plains cave

        //Plains deep cave

        //Swamp

        //Swamp cave

        //Swamp deep cave

        //Desert

        //Desert cave

        //Desert deep cave

        //Dungeon

        //Village

        //Temple

        //Slime

        //Graveyard

        //Spider castle

        //Crystal

        //Sunlight

        //Moonlight

    }

    public static int registerChallenge(String stringID, JournalChallenge journalChallenge)
    {
        return JournalChallengeRegistry.registerChallenge(stringID, journalChallenge);
    }
}