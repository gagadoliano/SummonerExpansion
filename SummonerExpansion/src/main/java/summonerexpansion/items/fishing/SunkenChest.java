package summonerexpansion.items.fishing;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.CirclingFishParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.container.Container;
import necesse.inventory.container.ContainerActionResult;
import necesse.inventory.container.slots.ContainerSlot;
import necesse.inventory.item.matItem.FishItemInterface;
import necesse.inventory.item.matItem.MatItem;
import necesse.level.maps.Level;
import summonerexpansion.codes.registries.RegistryLootBag;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SunkenChest extends MatItem implements FishItemInterface
{
    public GameTexture circlingFishTexture;

    public SunkenChest()
    {
        super(999, Rarity.RARE);
        setItemCategory("misc");
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.circlingFishTexture = GameTexture.fromFile("particles/circlingfish");
    }

    public Particle getParticle(Level level, int x, int y, int lifeTime)
    {
        return new CirclingFishParticle(level, (float)x, (float)y, lifeTime, this.circlingFishTexture, 60);
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
                RegistryLootBag.sunkenChestLoot.addItems(itemList, GameRandom.globalRandom, 1.0F, container.getClient());
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

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/fishing/" + getStringID());
    }
}