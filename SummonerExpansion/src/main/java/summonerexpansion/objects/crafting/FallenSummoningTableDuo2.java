package summonerexpansion.objects.crafting;

import necesse.gfx.gameTexture.GameTexture;

import java.awt.*;

public class FallenSummoningTableDuo2 extends SummoningTableDuo2
{
    public FallenSummoningTableDuo2()
    {
        mapColor = new Color(0, 107, 109);
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/crafting/fallensummoningtableduo");
    }
}