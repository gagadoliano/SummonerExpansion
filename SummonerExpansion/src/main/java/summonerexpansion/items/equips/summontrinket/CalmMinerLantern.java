package summonerexpansion.items.equips.summontrinket;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.inventory.lootTable.presets.TrinketsLootTable;
import necesse.level.maps.Level;

public class CalmMinerLantern extends TrinketItem
{
    public CalmMinerLantern() {
        super(Rarity.RARE, 800, TrinketsLootTable.trinkets);
    }

    public TrinketBuff[] getBuffs(InventoryItem item)
    {
        return new TrinketBuff[]{(TrinketBuff)BuffRegistry.getBuff("calmminerslanternbuff")};
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress)
    {
        drawOptions.swingRotation(attackProgress);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        InventoryItem out = new InventoryItem("minerslantern", item.getAmount());
        out.setGndData(item.getGndData().copy());
        out.setLocked(item.isLocked());
        return out;
    }
}