package summonerexpansion.items.trinkets.summonedbuffs;

import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;
import necesse.gfx.gameTexture.GameTexture;

public class SummonedCarrotMinionBuff extends SummonedCountBuff
{
    public SummonedCarrotMinionBuff()
    {
        canCancel = false;
    }

    public void loadTextures()
    {
        this.iconTexture = GameTexture.fromFile("buffs/summoned/" + getStringID());
    }
}