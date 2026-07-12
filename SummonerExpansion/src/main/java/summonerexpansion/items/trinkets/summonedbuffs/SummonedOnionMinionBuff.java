package summonerexpansion.items.trinkets.summonedbuffs;

import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;
import necesse.gfx.gameTexture.GameTexture;

public class SummonedOnionMinionBuff extends SummonedCountBuff
{
    public SummonedOnionMinionBuff()
    {
        canCancel = false;
    }

    public void loadTextures()
    {
        this.iconTexture = GameTexture.fromFile("buffs/summoned/" + getStringID());
    }
}
