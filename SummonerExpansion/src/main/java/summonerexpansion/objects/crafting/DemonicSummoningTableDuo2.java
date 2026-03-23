package summonerexpansion.objects.crafting;

import necesse.gfx.gameTexture.GameTexture;

import java.awt.*;

public class DemonicSummoningTableDuo2 extends SummoningTableDuo2
{
    public DemonicSummoningTableDuo2()
    {
        mapColor = new Color(30, 30, 30);
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/crafting/demonicsummoningtableduo");
    }
}