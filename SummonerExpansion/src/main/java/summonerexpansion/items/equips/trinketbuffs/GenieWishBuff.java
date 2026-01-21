package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

import static summonerexpansion.codes.registry.SummonerBuffs.SummonBuffs.GENIECRITSTACKS;

public class GenieWishBuff extends TrinketBuff
{
    public GenieWishBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {

    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if (event.damageType == DamageTypeRegistry.SUMMON)
        {
            if (!buff.owner.buffManager.hasBuff(GENIECRITSTACKS))
            {
                buff.owner.buffManager.addBuff(new ActiveBuff(GENIECRITSTACKS, buff.owner, 120F, null), false);
            }
            else if (buff.owner.buffManager.getBuff(GENIECRITSTACKS).getStacks() < 20)
            {
                buff.owner.buffManager.getBuff(GENIECRITSTACKS).addStack(600, null);
            }
            else if (buff.owner.buffManager.getBuff(GENIECRITSTACKS).getStacks() >= 20 && event.isCrit)
            {
                buff.owner.buffManager.removeBuff(GENIECRITSTACKS, false);
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "geniewishtip"));
        return tooltips;
    }
}