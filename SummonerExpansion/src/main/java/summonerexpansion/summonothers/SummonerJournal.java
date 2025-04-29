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

        JournalEntry plainsSurfaceJournal = JournalRegistry.getJournalEntry("plainssurface");
        plainsSurfaceJournal.addTreasureEntry("royalhive");

        JournalEntry forestDeepCaveJournal = JournalRegistry.getJournalEntry("forestdeepcave");
        forestDeepCaveJournal.addMobEntries("horrorspiritmob");

        JournalEntry templeJournal = JournalRegistry.getJournalEntry("temple");
        templeJournal.addTreasureEntry("sandwormstaff");
    }
}