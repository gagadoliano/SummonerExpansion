package summonerexpansion.summonchallenges;

import necesse.engine.journal.JournalChallengeUtils;
import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.journal.ObjectsPlacedJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;

import java.awt.*;

public class BeetPlaceJournalChallenge extends ObjectsPlacedJournalChallenge
{
    public BeetPlaceJournalChallenge()
    {
        super(20, "beetseed");
    }

    @Override
    public void onObjectPlaced(GameObject object, Level level, int layerID, int tileX, int tileY, int objectRotation, ServerClient client)
    {
        if (level.isCave)
        {
            super.onObjectPlaced(object, level, layerID, tileX, tileY, objectRotation, client);
        }
    }
}