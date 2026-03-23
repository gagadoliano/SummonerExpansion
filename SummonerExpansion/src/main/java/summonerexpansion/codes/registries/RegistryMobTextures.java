package summonerexpansion.codes.registries;

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

    // Miniboss
    public static GameTexture horrorSpiritBoss;

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

        // Miniboss
        horrorSpiritBoss = GameTexture.fromFile("mobs/foes/horrorspiritbossmob");

        // Boss
        bossRiftPortal = GameTexture.fromFile("mobs/foes/boss/riftportalmob");
        //BossRiftPortalMob.icon =  GameTexture.fromFile("mobicons/riftportalicon");
    }
}
