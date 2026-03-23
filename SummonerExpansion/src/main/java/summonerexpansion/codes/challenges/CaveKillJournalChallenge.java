package summonerexpansion.codes.challenges;

import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;

public class CaveKillJournalChallenge extends MobsKilledJournalChallenge
{
    public CaveKillJournalChallenge(int killCount, String killMob)
    {
        super(killCount, killMob);
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