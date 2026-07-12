package summonerexpansion.items.trinkets.summonedbuffs;

import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;
import necesse.gfx.gameTexture.GameTexture;

public class SummonedPumpkinMinionBuff extends SummonedCountBuff
{
    public SummonedPumpkinMinionBuff()
    {
        canCancel = false;
    }

    public void loadTextures()
    {
        this.iconTexture = GameTexture.fromFile("buffs/summoned/" + getStringID());
    }
}
