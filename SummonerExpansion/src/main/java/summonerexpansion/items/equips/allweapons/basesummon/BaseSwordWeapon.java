package summonerexpansion.items.equips.allweapons.basesummon;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class BaseSwordWeapon extends SwordToolItem
{
    public BaseSwordWeapon(int enchantCost, Item.Rarity rarityTier)
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
}
