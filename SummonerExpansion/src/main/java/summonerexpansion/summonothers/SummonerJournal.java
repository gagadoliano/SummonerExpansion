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
        forestCaveJournal.addTreasureEntry("magiccopperlamp");
        forestCaveJournal.addTreasureEntry("minionattackspeedpotion");
        forestCaveJournal.addTreasureEntry("minioncloserangepotion");
        forestCaveJournal.addTreasureEntry("minioncritchancepotion");
        forestCaveJournal.addTreasureEntry("minionrangepotion");
        forestCaveJournal.addTreasureEntry("minionspeedpotion");
        forestCaveJournal.addTreasureEntry("minioncritpotion");
        forestCaveJournal.addTreasureEntry("minionfarmpotion");

        JournalEntry plainsSurfaceJournal = JournalRegistry.getJournalEntry("plainssurface");
        plainsSurfaceJournal.addTreasureEntry("royalhive");

        JournalEntry forestDeepCaveJournal = JournalRegistry.getJournalEntry("forestdeepcave");
        forestDeepCaveJournal.addMobEntries("horrorspiritmob");

        JournalEntry templeJournal = JournalRegistry.getJournalEntry("temple");
        templeJournal.addTreasureEntry("sandwormstaff");
    }
}