package summonerexpansion.codes.entity;

import necesse.entity.objectEntity.InventoryObjectEntity;
import necesse.level.maps.Level;

public class BeehiveChestObjectEntity extends InventoryObjectEntity
{
    public float spoilRate;
    public boolean forceUpdate = true;

    public BeehiveChestObjectEntity(Level level, int x, int y, int inventorySlots, float spoilRate)
    {
        super(level, x, y, inventorySlots);
        this.spoilRate = spoilRate;
    }

    protected void onInventorySlotUpdated(int slot)
    {
        super.onInventorySlotUpdated(slot);
        this.forceUpdate = true;
    }

    public void init()
    {
        super.init();
        this.updateInventorySpoilRate();
    }

    public void clientTick()
    {
        super.clientTick();
        this.updateInventorySpoilRate();
    }

    public void serverTick()
    {
        super.serverTick();
        this.updateInventorySpoilRate();
    }

    public void updateInventorySpoilRate()
    {
        inventory.spoilRateModifier = this.spoilRate;
    }
}