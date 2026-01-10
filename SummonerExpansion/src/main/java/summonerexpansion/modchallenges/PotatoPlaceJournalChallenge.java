package summonerexpansion.modchallenges;

import necesse.engine.journal.ObjectsPlacedJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;

public class PotatoPlaceJournalChallenge extends ObjectsPlacedJournalChallenge
{
    public PotatoPlaceJournalChallenge()
    {
        super(20, "potatoseed");
    }

    public void onObjectPlaced(GameObject object, Level level, int layerID, int tileX, int tileY, int objectRotation, ServerClient client)
    {
        if (level.isCave)
        {
            super.onObjectPlaced(object, level, layerID, tileX, tileY, objectRotation, client);
        }
    }
}