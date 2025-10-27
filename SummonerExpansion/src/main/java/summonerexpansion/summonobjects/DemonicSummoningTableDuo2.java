package summonerexpansion.summonobjects;

import java.awt.Color;
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
        this.texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/demonicsummoningtableduo");
    }
}