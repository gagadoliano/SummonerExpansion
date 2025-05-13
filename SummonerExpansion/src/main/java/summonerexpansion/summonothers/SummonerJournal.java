package summonerexpansion.summonothers;

import necesse.engine.journal.JournalEntry;
import necesse.engine.registries.JournalRegistry;

public class SummonerJournal
{
    public static void registerSummonerJournal()
    {
        // Journal
        JournalEntry forestSurfaceJournal = JournalRegistry.getJournalEntry("forestsurface");
        forestSurfaceJournal.addEntryChallenges(SummonerChallenges.SUMMON_FOREST_SURFACE_CHALLENGES_ID);

        JournalEntry forestCaveJournal = JournalRegistry.getJournalEntry("forestcave");
        forestCaveJournal.addTreasureEntry("magiccopperlamp", "minionattackspeedpotion", "minioncloserangepotion", "minioncritchancepotion", "minionrangepotion", "minionspeedpotion", "minioncritpotion", "minionfarmpotion");
        forestCaveJournal.addEntryChallenges(SummonerChallenges.SUMMON_FOREST_CAVES_CHALLENGES_ID);

        JournalEntry snowSurfaceJournal = JournalRegistry.getJournalEntry("snowsurface");
        snowSurfaceJournal.addEntryChallenges(SummonerChallenges.SUMMON_SNOW_SURFACE_CHALLENGES_ID);

        JournalEntry plainsSurfaceJournal = JournalRegistry.getJournalEntry("plainssurface");
        plainsSurfaceJournal.addTreasureEntry("royalhive");

        JournalEntry forestDeepCaveJournal = JournalRegistry.getJournalEntry("forestdeepcave");
        forestDeepCaveJournal.addMobEntries("horrorspiritmob");

        JournalEntry templeJournal = JournalRegistry.getJournalEntry("temple");
        templeJournal.addTreasureEntry("sandwormstaff");
    }
}