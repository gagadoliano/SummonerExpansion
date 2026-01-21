package summonerexpansion.buffs.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;

import java.io.FileNotFoundException;

public class GenieCritBuff extends Buff 
{
    private GameTexture lowStacksTexture;
    private GameTexture maxStacksTexture;

    public GenieCritBuff() 
    {
        canCancel = false;
        isImportant = true;
        isPassive = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {

    }

    public void onStacksUpdated(ActiveBuff buff, ActiveBuff other)
    {
        super.onStacksUpdated(buff, other);
        if (buff.getStacks() == 20)
        {
            buff.setMinModifier(BuffModifiers.SUMMON_CRIT_CHANCE, 1.00F);
            buff.setModifier(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.05F);
        }
    }

    public void onUpdate(ActiveBuff buff)
    {
        if (!buff.owner.buffManager.hasBuff("geniewishbuff"))
        {
            buff.remove();
        }
    }

    public GameTexture getDrawIcon(ActiveBuff buff)
    {
        return buff.getStacks() == 20 ? maxStacksTexture : lowStacksTexture;
    }

    public void loadTextures()
    {
        super.loadTextures();
        try
        {
            lowStacksTexture = GameTexture.fromFileRaw("buffs/geniewishstack");
            maxStacksTexture = GameTexture.fromFileRaw("buffs/geniewishmaxstack");
        }
        catch (FileNotFoundException var2)
        {
            lowStacksTexture = GameTexture.fromFile("buffs/unknown");
            maxStacksTexture = GameTexture.fromFile("buffs/unknown");
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return 20;
    }

    public boolean overridesStackDuration() {
        return true;
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("bufftooltip", "geniecrittip"));
        return tooltips;
    }
}