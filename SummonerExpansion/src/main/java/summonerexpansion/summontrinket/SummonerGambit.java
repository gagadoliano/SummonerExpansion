package summonerexpansion.summontrinket;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.container.Container;
import necesse.inventory.container.slots.ContainerSlot;
import necesse.inventory.item.trinketItem.TrinketItem;

public class SummonerGambit extends TrinketItem
{
    public SummonerGambit() {
        super(Rarity.RARE, 400);
    }

    public TrinketBuff[] getBuffs(InventoryItem item)
    {
        return new TrinketBuff[]{(TrinketBuff)BuffRegistry.getBuff("summonergambitbuff")};
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "summonergambittip"));
        return tooltips;
    }

    public void addTrinketAbilityHotkeyTooltip(ListGameTooltips tooltips, InventoryItem item) {}

    public boolean isAbilityTrinket(InventoryItem item) {
        return true;
    }

    public String getInvalidInSlotError(Container container, ContainerSlot slot, InventoryItem item)
    {
        String superInvalidError = super.getInvalidInSlotError(container, slot, item);
        if (superInvalidError != null)
        {
            return superInvalidError;
        }
        else
        {
            return slot.getContainerIndex() == container.CLIENT_TRINKET_ABILITY_SLOT ? null : Localization.translate("itemtooltip", "foolsgambiterrortip");
        }
    }
}
