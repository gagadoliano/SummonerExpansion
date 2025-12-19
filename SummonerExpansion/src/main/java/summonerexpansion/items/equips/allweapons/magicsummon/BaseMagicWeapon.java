package summonerexpansion.items.equips.allweapons.magicsummon;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.EnchantmentRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.GameResources;
import necesse.inventory.InventoryItem;
import necesse.inventory.enchants.Enchantable;
import necesse.inventory.enchants.ItemEnchantment;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

import java.util.Set;

public class BaseMagicWeapon extends MagicProjectileToolItem
{
    public BaseMagicWeapon(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, SummonWeaponsLootTable.summonWeapons);
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        rarity = rarityTier;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(10.0F);
        manaCost.setBaseValue(1.0F);
        resilienceGain.setBaseValue(0);
        attackAnimTime.setBaseValue(500);
        attackRange.setBaseValue(500);
        velocity.setBaseValue(100);
        knockback.setBaseValue(10);
        attackXOffset = 20;
        attackYOffset = 20;
        itemAttackerProjectileCanHitWidth = 5.0F;
        itemAttackerPredictionDistanceOffset = -20.0F;
        canBeUsedForRaids = false;
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (level.isClient())
        {
            SoundManager.playSound(GameResources.magicbolt2, SoundEffect.effect(attackerMob).volume(0.4F).pitch(GameRandom.globalRandom.getFloatBetween(0.8F, 0.9F)));
        }
    }

    public ToolItemEnchantment getRandomEnchantment(GameRandom random, InventoryItem item)
    {
        return Enchantable.getRandomEnchantment(random, EnchantmentRegistry.magicItemEnchantments, this.getEnchantmentID(item), ToolItemEnchantment.class);
    }

    public boolean isValidEnchantment(InventoryItem item, ItemEnchantment enchantment)
    {
        return EnchantmentRegistry.magicItemEnchantments.contains(enchantment.getID());
    }

    public Set<Integer> getValidEnchantmentIDs(InventoryItem item)
    {
        return EnchantmentRegistry.magicItemEnchantments;
    }
}