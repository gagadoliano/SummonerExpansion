package summonerexpansion.codes.registry;

import necesse.engine.journal.JournalEntry;
import necesse.engine.registries.JournalRegistry;

public class SummonerJournal
{
    public static void registerSummonJournal()
    {
        // Journal
        JournalEntry forestSurfaceJournal = JournalRegistry.getJournalEntry("forestsurface");
        forestSurfaceJournal.addEntryChallenges(SummonerChallenges.SUMMON_FOREST_SURFACE_CHALLENGES_ID);

        JournalEntry forestCaveJournal = JournalRegistry.getJournalEntry("forestcave");
        forestCaveJournal.addEntryChallenges(SummonerChallenges.SUMMON_FOREST_CAVE_CHALLENGES_ID);

        JournalEntry forestDeepCaveJournal = JournalRegistry.getJournalEntry("forestdeepcave");
        forestDeepCaveJournal.addEntryChallenges(SummonerChallenges.SUMMON_FOREST_DEEP_CAVE_CHALLENGES_ID);

        JournalEntry pirateVillageJournal = JournalRegistry.getJournalEntry("forestpiratevillage");

        JournalEntry snowSurfaceJournal = JournalRegistry.getJournalEntry("snowsurface");
        snowSurfaceJournal.addEntryChallenges(SummonerChallenges.SUMMON_SNOW_SURFACE_CHALLENGES_ID);

        JournalEntry snowCaveJournal = JournalRegistry.getJournalEntry("snowcave");
        snowCaveJournal.addEntryChallenges(SummonerChallenges.SUMMON_SNOW_CAVE_CHALLENGES_ID);

        JournalEntry snowDeepCaveJournal = JournalRegistry.getJournalEntry("snowdeepcave");
        snowDeepCaveJournal.addEntryChallenges(SummonerChallenges.SUMMON_SNOW_DEEP_CAVE_CHALLENGES_ID);

        JournalEntry dungeonJournal = JournalRegistry.getJournalEntry("dungeon");
        dungeonJournal.addEntryChallenges(SummonerChallenges.SUMMON_DUNGEON_CHALLENGES_ID);

        JournalEntry plainsSurfaceJournal = JournalRegistry.getJournalEntry("plainssurface");
        plainsSurfaceJournal.addEntryChallenges(SummonerChallenges.SUMMON_PLAINS_SURFACE_CHALLENGES_ID);

        JournalEntry plainsCaveJournal = JournalRegistry.getJournalEntry("plainscave");

        JournalEntry plainsDeepCaveJournal = JournalRegistry.getJournalEntry("plainsdeepcave");

        JournalEntry swampSurfaceJournal = JournalRegistry.getJournalEntry("swampsurface");

        JournalEntry swampCave = JournalRegistry.getJournalEntry("swampcave");
        swampCave.addEntryChallenges(SummonerChallenges.SUMMON_SWAMP_CAVE_CHALLENGES_ID);

        JournalEntry swampDeepCave = JournalRegistry.getJournalEntry("swampdeepcave");
        swampDeepCave.addEntryChallenges(SummonerChallenges.SUMMON_SWAMP_DEEP_CAVE_CHALLENGES_ID);

        JournalEntry desertSurface = JournalRegistry.getJournalEntry("desertsurface");

        JournalEntry desertCaveJournal = JournalRegistry.getJournalEntry("desertcave");
        desertCaveJournal.addEntryChallenges(SummonerChallenges.SUMMON_DESERT_CAVE_CHALLENGES_ID);

        JournalEntry desertDeepCaveJournal = JournalRegistry.getJournalEntry("desertdeepcave");
    }
}