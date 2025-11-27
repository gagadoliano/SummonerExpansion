package summonerexpansion.objects.summonobjects;

import java.awt.Color;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.ObjectDamagedTextureArray;

public class FallenSummoningTableDuo2 extends SummoningTableDuo2
{
    public FallenSummoningTableDuo2()
    {
        mapColor = new Color(0, 107, 109);
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/fallensummoningtableduo");
    }
}