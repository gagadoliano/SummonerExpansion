package summonerexpansion.summonothers;

import necesse.engine.registries.ObjectRegistry;
import summonerexpansion.summonobjects.*;

public class SummonerObjects
{
    public static void registerSummonObjects()
    {
        ObjectRegistry.registerObject("tanktrainingdummy", new TankTrainingDummyObject(), 50, true);
        ObjectRegistry.registerObject("summoningbookshelf", new SummoningBookshelf(), 50, true);
        ObjectRegistry.registerObject("bannerofwater", new BannerOfWater(), 100, true);
    }
}