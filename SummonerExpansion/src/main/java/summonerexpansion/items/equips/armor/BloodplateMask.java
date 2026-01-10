package summonerexpansion.items.equips.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class BloodplateMask extends SetHelmetArmorItem
{
    public FloatUpgradeValue healthRegen = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1F, 0.25F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);

    public BloodplateMask(int enchantCost, Item.Rarity rarityTier)
    {
        super(5, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "bloodplatemask", "bloodplatechestplate", "bloodplateboots", "bloodplatemasksetbonus");
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        hairMaskTextureName = "bloodplatecowl_hairmask";
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, healthRegen.getValue(getUpgradeTier(item))));
    }
}