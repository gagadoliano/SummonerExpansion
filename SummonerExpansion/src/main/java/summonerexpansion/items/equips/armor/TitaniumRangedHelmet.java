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

public class TitaniumRangedHelmet extends SetHelmetArmorItem
{
    public FloatUpgradeValue projVelocity = (new FloatUpgradeValue()).setBaseValue(0.15F).setUpgradedValue(1, 0.25F).setUpgradedValue(10, 0.50F);
    public FloatUpgradeValue summonRange = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1, 0.30F).setUpgradedValue(10, 0.50F);

    public TitaniumRangedHelmet(int enchantCost, Item.Rarity rarityTier)
    {
        super(6, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "titaniumrangedhelmet", "titaniumchestplate", "titaniumboots", "titaniumrangedsetbonus");
        hairDrawOptions = HairDrawMode.NO_HAIR;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMONS_TARGET_RANGE, summonRange.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, projVelocity.getValue(getUpgradeTier(item))));
    }
}