package summonerexpansion.summonchallenges;

import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;

import java.awt.*;

public class BeetKillJournalChallenge extends MobsKilledJournalChallenge
{
    public BeetKillJournalChallenge() {
        super(20, "beetcavecroppler");
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