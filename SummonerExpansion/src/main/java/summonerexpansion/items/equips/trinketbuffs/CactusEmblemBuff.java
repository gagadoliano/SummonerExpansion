package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class CactusEmblemBuff extends TrinketBuff
{
    public CactusEmblemBuff() {}

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber) {
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        if (!event.wasPrevented && buff.owner.isServer())
        {
            Mob attackOwner = event.attacker != null ? event.attacker.getAttackOwner() : null;
            boolean hasOwnerInChain = event.attacker != null && event.attacker.isInAttackOwnerChain(buff.owner);
            if (attackOwner != null && !hasOwnerInChain)
            {
                float dx = (float)(attackOwner.getX() - buff.owner.getX());
                float dy = (float)(attackOwner.getY() - buff.owner.getY());
                float damage = (float)event.damage;
                damage *= buff.owner.buffManager.getModifier(BuffModifiers.MAX_SUMMONS);
                attackOwner.isServerHit(new GameDamage(damage, 0.0F), dx, dy, 50.0F, buff.owner);
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "cactusemblemtip"));
        return tooltips;
    }
}