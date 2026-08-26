package summonerexpansion.items.armors.minions;

import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;
import necesse.gfx.gameTexture.GameTexture;

public class SummonedSharpshooterMinionBuff extends SummonedCountBuff
{
    public SummonedSharpshooterMinionBuff()
    {
        canCancel = false;
        isVisible = true;
    }

    public void loadTextures()
    {
        this.iconTexture = GameTexture.fromFile("buffs/summoned/" + getStringID());
    }
}