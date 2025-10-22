package summonerexpansion.summonchallenges;

import necesse.engine.journal.JournalChallengeUtils;
import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;

public class FishianKillJournalChallenge extends MobsKilledJournalChallenge
{
    public FishianKillJournalChallenge()
    {
        super(40, "fishianhookwarrior", "fishianhealer", "fishianshaman", "staticjellyfish");
    }

    public void onMobKilled(ServerClient serverClient, Mob mob)
    {
        Level level = mob.getLevel();
        if (level.isCave)
        {
            super.onMobKilled(serverClient, mob);
        }
    }
}