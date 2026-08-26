package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;

import java.io.FileNotFoundException;

public class TitaniumShardsStacksBuff extends Buff
{
    protected int manaNeeded = 10;

    public TitaniumShardsStacksBuff()
    {
        isImportant = true;
        isVisible = true;
    }

    public void setManaNeeded(int value) {
        manaNeeded = value;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public int getStackSize(ActiveBuff buff) {
        return 10;
    }

    public void loadTextures()
    {
        try
        {
            iconTexture = GameTexture.fromFileRaw("buffs/manaspent");
        }
        catch (FileNotFoundException var2)
        {
            iconTexture = GameTexture.fromFile("buffs/unknown");
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltip = super.getTooltip(ab, blackboard);
        tooltip.add(Localization.translate("bufftooltip", "titaniumshardbufftip", "value", manaNeeded));
        return tooltip;
    }
}