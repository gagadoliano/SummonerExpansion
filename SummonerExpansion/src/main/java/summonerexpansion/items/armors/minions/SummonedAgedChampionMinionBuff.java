package summonerexpansion.items.armors.minions;

import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;
import necesse.gfx.gameTexture.GameTexture;

public class SummonedAgedChampionMinionBuff extends SummonedCountBuff
{
    public SummonedAgedChampionMinionBuff()
    {
        canCancel = false;
        isVisible = true;
    }

    public void loadTextures()
    {
        this.iconTexture = GameTexture.fromFile("buffs/summoned/" + getStringID());
    }
}