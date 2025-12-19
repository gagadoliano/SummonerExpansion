package summonerexpansion.buffs;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;

import java.io.FileNotFoundException;

public class CopperSetConsecutiveBuff extends Buff
{
    private GameTexture tip1;
    private GameTexture tip2;
    private GameTexture tip3;
    private GameTexture tip4;

    public CopperSetConsecutiveBuff()
    {
        isImportant = true;
        isVisible = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public int getStackSize(ActiveBuff buff) {
        return 999999;
    }

    public boolean overridesStackDuration() {
        return true;
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }

    public GameTexture getDrawIcon(ActiveBuff buff)
    {
        int consecutiveShots = buff.getStacks();
        if (consecutiveShots < 20)
        {
            return tip1;
        }
        else if (consecutiveShots < 100)
        {
            return tip2;
        }
        else
        {
            return consecutiveShots < 200 ? tip3 : tip4;
        }
    }

    public void loadTextures()
    {
        super.loadTextures();
        try
        {
            tip1 = GameTexture.fromFileRaw("buffs/coppersettip1");
            tip2 = GameTexture.fromFileRaw("buffs/coppersettip2");
            tip3 = GameTexture.fromFileRaw("buffs/coppersettip3");
            tip4 = GameTexture.fromFileRaw("buffs/coppersettip4");
        }
        catch (FileNotFoundException var2)
        {
            tip1 = GameTexture.fromFile("buffs/unknown");
            tip2 = GameTexture.fromFile("buffs/unknown");
            tip3 = GameTexture.fromFile("buffs/unknown");
            tip4 = GameTexture.fromFile("buffs/unknown");
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        int consecutiveExplosions = ab.getStacks();
        String tooltipText = Localization.translate("bufftooltip", "copperbufftip", "value", consecutiveExplosions);
        tooltips.add(tooltipText, 300);
        return tooltips;
    }
}