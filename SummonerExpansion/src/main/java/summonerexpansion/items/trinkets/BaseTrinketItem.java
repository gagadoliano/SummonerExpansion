package summonerexpansion.items.trinkets;

import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.inventory.lootTable.lootItem.OneOfLootItems;

import java.util.Arrays;

public class BaseTrinketItem extends TrinketItem
{
    private String[] buffStringIDs;

    public BaseTrinketItem(Item.Rarity rarity, String[] buffStringIDs, int enchantCost, OneOfLootItems lootTableCategory)
    {
        super(rarity, enchantCost, lootTableCategory);
        this.buffStringIDs = buffStringIDs;
    }

    public BaseTrinketItem(Item.Rarity rarity, String buffStringID, int enchantCost, OneOfLootItems lootTableCategory)
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
}