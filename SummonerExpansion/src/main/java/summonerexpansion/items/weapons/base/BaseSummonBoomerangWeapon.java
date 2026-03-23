package summonerexpansion.items.weapons.base;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.EnchantmentRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.enchants.Enchantable;
import necesse.inventory.enchants.ItemEnchantment;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem.BoomerangToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

import java.util.Set;

public class BaseSummonBoomerangWeapon extends BoomerangToolItem
{
    public IntUpgradeValue amount = (new IntUpgradeValue()).setBaseValue(1);

    public BaseSummonBoomerangWeapon(float baseDamage, float t1Damage, int attackSpeed, int projVelocity, int range, int amount, int enchantCost, Item.Rarity rarityTier, String projID)
    {
        super(enchantCost, SummonWeaponsLootTable.summonWeapons, projID);
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        rarity = rarityTier;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(baseDamage).setUpgradedValue(1, t1Damage);
        attackAnimTime.setBaseValue(attackSpeed);
        resilienceGain.setBaseValue(0.5F).setUpgradedValue(1, 1.5F).setUpgradedValue(10, 2.0F);
        attackRange.setBaseValue(range).setUpgradedValue(1, range + 200);
        velocity.setBaseValue(projVelocity).setUpgradedValue(1, projVelocity + 50);
        this.amount.setBaseValue(amount).setUpgradedValue(1, amount + 1).setUpgradedValue(10, amount + 2);
        canBeUsedForRaids = false;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/weapons/" + getStringID());
    }

    public String canAttack(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return attackerMob.getBoomerangsUsage() < amount.getValue(getUpgradeTier(item)) ? null : "";
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