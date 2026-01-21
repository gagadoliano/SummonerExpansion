package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import summonerexpansion.codes.registry.SummonerModifiers;

public class CompanionSatchelsBuff extends TrinketBuff
{
    public CompanionSatchelsBuff() {}

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.10F);
        activeBuff.setModifier(BuffModifiers.SUMMONS_SPEED, 0.30F);
        activeBuff.setModifier(BuffModifiers.LIFE_ESSENCE_GAIN, 0.50F);
        activeBuff.setModifier(BuffModifiers.LIFE_ESSENCE_DURATION, 1.0F);
        activeBuff.setModifier(BuffModifiers.SPEED, 0.10F);
        activeBuff.setModifier(SummonerModifiers.EMITS_SUMMON_LIGHT, true);
    }

    public void clientTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void serverTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        int maxStacks = buff.owner.buffManager.getModifier(BuffModifiers.MAX_SUMMONS);
        int usedStacks = buff.owner.buffManager.getStacks(BuffRegistry.SUMMONED_MOB);
        if (maxStacks != buff.getGndData().getInt("lastMaxStacks") || usedStacks != buff.getGndData().getInt("lastUsedStacks"))
        {
            float damageModifier = 0.0F;
            float speedModifier = 0.0F;
            for(int i = 0; i < maxStacks; ++i)
            {
                if (i >= usedStacks)
                {
                    float damageDelta = Math.max(1.0F - 0.1F * (float)i, 0.0F);
                    float speedDelta = Math.max(0.2F - 0.02F * (float)i, 0.0F);
                    if (damageDelta <= 0.0F && speedDelta <= 0.0F)
                    {
                        break;
                    }
                    damageModifier += damageDelta;
                    speedModifier += speedDelta;
                }
            }
            buff.setModifier(BuffModifiers.SUMMON_DAMAGE, damageModifier);
            buff.setModifier(BuffModifiers.SUMMONS_SPEED, speedModifier);
            buff.getGndData().setInt("lastMaxStacks", maxStacks);
            buff.getGndData().setInt("lastUsedStacks", usedStacks);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "companionsatchelstip"));
        return tooltips;
    }
}