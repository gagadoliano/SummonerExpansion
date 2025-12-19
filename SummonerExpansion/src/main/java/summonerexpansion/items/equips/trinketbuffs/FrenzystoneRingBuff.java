package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class FrenzystoneRingBuff extends TrinketBuff
{
    public FrenzystoneRingBuff()
    {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        this.updateBuff(buff);
        this.isImportant = true;
    }

    public void serverTick(ActiveBuff buff)
    {
        this.updateBuff(buff);
        this.updateActiveBuff(buff);
    }

    public void clientTick(ActiveBuff buff)
    {
        this.updateBuff(buff);
        this.updateActiveBuff(buff);
    }

    public void updateActiveBuff(ActiveBuff buff)
    {
        float healthPercent = buff.owner.getHealthPercent();
        if (healthPercent < 0.5F && buff.owner.buffManager.getBuffDurationLeftSeconds(BuffRegistry.BLOODSTONE_RING_REGEN_ACTIVE_BUFF) <= 1.0F)
        {
            ActiveBuff activeBuff = new ActiveBuff(BuffRegistry.BLOODSTONE_RING_REGEN_ACTIVE_BUFF, buff.owner, 4.0F, (Attacker)null);
            buff.owner.buffManager.addBuff(activeBuff, true);
        } else if (healthPercent >= 0.5F && buff.owner.buffManager.hasBuff(BuffRegistry.BLOODSTONE_RING_REGEN_ACTIVE_BUFF))
        {
            buff.owner.buffManager.removeBuff(BuffRegistry.BLOODSTONE_RING_REGEN_ACTIVE_BUFF, true);
        }
    }

    private void updateBuff(ActiveBuff buff)
    {
        float current = buff.getModifier(BuffModifiers.SUMMON_DAMAGE);
        float next = getAttackBonusPerc((float)buff.owner.getHealth() / (float)buff.owner.getMaxHealth(), 0.1F) * 0.5F;
        next = GameMath.toDecimals(next, 2);
        if (current != next)
        {
            buff.setModifier(BuffModifiers.SUMMON_DAMAGE, next);
            buff.forceManagerUpdate();
        }
    }

    public static float getAttackBonusPerc(float healthPercent, float offset)
    {
        if (offset != 0.0F)
        {
            healthPercent = (offset - healthPercent) / (offset - 1.0F);
        }
        healthPercent = GameMath.limit(healthPercent, 0.0F, 1.0F);
        return Math.abs(healthPercent - 1.0F);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "frenzystoneringtip"));
        return tooltips;
    }
}
