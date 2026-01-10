package summonerexpansion.items.miscitems;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.container.Container;
import necesse.inventory.container.ContainerActionResult;
import necesse.inventory.container.slots.ContainerSlot;
import necesse.inventory.item.matItem.MatItem;
import summonerexpansion.codes.registry.SummonerLoot;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SunkenChest extends MatItem
{
    public SunkenChest()
    {
        super(999, Rarity.RARE);
        setItemCategory("misc");
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "sunkenchesttip"));
        tooltips.add(Localization.translate("itemtooltip", "rclickinvopentip"));
        return tooltips;
    }

    public Supplier<ContainerActionResult> getInventoryRightClickAction(Container container, InventoryItem item, int slotIndex, ContainerSlot slot)
    {
        return () ->
        {
            if (container.getClient().isServer())
            {
                ArrayList<InventoryItem> itemList = new ArrayList<>();
                SummonerLoot.sunkenChestLoot.addItems(itemList, GameRandom.globalRandom, 1.0F, container.getClient());
                for(InventoryItem inventoryItem : itemList)
                {
                    container.getClient().playerMob.getInv().addItemsDropRemaining(inventoryItem, "addback", container.getClient().playerMob, true, false);
                }
            }
            slot.setAmount(item.getAmount() - 1);
            if (item.getAmount() <= 0)
            {
                slot.setItem(null);
            }
            return new ContainerActionResult(154617259 * (item.getAmount() + GameRandom.prime(4)));
        };
    }
}