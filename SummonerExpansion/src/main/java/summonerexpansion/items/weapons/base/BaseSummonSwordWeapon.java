package summonerexpansion.items.weapons.base;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class BaseSummonSwordWeapon extends SwordToolItem
{
    public BaseSummonSwordWeapon(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, SummonWeaponsLootTable.summonWeapons);
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        rarity = rarityTier;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(10.0F);
        canBeUsedForRaids = false;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/weapons/" + getStringID());
    }
}