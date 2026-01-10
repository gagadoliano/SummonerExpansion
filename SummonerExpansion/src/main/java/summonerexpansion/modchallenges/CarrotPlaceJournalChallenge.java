package summonerexpansion.modchallenges;

import necesse.engine.journal.ObjectsPlacedJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;

public class CarrotPlaceJournalChallenge extends ObjectsPlacedJournalChallenge
{
    public CarrotPlaceJournalChallenge()
    {
        super(20, "carrotseed");
    }

    public void onObjectPlaced(GameObject object, Level level, int layerID, int tileX, int tileY, int objectRotation, ServerClient client)
    {
        if (level.isCave)
        {
            super.onObjectPlaced(object, level, layerID, tileX, tileY, objectRotation, client);
        }
    }
}