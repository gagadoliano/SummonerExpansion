package summonerexpansion.summonobjects;

import java.awt.Color;
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
        this.texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/fallensummoningtableduo");
    }
}