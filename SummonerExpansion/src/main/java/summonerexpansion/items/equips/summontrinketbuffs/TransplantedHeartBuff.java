package summonerexpansion.items.equips.summontrinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.packet.PacketLifelineEvent;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.maps.Level;

public class TransplantedHeartBuff extends TrinketBuff
{
    public TransplantedHeartBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 0.75F);
        buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.10F);
        eventSubscriber.subscribeEvent(MobBeforeDamageOverTimeTakenEvent.class, (event) -> {
            if (runLifeLineLogic(buff, event.getExpectedHealth()))
            {
                event.prevent();
            }
        });
        updateModifiers(buff);
    }

    public void clientTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void serverTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    private void updateModifiers(ActiveBuff buff)
    {
        int lastMaxHealth = buff.getGndData().getInt("lastMaxHealth");
        int nextMaxHealth = buff.owner.getMaxHealth() + lastMaxHealth / 2;
        if (lastMaxHealth != nextMaxHealth)
        {
            buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, -nextMaxHealth / 2);
            buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, nextMaxHealth / 2);
            buff.getGndData().setInt("lastMaxHealth", nextMaxHealth);
            buff.owner.buffManager.forceUpdateBuffs();
        }
        Mob owner = buff.owner;
        if (owner.getHealth() < owner.getMaxHealth())
        {
            buff.setModifier(BuffModifiers.RESILIENCE_REGEN_FLAT, 0.0F);
        }
        else
        {
            buff.setModifier(BuffModifiers.RESILIENCE_REGEN_FLAT, owner.isInCombat() ? 1.0F + owner.getCombatRegen() : 1.0F + owner.getRegen() + owner.getCombatRegen());
        }
    }

    public void onBeforeHitCalculated(ActiveBuff buff, MobBeforeHitCalculatedEvent event)
    {
        super.onBeforeHitCalculated(buff, event);
        if (this.runLifeLineLogic(buff, event.getExpectedHealth()))
        {
            event.prevent();
        }
    }

    protected boolean runLifeLineLogic(ActiveBuff buff, int expectedHealth)
    {
        Level level = buff.owner.getLevel();
        if (level.isServer() && !buff.owner.buffManager.hasBuff(BuffRegistry.Debuffs.LIFELINE_COOLDOWN.getID()) && expectedHealth <= 0)
        {
            buff.owner.setHealth(Math.max(10, buff.owner.getMaxHealth() / 4));
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.LIFELINE_COOLDOWN, buff.owner, 300.0F, null), true);
            level.getServer().network.sendToClientsWithEntity(new PacketLifelineEvent(buff.owner.getUniqueID()), buff.owner);
            return true;
        }
        else
        {
            return false;
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "transplantedhearttip"));
        return tooltips;
    }
}