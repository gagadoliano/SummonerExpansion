package summonerexpansion.items.trinkets.summonedbuffs;

import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;
import necesse.gfx.gameTexture.GameTexture;

public class SummonedBeetMinionBuff extends SummonedCountBuff
{
    public SummonedBeetMinionBuff()
    {
        canCancel = false;
    }

    public void loadTextures()
    {
        this.iconTexture = GameTexture.fromFile("buffs/summoned/" + getStringID());
    }
}