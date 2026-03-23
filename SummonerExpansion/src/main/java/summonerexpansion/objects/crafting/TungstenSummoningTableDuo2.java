package summonerexpansion.objects.crafting;

import necesse.gfx.gameTexture.GameTexture;

import java.awt.*;

public class TungstenSummoningTableDuo2 extends SummoningTableDuo2
{
    public TungstenSummoningTableDuo2()
    {
        mapColor = new Color(97, 95, 132);
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/crafting/tungstensummoningtableduo");
    }
}