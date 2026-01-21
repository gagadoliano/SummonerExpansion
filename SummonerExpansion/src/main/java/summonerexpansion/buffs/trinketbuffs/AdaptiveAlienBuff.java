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

public class AdaptiveAlienBuff extends Buff
{
    private GameTexture Texture1;
    private GameTexture Texture2;
    private GameTexture Texture3;
    private GameTexture Texture4;

    public AdaptiveAlienBuff()
    {
        canCancel = false;
        isImportant = true;
        isPassive = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0.05F);
        buff.setModifier(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.01F);
        buff.setModifier(BuffModifiers.STAMINA_CAPACITY, 0.02F);
        buff.setModifier(BuffModifiers.MAX_MANA, 0.01F);
        buff.setModifier(BuffModifiers.MAX_HEALTH, -0.02F);
        buff.setModifier(BuffModifiers.MAX_RESILIENCE, -0.01F);
    }

    public void onUpdate(ActiveBuff buff)
    {
        if (!buff.owner.buffManager.hasBuff("alienparasitebuff"))
        {
            buff.remove();
        }
    }

    public GameTexture getDrawIcon(ActiveBuff buff)
    {
        if (buff.getStacks() >= 20)
        {
            return Texture4;
        }
        else if (buff.getStacks() >= 13)
        {
            return Texture3;
        }
        else if (buff.getStacks() >= 7)
        {
            return Texture2;
        }
        else
        {
            return Texture1;
        }
    }

    public void loadTextures()
    {
        super.loadTextures();
        try
        {
            Texture1 = GameTexture.fromFileRaw("buffs/adaptivealienbuff1");
            Texture2 = GameTexture.fromFileRaw("buffs/adaptivealienbuff2");
            Texture3 = GameTexture.fromFileRaw("buffs/adaptivealienbuff3");
            Texture4 = GameTexture.fromFileRaw("buffs/adaptivealienbuff4");
        }
        catch (FileNotFoundException var2)
        {
            Texture1 = GameTexture.fromFile("buffs/unknown");
            Texture2 = GameTexture.fromFile("buffs/unknown");
            Texture3 = GameTexture.fromFile("buffs/unknown");
            Texture4 = GameTexture.fromFile("buffs/unknown");
        }
    }

    public int getStackSize(ActiveBuff buff) {
        return 20;
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("bufftooltip", "adaptivealientip"));
        return tooltips;
    }
}