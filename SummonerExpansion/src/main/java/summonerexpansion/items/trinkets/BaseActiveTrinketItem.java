package summonerexpansion.items.trinkets;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.container.Container;
import necesse.inventory.container.slots.ContainerSlot;
import necesse.inventory.item.Item;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.inventory.lootTable.lootItem.OneOfLootItems;

import java.util.Arrays;

public class BaseActiveTrinketItem extends TrinketItem
{
    private String[] buffStringIDs;

    public BaseActiveTrinketItem(Item.Rarity rarity, String[] buffStringIDs, int enchantCost, OneOfLootItems lootTableCategory)
    {
        super(rarity, enchantCost, lootTableCategory);
        this.buffStringIDs = buffStringIDs;
    }

    public BaseActiveTrinketItem(Item.Rarity rarity, String buffStringID, int enchantCost, OneOfLootItems lootTableCategory)
    {
        this(rarity, new String[]{buffStringID}, enchantCost, lootTableCategory);
    }

    public TrinketBuff[] getBuffs(InventoryItem item)
    {
        return Arrays.stream(buffStringIDs).map((s) -> (TrinketBuff) BuffRegistry.getBuff(s)).toArray(TrinketBuff[]::new);
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/trinkets/" + getStringID());
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