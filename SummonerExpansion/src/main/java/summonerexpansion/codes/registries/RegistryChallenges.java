package summonerexpansion.codes.registries;

import necesse.engine.journal.*;
import necesse.engine.registries.JournalChallengeRegistry;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import summonerexpansion.codes.challenges.*;

public class RegistryChallenges
{
    public static LootTable FOREST_SURFACE_REWARD = new LootTable((new LootItemList(new LootItem("bannerofwater"))).setCustomListName("object", "bannerofwater"));
    public static LootTable FOREST_SURFACE_REWARD2 = new LootTable(new LootItem("minionsunflowerpotion"));
    public static LootTable FOREST_CAVE_REWARD = new LootTable(new LootItem("giantbeet"));
    public static LootTable FOREST_DEEP_CAVE_REWARD = new LootTable(new LootItem("giantonion"));
    public static LootTable SNOW_SURFACE_REWARD = new LootTable(new LootItem("xmastreescepter"));
    public static LootTable SNOW_CAVE_REWARD = new LootTable(new LootItem("giantpotato"));
    public static LootTable SNOW_DEEP_CAVE_REWARD = new LootTable(new LootItem("giantpumpkin"));
    public static LootTable DUNGEON_REWARD = new LootTable(new LootItem("glyphtrapheal"));
    public static LootTable PLAINS_SURFACE_REWARD = new LootTable(new LootItem("silvergoblet"));
    public static LootTable SWAMP_CAVE_REWARD = new LootTable(new LootItem("doomshroomshield"));
    public static LootTable SWAMP_DEEP_CAVE_REWARD = new LootTable(new LootItem("jellyfishbowl"));
    public static LootTable DESERT_CAVE_REWARD = new LootTable(new LootItem("giantcarrot"));

    public static int FOREST_SURFACE_CHALLENGES_ID;
    public static int FOREST_SURFACE_CHALLENGES_ID2;
    public static int FOREST_CAVE_CHALLENGES_ID;
    public static int FOREST_DEEP_CAVE_CHALLENGES_ID;
    public static int SNOW_SURFACE_CHALLENGES_ID;
    public static int SNOW_CAVE_CHALLENGES_ID;
    public static int SNOW_DEEP_CAVE_CHALLENGES_ID;
    public static int DUNGEON_CHALLENGES_ID;
    public static int PLAINS_SURFACE_CHALLENGES_ID;
    public static int PLAINS_CAVE_CHALLENGES_ID;
    public static int PLAINS_DEEP_CAVE_CHALLENGES_ID;
    public static int SWAMP_SURFACE_CHALLENGES_ID;
    public static int SWAMP_CAVE_CHALLENGES_ID;
    public static int SWAMP_DEEP_CAVE_CHALLENGES_ID;
    public static int DESERT_SURFACE_CHALLENGES_ID;
    public static int DESERT_CAVE_CHALLENGES_ID;
    public static int DESERT_DEEP_CAVE_CHALLENGES_ID;

    public static int WATERGRASS_COLLECT_ID;
    public static int LANTERN_PLACE_ID;
    public static int SWIM_COLLECT_ID;
    public static int BUCKET_CRAFT_ID;
    public static int DUCK_KILL_ID;
    public static int UPGRADE_TIER1_ID;
    public static int UPGRADE_TIER2_ID;
    public static int UPGRADE_TIER3_ID;
    public static int UPGRADE_TIER4_ID;
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

    public static void registerChallenges()
    {
        //Forest
        DUCK_KILL_ID = registerChallenge("duckkilled", new MobsKilledJournalChallenge(20, "duck"));
        BUCKET_CRAFT_ID = registerChallenge("bucketcrafted", new CraftItemJournalChallenge("bucket"));
        SWIM_COLLECT_ID = registerChallenge("fincollected", new ItemObtainedJournalChallenge("fins"));
        LANTERN_PLACE_ID = registerChallenge("lanternplaced", new ObjectsPlacedJournalChallenge(5, "waterlantern"));
        WATERGRASS_COLLECT_ID = registerChallenge("watergrasscollected", new PickupItemsJournalChallenge(35, true, "watergrass"));
        FOREST_SURFACE_CHALLENGES_ID = registerChallenge("summonforestsurface", (new MultiJournalChallenge(WATERGRASS_COLLECT_ID, SWIM_COLLECT_ID, LANTERN_PLACE_ID, BUCKET_CRAFT_ID, DUCK_KILL_ID)).setReward(FOREST_SURFACE_REWARD));

        UPGRADE_TIER1_ID = registerChallenge("upgradetier1", new DefeatMobJournalChallenge("swampguardian"));
        UPGRADE_TIER2_ID = registerChallenge("upgradetier2", new DefeatMobJournalChallenge("piratecaptain"));
        UPGRADE_TIER3_ID = registerChallenge("upgradetier3", new DefeatMobJournalChallenge("pestwarden"));
        UPGRADE_TIER4_ID = registerChallenge("upgradetier4", new DefeatMobJournalChallenge("fallenwizard"));
        FOREST_SURFACE_CHALLENGES_ID2 = registerChallenge("summonupgradeforestsurface", (new MultiJournalChallenge(UPGRADE_TIER1_ID, UPGRADE_TIER2_ID, UPGRADE_TIER3_ID, UPGRADE_TIER4_ID)).setReward(FOREST_SURFACE_REWARD2));

        //Forest cave
        BEET_KILL_ID = registerChallenge("beetkilled", new CaveKillJournalChallenge(20, "cropplerbeet"));
        BEET_PLACE_ID = registerChallenge("beetplanted", new CavePlaceJournalChallenge(20, "beetseed"));
        SEED_COLLECT_ID = registerChallenge("seedcollected", new PickupItemsJournalChallenge(50, true, "beetseed", "carrotseed", "onionseed", "potatoseed", "pumpkinseed"));
        FOREST_CAVE_CHALLENGES_ID = registerChallenge("summonforestcave", (new MultiJournalChallenge(BEET_KILL_ID, BEET_PLACE_ID, SEED_COLLECT_ID)).setReward(FOREST_CAVE_REWARD));

        //Forest deep cave
        ONION_KILL_ID = registerChallenge("onionkilled", new CaveKillJournalChallenge(20, "croppleronion"));
        ONION_PLACE_ID = registerChallenge("onionplanted", new CavePlaceJournalChallenge(20, "onionseed"));

        FOREST_DEEP_CAVE_CHALLENGES_ID = registerChallenge("summonforestdeepcave", (new MultiJournalChallenge(ONION_KILL_ID, ONION_PLACE_ID)).setReward(FOREST_DEEP_CAVE_REWARD));

        //Snow
        COOKIE_EAT_ID = registerChallenge("cookieeated", new FoodConsumedJournalChallenge("cookies"));
        SNOWMAN_COLLECT_ID = registerChallenge("snowmancollected", new CraftItemJournalChallenge("snowmantrainingdummy"));
        XMASTREE_PLACE_ID = registerChallenge("xmastreeplaced", new ObjectPlacedJournalChallenge("christmastree"));
        XMASWREATH_COLLECT_ID = registerChallenge("xmaswreathcollected", new ItemObtainedJournalChallenge( "christmaswreath"));
        SNOW_SURFACE_CHALLENGES_ID = registerChallenge("summonsnowsurface", (new MultiJournalChallenge(SNOWMAN_COLLECT_ID, XMASWREATH_COLLECT_ID, XMASTREE_PLACE_ID, COOKIE_EAT_ID)).setReward(SNOW_SURFACE_REWARD));

        //Snow cave
        POTATO_KILL_ID = registerChallenge("potatokilled", new CaveKillJournalChallenge(20, "cropplerpotato"));
        POTATO_PLACE_ID = registerChallenge("potatoplanted", new CavePlaceJournalChallenge(20, "potatoseed"));

        SNOW_CAVE_CHALLENGES_ID = registerChallenge("summonsnowcave", (new MultiJournalChallenge(POTATO_KILL_ID, POTATO_PLACE_ID)).setReward(SNOW_CAVE_REWARD));

        //Snow deep cave
        PUMPKIN_KILL_ID = registerChallenge("pumpkinkilled", new CaveKillJournalChallenge(20, "cropplerpumpkin"));
        PUMPKIN_PLACE_ID = registerChallenge("pumpkinplanted", new CavePlaceJournalChallenge(20, "pumpkinseed"));

        SNOW_DEEP_CAVE_CHALLENGES_ID = registerChallenge("summonsnowdeepcave", (new MultiJournalChallenge(PUMPKIN_KILL_ID, PUMPKIN_PLACE_ID)).setReward(SNOW_DEEP_CAVE_REWARD));

        //Dungeon
        TRAP_REVERSE_COLLECT_ID = registerChallenge("trapreversecollected", new PickupItemsJournalChallenge(3, true, "glyphtrapreversedamage"));
        TRAP_CHICKEN_COLLECT_ID = registerChallenge("trapchickencollected", new PickupItemsJournalChallenge(3, true, "glyphtrapchicken"));
        TRAP_BOUNCY_COLLECT_ID = registerChallenge("trapbouncycollected", new PickupItemsJournalChallenge(3, true, "glyphtrapbounce"));
        DUNGEON_CHALLENGES_ID = registerChallenge("summondungeon", (new MultiJournalChallenge(TRAP_REVERSE_COLLECT_ID, TRAP_CHICKEN_COLLECT_ID, TRAP_BOUNCY_COLLECT_ID)).setReward(DUNGEON_REWARD));

        //Plains
        FIREFLY_COLLECT_ID = registerChallenge("fireflycollected", new PickupItemsJournalChallenge(1, true, "fireflyyellow", "fireflygreen", "fireflyblue"));
        EGG_PLACE_ID = registerChallenge("eggplaced", new FoodConsumedJournalChallenge("friedegg"));
        KATANA_DESTROY_ID = registerChallenge("katanastonedestroyed", new ObjectDestroyedJournalChallenge("katanastone"));
        WHITEFLOWER_PLACE_ID = registerChallenge("whiteflowerplaced", new ObjectsPlacedJournalChallenge(15, "whiteflowerpatch"));
        PLAINS_SURFACE_CHALLENGES_ID = registerChallenge("summonplainssurface", (new MultiJournalChallenge(FIREFLY_COLLECT_ID, EGG_PLACE_ID, KATANA_DESTROY_ID, WHITEFLOWER_PLACE_ID)).setReward(PLAINS_SURFACE_REWARD));

        //Plains cave

        //Plains deep cave

        //Swamp

        //Swamp cave
        SPORE_KILL_ID = registerChallenge("sporekilled", new MobsKilledJournalChallenge(10, "swampsporedummy"));
        MUSHROOM_COLLECT_ID = registerChallenge("mushroomcollected", new PickupItemsJournalChallenge(50, true, "mushroom"));
        MUD_COLLECT_ID = registerChallenge("mudcollected", new PickupItemsJournalChallenge(100, true, "mudtile"));
        STINKFLASK_COLLECT_ID = registerChallenge("stinkflaskcollected", new PickupItemJournalChallenge(true, "stinkflask"));
        SWAMP_CAVE_CHALLENGES_ID = registerChallenge("summonswampcave", (new MultiJournalChallenge(SPORE_KILL_ID, MUSHROOM_COLLECT_ID, MUD_COLLECT_ID, STINKFLASK_COLLECT_ID)).setReward(SWAMP_CAVE_REWARD));

        //Swamp deep cave
        FISHIAN_KILL_ID = registerChallenge("fishiankilled", new MobsKilledJournalChallenge(40, "fishianhookwarrior", "fishianhealer", "fishianshaman", "staticjellyfish"));
        BAMBOO_COLLECT_ID = registerChallenge("bamboocollected", new PickupItemsJournalChallenge(100, true, "bamboo"));
        SHELL_DESTROY_ID = registerChallenge("shelldestroyed", new ObjectsDestroyedJournalChallenge(30, true, "seashell", "seasnail", "seastar"));
        SWAMP_DEEP_CAVE_CHALLENGES_ID = registerChallenge("summondeepswampcave", (new MultiJournalChallenge(FISHIAN_KILL_ID, BAMBOO_COLLECT_ID, SHELL_DESTROY_ID)).setReward(SWAMP_DEEP_CAVE_REWARD));

        //Desert

        //Desert cave
        CARROT_KILL_ID = registerChallenge("carrotkilled", new CaveKillJournalChallenge(20, "cropplercarrot"));
        CARROT_PLACE_ID = registerChallenge("carrotplanted", new CavePlaceJournalChallenge(20, "carrotseed"));

        DESERT_CAVE_CHALLENGES_ID = registerChallenge("summondesertcave", (new MultiJournalChallenge(CARROT_KILL_ID, CARROT_PLACE_ID)).setReward(DESERT_CAVE_REWARD));

        //Desert deep cave

        //Pirate Village

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