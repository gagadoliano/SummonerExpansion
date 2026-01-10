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
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class TitaniumMeleeHelmet extends SetHelmetArmorItem
{
    public FloatUpgradeValue maxResilience = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1, 0.10F).setUpgradedValue(10, 0.20F);
    public FloatUpgradeValue resilienceGain = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.50F);

    public TitaniumMeleeHelmet(int enchantCost, Item.Rarity rarityTier)
    {
        super(8, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "titaniummeleehelmet", "titaniumchestplate", "titaniumboots", "titaniummeleesetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.RESILIENCE_GAIN, resilienceGain.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.MAX_RESILIENCE, maxResilience.getValue(getUpgradeTier(item))));
    }
}