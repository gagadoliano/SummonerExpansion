package summonerexpansion.codes.registry;

import necesse.engine.journal.*;
import necesse.engine.registries.JournalChallengeRegistry;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import summonerexpansion.modchallenges.*;

public class SummonerChallenges
{
    public static LootTable SUMMON_FOREST_SURFACE_REWARD = new LootTable((new LootItemList(new LootItem("bannerofwater"))).setCustomListName("object", "bannerofwater"));
    public static LootTable SUMMON_FOREST_CAVE_REWARD = new LootTable(new LootItem("giantbeet"));
    public static LootTable SUMMON_SNOW_SURFACE_REWARD = new LootTable(new LootItem("xmastreescepter"));
    public static LootTable SUMMON_SWAMP_CAVE_REWARD = new LootTable(new LootItem("doomshroomshield"));
    public static LootTable SUMMON_DEEP_SWAMP_CAVE_REWARD = new LootTable(new LootItem("jellyfishbowl"));

    public static int SUMMON_FOREST_SURFACE_CHALLENGES_ID;
    public static int SUMMON_FOREST_CAVE_CHALLENGES_ID;
    public static int SUMMON_SNOW_SURFACE_CHALLENGES_ID;
    public static int SUMMON_SWAMP_CAVE_CHALLENGES_ID;
    public static int SUMMON_DEEP_SWAMP_CAVE_CHALLENGES_ID;

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
    public static int COOKIE_EAT_ID;
    public static int SPORE_KILL_ID;
    public static int MUSHROOM_COLLECT_ID;
    public static int MUD_COLLECT_ID;
    public static int STINKFLASK_COLLECT_ID;
    public static int SHELL_DESTROY_ID;
    public static int BAMBOO_COLLECT_ID;
    public static int FISHIAN_KILL_ID;

    public static void registerSummonChallenges()
    {
        //Forest
        DUCK_KILL_ID = registerChallenge("duckkilled", new DuckKillJournalChallenge());
        BUCKET_CRAFT_ID = registerChallenge("bucketcrafted", new CraftItemJournalChallenge("bucket"));
        SWIM_COLLECT_ID = registerChallenge("fincollected", new ItemObtainedJournalChallenge("fins"));
        LANTERN_PLACE_ID = registerChallenge("lanternplaced", new ObjectsPlacedJournalChallenge(5, "waterlantern"));
        WATERGRASS_COLLECT_ID = registerChallenge("watergrasscollected", new PickupItemsJournalChallenge(35, true, "watergrass"));
        SUMMON_FOREST_SURFACE_CHALLENGES_ID = registerChallenge("summonforestsurface", (new MultiJournalChallenge(WATERGRASS_COLLECT_ID, SWIM_COLLECT_ID, LANTERN_PLACE_ID, BUCKET_CRAFT_ID, DUCK_KILL_ID)).setReward(SUMMON_FOREST_SURFACE_REWARD));

        //Forest cave
        BEET_KILL_ID = registerChallenge("beetskilled", new BeetKillJournalChallenge());
        BEET_PLACE_ID = registerChallenge("beetplanted", new BeetPlaceJournalChallenge());
        SEED_COLLECT_ID = registerChallenge("seedcollected", new PickupItemsJournalChallenge(50, true, "beetseed", "cabbageseed", "cornseed", "tomatoseed", "wheatseed"));
        SUMMON_FOREST_CAVE_CHALLENGES_ID = registerChallenge("summonforestcave", (new MultiJournalChallenge(BEET_KILL_ID, BEET_PLACE_ID, SEED_COLLECT_ID)).setReward(SUMMON_FOREST_CAVE_REWARD));

        //Forest deep cave

        //Snow
        COOKIE_EAT_ID = registerChallenge("cookieeated", new FoodConsumedJournalChallenge("cookies"));
        SNOWMAN_COLLECT_ID = registerChallenge("snowmancollected", new CraftItemJournalChallenge("snowmantrainingdummy"));
        XMASTREE_PLACE_ID = registerChallenge("xmastreeplaced", new ObjectPlacedJournalChallenge("christmastree"));
        XMASWREATH_COLLECT_ID = registerChallenge("xmaswreathcollected", new ItemObtainedJournalChallenge( "christmaswreath"));
        SUMMON_SNOW_SURFACE_CHALLENGES_ID = registerChallenge("summonsnowsurface", (new MultiJournalChallenge(SNOWMAN_COLLECT_ID, XMASWREATH_COLLECT_ID, XMASTREE_PLACE_ID, COOKIE_EAT_ID)).setReward(SUMMON_SNOW_SURFACE_REWARD));

        //Snow cave

        //Snow deep cave

        //Plains

        //Plains cave

        //Plains deep cave

        //Swamp

        //Swamp cave
        SPORE_KILL_ID = registerChallenge("sporekilled", new MobsKilledJournalChallenge(10, "swampsporedummy"));
        MUSHROOM_COLLECT_ID = registerChallenge("mushroomcollected", new PickupItemsJournalChallenge(50, true, "mushroom"));
        MUD_COLLECT_ID = registerChallenge("mudcollected", new PickupItemsJournalChallenge(100, true, "mudtile"));
        STINKFLASK_COLLECT_ID = registerChallenge("stinkflaskcollected", new PickupItemJournalChallenge(true, "stinkflask"));
        SUMMON_SWAMP_CAVE_CHALLENGES_ID = registerChallenge("summonswampcave", (new MultiJournalChallenge(SPORE_KILL_ID, MUSHROOM_COLLECT_ID, MUD_COLLECT_ID, STINKFLASK_COLLECT_ID)).setReward(SUMMON_SWAMP_CAVE_REWARD));

        //Swamp deep cave
        FISHIAN_KILL_ID = registerChallenge("fishiankilled", new FishianKillJournalChallenge());
        BAMBOO_COLLECT_ID = registerChallenge("bamboocollected", new PickupItemsJournalChallenge(100, true, "bamboo"));
        SHELL_DESTROY_ID = registerChallenge("shelldestroyed", new ObjectsDestroyedJournalChallenge(30, true, "seashell", "seasnail", "seastar"));
        SUMMON_DEEP_SWAMP_CAVE_CHALLENGES_ID = registerChallenge("summondeepswampcave", (new MultiJournalChallenge(FISHIAN_KILL_ID, BAMBOO_COLLECT_ID, SHELL_DESTROY_ID)).setReward(SUMMON_DEEP_SWAMP_CAVE_REWARD));

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