package summonerexpansion.objects.summonobjects;

import java.awt.Color;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.ObjectDamagedTextureArray;

public class TungstenSummoningTableDuo2 extends SummoningTableDuo2
{
    public TungstenSummoningTableDuo2()
    {
        mapColor = new Color(97, 95, 132);
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/tungstensummoningtableduo");
    }
}