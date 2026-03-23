package summonerexpansion.codes.registries;

import necesse.engine.journal.JournalEntry;
import necesse.engine.registries.JournalRegistry;

public class RegistryJournal
{
    public static void registerJournal()
    {
        // Journal
        JournalEntry forestSurfaceJournal = JournalRegistry.getJournalEntry("forestsurface");
        forestSurfaceJournal.addEntryChallenges(RegistryChallenges.FOREST_SURFACE_CHALLENGES_ID);
        forestSurfaceJournal.addEntryChallenges(RegistryChallenges.FOREST_SURFACE_CHALLENGES_ID2);

        JournalEntry forestCaveJournal = JournalRegistry.getJournalEntry("forestcave");
        forestCaveJournal.addEntryChallenges(RegistryChallenges.FOREST_CAVE_CHALLENGES_ID);

        JournalEntry forestDeepCaveJournal = JournalRegistry.getJournalEntry("forestdeepcave");
        forestDeepCaveJournal.addEntryChallenges(RegistryChallenges.FOREST_DEEP_CAVE_CHALLENGES_ID);

        JournalEntry pirateVillageJournal = JournalRegistry.getJournalEntry("forestpiratevillage");

        JournalEntry snowSurfaceJournal = JournalRegistry.getJournalEntry("snowsurface");
        snowSurfaceJournal.addEntryChallenges(RegistryChallenges.SNOW_SURFACE_CHALLENGES_ID);

        JournalEntry snowCaveJournal = JournalRegistry.getJournalEntry("snowcave");
        snowCaveJournal.addEntryChallenges(RegistryChallenges.SNOW_CAVE_CHALLENGES_ID);

        JournalEntry snowDeepCaveJournal = JournalRegistry.getJournalEntry("snowdeepcave");
        snowDeepCaveJournal.addEntryChallenges(RegistryChallenges.SNOW_DEEP_CAVE_CHALLENGES_ID);

        JournalEntry dungeonJournal = JournalRegistry.getJournalEntry("dungeon");
        dungeonJournal.addEntryChallenges(RegistryChallenges.DUNGEON_CHALLENGES_ID);

        JournalEntry plainsSurfaceJournal = JournalRegistry.getJournalEntry("plainssurface");
        plainsSurfaceJournal.addEntryChallenges(RegistryChallenges.PLAINS_SURFACE_CHALLENGES_ID);

        JournalEntry plainsCaveJournal = JournalRegistry.getJournalEntry("plainscave");

        JournalEntry plainsDeepCaveJournal = JournalRegistry.getJournalEntry("plainsdeepcave");

        JournalEntry swampSurfaceJournal = JournalRegistry.getJournalEntry("swampsurface");

        JournalEntry swampCave = JournalRegistry.getJournalEntry("swampcave");
        swampCave.addEntryChallenges(RegistryChallenges.SWAMP_CAVE_CHALLENGES_ID);

        JournalEntry swampDeepCave = JournalRegistry.getJournalEntry("swampdeepcave");
        swampDeepCave.addEntryChallenges(RegistryChallenges.SWAMP_DEEP_CAVE_CHALLENGES_ID);

        JournalEntry desertSurface = JournalRegistry.getJournalEntry("desertsurface");

        JournalEntry desertCaveJournal = JournalRegistry.getJournalEntry("desertcave");
        desertCaveJournal.addEntryChallenges(RegistryChallenges.DESERT_CAVE_CHALLENGES_ID);

        JournalEntry desertDeepCaveJournal = JournalRegistry.getJournalEntry("desertdeepcave");
    }
}