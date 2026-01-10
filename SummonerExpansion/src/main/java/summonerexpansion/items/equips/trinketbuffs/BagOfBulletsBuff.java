package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.trinketItem.TrinketItem;

import java.util.LinkedList;

public class BagOfBulletsBuff extends TrinketBuff
{
    public BagOfBulletsBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(BuffModifiers.BULLET_USAGE, -0.75F);
        buff.setModifier(BuffModifiers.PROJECTILE_BOUNCES, 10);
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.20F);
        buff.setModifier(BuffModifiers.ATTACK_MOVEMENT_MOD, 0.5F);
        buff.setModifier(BuffModifiers.EXTENDED_MAP_DISCOVER_RANGE, true);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "bagofbulletstip"));
        return tooltips;
    }
}