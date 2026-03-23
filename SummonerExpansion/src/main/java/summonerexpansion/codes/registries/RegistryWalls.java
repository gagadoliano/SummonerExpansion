package summonerexpansion.codes.registries;

import necesse.engine.registries.ObjectRegistry;
import summonerexpansion.objects.walls.*;

import java.awt.*;

public class RegistryWalls
{
    public static void registerSummonObjects()
    {
        RegistryWalls.registerRockWalls();
        RegistryWalls.registerFences();
    }

    public static void registerRockWalls()
    {
        BaseRockWallObject empoweredsapphirerock;
        ObjectRegistry.registerObject("empoweredsapphirerock", empoweredsapphirerock = new BaseRockWallObject("empoweredsapphirerock", new Color(69, 76, 94), "sapphire", 0, 3, 1), 1F, true);
        empoweredsapphirerock.toolTier = 9F;
        empoweredsapphirerock.objectHealth = 300;

        BaseRockWallObject empoweredamethystrock;
        ObjectRegistry.registerObject("empoweredamethystrock", empoweredamethystrock = new BaseRockWallObject("empoweredamethystrock", new Color(78, 60, 83), "amethyst", 0, 3, 1), 1F, true);
        empoweredamethystrock.toolTier = 9F;
        empoweredamethystrock.objectHealth = 300;

        BaseRockWallObject empoweredemeraldrock;
        ObjectRegistry.registerObject("empoweredemeraldrock", empoweredemeraldrock = new BaseRockWallObject("empoweredemeraldrock", new Color(84, 70, 58), "emerald", 0, 3, 1), 1F, true);
        empoweredemeraldrock.toolTier = 9F;
        empoweredemeraldrock.objectHealth = 300;

        BaseRockWallObject empoweredtopazrock;
        ObjectRegistry.registerObject("empoweredtopazrock", empoweredtopazrock = new BaseRockWallObject("empoweredtopazrock", new Color(101, 73, 62), "topaz", 0, 3, 1), 1F, true);
        empoweredtopazrock.toolTier = 9F;
        empoweredtopazrock.objectHealth = 300;

        BaseRockWallObject empoweredrubyrock;
        ObjectRegistry.registerObject("empoweredrubyrock", empoweredrubyrock = new BaseRockWallObject("empoweredrubyrock", new Color(97, 55, 58), "ruby", 0, 3, 1), 1F, true);
        empoweredrubyrock.toolTier = 9F;
        empoweredrubyrock.objectHealth = 300;
    }

    public static void registerFences()
    {
        int ancientWoodFenceID = ObjectRegistry.registerObject("ancientwoodfence", new BaseFenceObject("ancientwoodfence", new Color(88, 38, 56), 12, 10, -26), 0F, true);
        BaseFenceGateObject.registerGatePair(ancientWoodFenceID, "ancientwoodfencegate", "ancientwoodfencegate", new Color(88, 38, 56), 12, 10, 0F);
    }
}