package summonerexpansion.codes.registries;

import necesse.entity.mobs.HumanTexture;
import necesse.gfx.gameTexture.GameTexture;

public class RegistryMobTextures
{
    // Mobs
    public static GameTexture sandGiantScorpion;
    public static GameTexture sandScorpion;
    public static GameTexture lavaShark;
    public static GameTexture honeyBeeGuard;
    public static GameTexture woodIdol;
    public static GameTexture horrorSpirit;
    public static GameTexture poisonSwampSlime;
    public static GameTexture poisonSwampSlimeWorm;
    public static HumanTexture voidSwampZombie;


    // Miniboss
    public static GameTexture horrorSpiritBoss;
    public static HumanTexture dollMaker;

    // Boss
    public static GameTexture bossRiftPortal;

    public static void initResources()
    {
        // Mobs
        honeyBeeGuard = GameTexture.fromFile("mobs/foes/honeybeeguardmob");
        sandGiantScorpion = GameTexture.fromFile("mobs/foes/sandgiantscorpionmob");
        sandScorpion = GameTexture.fromFile("mobs/foes/sandscorpionmob");
        lavaShark = GameTexture.fromFile("mobs/foes/lavacavesharkmob");
        woodIdol = GameTexture.fromFile("mobs/foes/woodmob");
        horrorSpirit = GameTexture.fromFile("mobs/foes/horrorspiritmob");
        poisonSwampSlime = GameTexture.fromFile("mobs/foes/poisonswampslime");
        poisonSwampSlimeWorm = GameTexture.fromFile("mobs/foes/swampslimeworm");
        voidSwampZombie = new HumanTexture(GameTexture.fromFile("mobs/foes/voidswampzombie"), GameTexture.fromFile("mobs/foes/voidswampzombiearms_left"), GameTexture.fromFile("mobs/foes/voidswampzombiearms_right"));


        // Miniboss
        horrorSpiritBoss = GameTexture.fromFile("mobs/foes/horrorspiritbossmob");
        dollMaker = new HumanTexture(GameTexture.fromFile("mobs/foes/dollmakermob"), GameTexture.fromFile("mobs/foes/dollmakermobarms_left"), GameTexture.fromFile("mobs/foes/dollmakermobarms_right"));

        // Boss
        bossRiftPortal = GameTexture.fromFile("mobs/foes/boss/riftportalmob");
    }
}