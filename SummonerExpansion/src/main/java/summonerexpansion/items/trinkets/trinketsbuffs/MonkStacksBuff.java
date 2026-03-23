package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;

import java.io.FileNotFoundException;

public class MonkStacksBuff extends Buff
{
    private GameTexture lowStacksTexture;
    private GameTexture maxStacksTexture;

    public MonkStacksBuff()
    {
        canCancel = false;
        isImportant = true;
        isPassive = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.STAMINA_CAPACITY, 0.01F);
    }

    public GameTexture getDrawIcon(ActiveBuff buff)
    {
        return buff.getStacks() == 50 ? maxStacksTexture : lowStacksTexture;
    }

    public void loadTextures()
    {
        super.loadTextures();
        try
        {
            lowStacksTexture = GameTexture.fromFileRaw("buffs/monkstackshalfbuff");
            maxStacksTexture = GameTexture.fromFileRaw("buffs/monkstacksfullbuff");
        }
        catch (FileNotFoundException var2)
        {
            lowStacksTexture = GameTexture.fromFile("buffs/unknown");
            maxStacksTexture = GameTexture.fromFile("buffs/unknown");
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return 50;
    }

    public boolean overridesStackDuration() {
        return true;
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("bufftooltip", "monkstackstip"));
        return tooltips;
    }
}