package summonerexpansion.items.trinkets.summonedbuffs;

import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;
import necesse.gfx.gameTexture.GameTexture;

public class SummonedLivingArmorStandMinionBuff extends SummonedCountBuff
{
    public SummonedLivingArmorStandMinionBuff()
    {
        canCancel = false;
    }

    public void loadTextures()
    {
        this.iconTexture = GameTexture.fromFile("buffs/summoned/" + getStringID());
    }
}