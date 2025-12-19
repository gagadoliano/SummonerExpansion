package summonerexpansion.modchallenges;

import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;

public class DuckKillJournalChallenge extends MobsKilledJournalChallenge
{
    public DuckKillJournalChallenge() {
        super(20, "duck");
    }

    public void onMobKilled(ServerClient serverClient, Mob mob)
    {
        Level level = mob.getLevel();
        if (!level.isCave)
        {
            super.onMobKilled(serverClient, mob);
        }
    }
}