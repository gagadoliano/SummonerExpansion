package summonerexpansion.items.equips.allweapons.basesummon;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.EnchantmentRegistry;
import necesse.engine.util.GameRandom;
import necesse.inventory.InventoryItem;
import necesse.inventory.enchants.Enchantable;
import necesse.inventory.enchants.ItemEnchantment;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem.BoomerangToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

import java.util.Set;

public class BaseBoomerangWeapon extends BoomerangToolItem
{
    public BaseBoomerangWeapon(int enchantCost, Item.Rarity rarityTier, String projID)
    {
        super(enchantCost, SummonWeaponsLootTable.summonWeapons, projID);
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        rarity = rarityTier;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(10F);
        attackAnimTime.setBaseValue(100);
        resilienceGain.setBaseValue(0.5F);
        attackRange.setBaseValue(100);
        velocity.setBaseValue(100);
        canBeUsedForRaids = true;
    }

    public ToolItemEnchantment getRandomEnchantment(GameRandom random, InventoryItem item)
    {
        return Enchantable.getRandomEnchantment(random, EnchantmentRegistry.meleeItemEnchantments, this.getEnchantmentID(item), ToolItemEnchantment.class);
    }

    public boolean isValidEnchantment(InventoryItem item, ItemEnchantment enchantment)
    {
        return EnchantmentRegistry.meleeItemEnchantments.contains(enchantment.getID());
    }

    public Set<Integer> getValidEnchantmentIDs(InventoryItem item)
    {
        return EnchantmentRegistry.meleeItemEnchantments;
    }
}