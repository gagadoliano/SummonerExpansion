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
    public static LootTable SUMMON_FOREST_DEEP_CAVE_REWARD = new LootTable(new LootItem("giantonion"));
    public static LootTable SUMMON_SNOW_SURFACE_REWARD = new LootTable(new LootItem("xmastreescepter"));
    public static LootTable SUMMON_SNOW_CAVE_REWARD = new LootTable(new LootItem("giantpotato"));
    public static LootTable SUMMON_SNOW_DEEP_CAVE_REWARD = new LootTable(new LootItem("giantpumpkin"));
    public static LootTable SUMMON_DUNGEON_REWARD = new LootTable(new LootItem("glyphtrapheal"));
    public static LootTable SUMMON_PLAINS_SURFACE_REWARD = new LootTable(new LootItem("silvergoblet"));
    public static LootTable SUMMON_SWAMP_CAVE_REWARD = new LootTable(new LootItem("doomshroomshield"));
    public static LootTable SUMMON_SWAMP_DEEP_CAVE_REWARD = new LootTable(new LootItem("jellyfishbowl"));
    public static LootTable SUMMON_DESERT_CAVE_REWARD = new LootTable(new LootItem("giantcarrot"));

    public static int SUMMON_FOREST_SURFACE_CHALLENGES_ID;
    public static int SUMMON_FOREST_CAVE_CHALLENGES_ID;
    public static int SUMMON_FOREST_DEEP_CAVE_CHALLENGES_ID;
    public static int SUMMON_SNOW_SURFACE_CHALLENGES_ID;
    public static int SUMMON_SNOW_CAVE_CHALLENGES_ID;
    public static int SUMMON_SNOW_DEEP_CAVE_CHALLENGES_ID;
    public static int SUMMON_DUNGEON_CHALLENGES_ID;
    public static int SUMMON_PLAINS_SURFACE_CHALLENGES_ID;
    public static int SUMMON_PLAINS_CAVE_CHALLENGES_ID;
    public static int SUMMON_PLAINS_DEEP_CAVE_CHALLENGES_ID;
    public static int SUMMON_SWAMP_SURFACE_CHALLENGES_ID;
    public static int SUMMON_SWAMP_CAVE_CHALLENGES_ID;
    public static int SUMMON_SWAMP_DEEP_CAVE_CHALLENGES_ID;
    public static int SUMMON_DESERT_SURFACE_CHALLENGES_ID;
    public static int SUMMON_DESERT_CAVE_CHALLENGES_ID;
    public static int SUMMON_DESERT_DEEP_CAVE_CHALLENGES_ID;

    public static int WATERGRASS_COLLECT_ID;
    public static int LANTERN_PLACE_ID;
    public static int SWIM_COLLECT_ID;
    public static int BUCKET_CRAFT_ID;
    public static int DUCK_KILL_ID;
    public static int BEET_KILL_ID;
    public static int BEET_PLACE_ID;
    public static int SEED_COLLECT_ID;
    public static int ONION_KILL_ID;
    public static int ONION_PLACE_ID;

    public static int SNOWMAN_COLLECT_ID;
    public static int XMASWREATH_COLLECT_ID;
    public static int XMASTREE_PLACE_ID;
    public static int COOKIE_EAT_ID;
    public static int POTATO_KILL_ID;
    public static int POTATO_PLACE_ID;
    public static int PUMPKIN_KILL_ID;
    public static int PUMPKIN_PLACE_ID;

    public static int TRAP_REVERSE_COLLECT_ID;
    public static int TRAP_CHICKEN_COLLECT_ID;
    public static int TRAP_BOUNCY_COLLECT_ID;

    public static int FIREFLY_COLLECT_ID;
    public static int EGG_PLACE_ID;
    public static int KATANA_DESTROY_ID;
    public static int WHITEFLOWER_PLACE_ID;

    public static int SPORE_KILL_ID;
    public static int MUSHROOM_COLLECT_ID;
    public static int MUD_COLLECT_ID;
    public static int STINKFLASK_COLLECT_ID;
    public static int SHELL_DESTROY_ID;
    public static int BAMBOO_COLLECT_ID;
    public static int FISHIAN_KILL_ID;

    public static int CARROT_KILL_ID;
    public static int CARROT_PLACE_ID;

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
        BEET_KILL_ID = registerChallenge("beetkilled", new BeetKillJournalChallenge());
        BEET_PLACE_ID = registerChallenge("beetplanted", new BeetPlaceJournalChallenge());
        SEED_COLLECT_ID = registerChallenge("seedcollected", new PickupItemsJournalChallenge(50, true, "beetseed", "carrotseed", "onionseed", "potatoseed", "pumpkinseed"));
        SUMMON_FOREST_CAVE_CHALLENGES_ID = registerChallenge("summonforestcave", (new MultiJournalChallenge(BEET_KILL_ID, BEET_PLACE_ID, SEED_COLLECT_ID)).setReward(SUMMON_FOREST_CAVE_REWARD));

        //Forest deep cave
        ONION_KILL_ID = registerChallenge("onionkilled", new OnionKillJournalChallenge());
        ONION_PLACE_ID = registerChallenge("onionplanted", new OnionPlaceJournalChallenge());

        SUMMON_FOREST_DEEP_CAVE_CHALLENGES_ID = registerChallenge("summonforestdeepcave", (new MultiJournalChallenge(ONION_KILL_ID, ONION_PLACE_ID)).setReward(SUMMON_FOREST_DEEP_CAVE_REWARD));

        //Snow
        COOKIE_EAT_ID = registerChallenge("cookieeated", new FoodConsumedJournalChallenge("cookies"));
        SNOWMAN_COLLECT_ID = registerChallenge("snowmancollected", new CraftItemJournalChallenge("snowmantrainingdummy"));
        XMASTREE_PLACE_ID = registerChallenge("xmastreeplaced", new ObjectPlacedJournalChallenge("christmastree"));
        XMASWREATH_COLLECT_ID = registerChallenge("xmaswreathcollected", new ItemObtainedJournalChallenge( "christmaswreath"));
        SUMMON_SNOW_SURFACE_CHALLENGES_ID = registerChallenge("summonsnowsurface", (new MultiJournalChallenge(SNOWMAN_COLLECT_ID, XMASWREATH_COLLECT_ID, XMASTREE_PLACE_ID, COOKIE_EAT_ID)).setReward(SUMMON_SNOW_SURFACE_REWARD));

        //Snow cave
        POTATO_KILL_ID = registerChallenge("potatokilled", new PotatoKillJournalChallenge());
        POTATO_PLACE_ID = registerChallenge("potatoplanted", new PotatoPlaceJournalChallenge());

        SUMMON_SNOW_CAVE_CHALLENGES_ID = registerChallenge("summonsnowcave", (new MultiJournalChallenge(POTATO_KILL_ID, POTATO_PLACE_ID)).setReward(SUMMON_SNOW_CAVE_REWARD));

        //Snow deep cave
        PUMPKIN_KILL_ID = registerChallenge("pumpkinkilled", new PumpkinKillJournalChallenge());
        PUMPKIN_PLACE_ID = registerChallenge("pumpkinplanted", new PumpkinPlaceJournalChallenge());

        SUMMON_SNOW_DEEP_CAVE_CHALLENGES_ID = registerChallenge("summonsnowdeepcave", (new MultiJournalChallenge(PUMPKIN_KILL_ID, PUMPKIN_PLACE_ID)).setReward(SUMMON_SNOW_DEEP_CAVE_REWARD));

        //Dungeon
        TRAP_REVERSE_COLLECT_ID = registerChallenge("trapreversecollected", new PickupItemsJournalChallenge(3, true, "glyphtrapreversedamage"));
        TRAP_CHICKEN_COLLECT_ID = registerChallenge("trapchickencollected", new PickupItemsJournalChallenge(3, true, "glyphtrapchicken"));
        TRAP_BOUNCY_COLLECT_ID = registerChallenge("trapbouncycollected", new PickupItemsJournalChallenge(3, true, "glyphtrapbounce"));
        SUMMON_DUNGEON_CHALLENGES_ID = registerChallenge("summondungeon", (new MultiJournalChallenge(TRAP_REVERSE_COLLECT_ID, TRAP_CHICKEN_COLLECT_ID, TRAP_BOUNCY_COLLECT_ID)).setReward(SUMMON_DUNGEON_REWARD));

        //Plains
        FIREFLY_COLLECT_ID = registerChallenge("fireflycollected", new PickupItemsJournalChallenge(1, true, "fireflyyellow", "fireflygreen", "fireflyblue"));
        EGG_PLACE_ID = registerChallenge("eggplaced", new FoodConsumedJournalChallenge("friedegg"));
        KATANA_DESTROY_ID = registerChallenge("katanastonedestroyed", new ObjectDestroyedJournalChallenge("katanastone"));
        WHITEFLOWER_PLACE_ID = registerChallenge("whiteflowerplaced", new ObjectsPlacedJournalChallenge(15, "whiteflowerpatch"));
        SUMMON_PLAINS_SURFACE_CHALLENGES_ID = registerChallenge("summonplainssurface", (new MultiJournalChallenge(FIREFLY_COLLECT_ID, EGG_PLACE_ID, KATANA_DESTROY_ID, WHITEFLOWER_PLACE_ID)).setReward(SUMMON_PLAINS_SURFACE_REWARD));

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
        SUMMON_SWAMP_DEEP_CAVE_CHALLENGES_ID = registerChallenge("summondeepswampcave", (new MultiJournalChallenge(FISHIAN_KILL_ID, BAMBOO_COLLECT_ID, SHELL_DESTROY_ID)).setReward(SUMMON_SWAMP_DEEP_CAVE_REWARD));

        //Desert

        //Desert cave
        CARROT_KILL_ID = registerChallenge("carrotkilled", new CarrotKillJournalChallenge());
        CARROT_PLACE_ID = registerChallenge("carrotplanted", new CarrotPlaceJournalChallenge());

        SUMMON_DESERT_CAVE_CHALLENGES_ID = registerChallenge("summondesertcave", (new MultiJournalChallenge(CARROT_KILL_ID, CARROT_PLACE_ID)).setReward(SUMMON_DESERT_CAVE_REWARD));

        //Desert deep cave

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