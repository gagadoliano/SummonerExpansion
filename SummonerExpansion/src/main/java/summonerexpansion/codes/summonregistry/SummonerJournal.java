package summonerexpansion.codes.summonregistry;

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

        JournalEntry snowSurfaceJournal = JournalRegistry.getJournalEntry("snowsurface");
        snowSurfaceJournal.addEntryChallenges(SummonerChallenges.SUMMON_SNOW_SURFACE_CHALLENGES_ID);

        JournalEntry swampCave = JournalRegistry.getJournalEntry("swampcave");
        swampCave.addEntryChallenges(SummonerChallenges.SUMMON_SWAMP_CAVE_CHALLENGES_ID);

        JournalEntry swampDeepCave = JournalRegistry.getJournalEntry("swampdeepcave");
        swampDeepCave.addEntryChallenges(SummonerChallenges.SUMMON_DEEP_SWAMP_CAVE_CHALLENGES_ID);
    }
}