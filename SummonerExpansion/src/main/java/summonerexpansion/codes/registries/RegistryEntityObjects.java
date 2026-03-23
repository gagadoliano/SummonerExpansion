package summonerexpansion.codes.registries;

import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ObjectRegistry;
import summonerexpansion.objects.entities.*;

public class RegistryEntityObjects
{
    public static void registerObjects()
    {
        RegistryEntityObjects.registerDummies();
    }

    public static void registerDummies()
    {
        ObjectRegistry.registerObject("tanktrainingdummy", new TankTrainingDummyObject(), 50, true);
        MobRegistry.registerMob("tanktrainingdummy", TankTrainingDummyMob.class, false, false, new LocalMessage("object", "trainingdummy"), null);
    }
}