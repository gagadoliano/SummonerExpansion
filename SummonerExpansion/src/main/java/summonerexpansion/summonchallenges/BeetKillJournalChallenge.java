package summonerexpansion.summonchallenges;

import necesse.engine.journal.JournalChallengeUtils;
import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
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
        if (JournalChallengeUtils.isForestBiome(level.biome) && level.isCave)
        {
            super.onMobKilled(serverClient, mob);
        }
    }
}