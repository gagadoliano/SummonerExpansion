package summonerexpansion.objects.summonobjects;

import java.awt.Color;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.ObjectDamagedTextureArray;

public class DemonicSummoningTableDuo2 extends SummoningTableDuo2
{
    public DemonicSummoningTableDuo2()
    {
        mapColor = new Color(30, 30, 30);
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/demonicsummoningtableduo");
    }
}