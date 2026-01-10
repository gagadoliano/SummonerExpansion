package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.AuraBuff;
import necesse.gfx.ThemeColorRegistry;
import necesse.gfx.gameTooltips.ListGameTooltips;

import java.awt.*;

public class SilverGobletBuff extends AuraBuff
{
    public SilverGobletBuff()
    {

    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.ARMOR, 0.15F);
    }

    public boolean shouldDrawDuration(ActiveBuff buff)
    {
        return false;
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        return super.getTooltip(ab, blackboard);
    }

    public Color getParticleColor() {
        return ThemeColorRegistry.SMOKE.getRandomColor();
    }
}