package summonerexpansion.summontrinket;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class NecroticClaw extends TrinketItem
{
    public NecroticClaw()
    {
        super(Rarity.EPIC, 400);
    }

    public TrinketBuff[] getBuffs(InventoryItem item)
    {
        return new TrinketBuff[]{(TrinketBuff)BuffRegistry.getBuff("necroticclawbuff")};
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "necroticclawtip"));
        return tooltips;
    }
}
