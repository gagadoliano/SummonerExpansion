package summonerexpansion.buffs.tilebuffs;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.gameTooltips.ListGameTooltips;

import java.util.concurrent.atomic.AtomicBoolean;

import static summonerexpansion.codes.registry.SummonerTiles.liquidHoneyID;

public class LiquidHoneyBuff extends Buff
{
    public LiquidHoneyBuff()
    {
        isImportant = true;
        canCancel = false;
        sortByDuration = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        updateModifiers(buff);
    }

    public void onStacksUpdated(ActiveBuff buff, ActiveBuff other)
    {
        super.onStacksUpdated(buff, other);
        updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        float progress = (float)buff.getStacks() / (float)buff.getMaxStacks();
        buff.setMinModifier(BuffModifiers.SLOW, GameMath.lerp(progress, 0.0F, 0.9F), 1000000);
        buff.setModifier(BuffModifiers.HEALTH_REGEN, 0.01f);
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        tickValid(buff);
    }

    public void clientTick(ActiveBuff buff)
    {
        super.clientTick(buff);
        tickValid(buff);
    }

    public void tickValid(ActiveBuff buff)
    {
        Mob owner = buff.owner;
        int tileX = owner.getTileX();
        int tileY = owner.getTileY();
        if (owner.getLevel().getTileID(tileX, tileY) != liquidHoneyID || owner.getLevel().getObject(tileX, tileY).overridesInLiquid(owner.getLevel(), tileX, tileY, owner.getX(), owner.getY()) || owner.isWaterWalking() || owner.isFlying())
        {
            buff.remove();
        }
    }

    public int getRemainingStacksDuration(ActiveBuff buff, AtomicBoolean sendUpdatePacket) {
        return 100;
    }

    public int getStackSize(ActiveBuff buff) {
        return 100;
    }

    public boolean overridesStackDuration() {
        return true;
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return true;
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltip = super.getTooltip(ab, blackboard);
        tooltip.add(Localization.translate("bufftooltip", "liquidhoneytip"));
        return tooltip;
    }

    public String getDurationText(ActiveBuff buff)
    {
        float percentage = (float)buff.getStacks() / (float)buff.getMaxStacks();
        return (int)(percentage * 100.0F) + "%";
    }

    public int getStacksDisplayCount(ActiveBuff buff) {
        return 0;
    }
}