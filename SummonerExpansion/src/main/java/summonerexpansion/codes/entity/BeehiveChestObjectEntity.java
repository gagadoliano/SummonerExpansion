package summonerexpansion.codes.entity;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.GlobalIngredientRegistry;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.engine.util.GameMath;
import necesse.entity.objectEntity.FueledRefrigeratorObjectEntity;
import necesse.entity.objectEntity.InventoryObjectEntity;
import necesse.inventory.Inventory;
import necesse.inventory.InventoryItem;
import necesse.inventory.InventoryRange;
import necesse.level.maps.Level;
import necesse.level.maps.levelData.settlementData.SettlementRequestOptions;
import necesse.level.maps.levelData.settlementData.storage.SettlementStorageGlobalIngredientIDIndex;
import necesse.level.maps.levelData.settlementData.storage.SettlementStorageRecords;
import necesse.level.maps.levelData.settlementData.storage.SettlementStorageRecordsRegionData;

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