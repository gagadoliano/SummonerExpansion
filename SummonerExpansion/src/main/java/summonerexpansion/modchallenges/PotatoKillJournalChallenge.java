package summonerexpansion.modchallenges;

import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;

public class PotatoKillJournalChallenge extends MobsKilledJournalChallenge
{
    public PotatoKillJournalChallenge() {
        super(20, "cropplerpotato");
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
