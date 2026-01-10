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

public class TitaniumMagicHelmet extends SetHelmetArmorItem
{
    public FloatUpgradeValue combatManaRegen = (new FloatUpgradeValue()).setBaseValue(0.15F).setUpgradedValue(1, 0.25F).setUpgradedValue(10, 0.50F);
    public IntUpgradeValue maxMana = (new IntUpgradeValue()).setBaseValue(20).setUpgradedValue(1, 50).setUpgradedValue(10, 200);

    public TitaniumMagicHelmet(int enchantCost, Item.Rarity rarityTier)
    {
        super(7, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "titaniummagichelmet", "titaniumchestplate", "titaniumboots", "titaniummagicsetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.MAX_MANA_FLAT, maxMana.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.COMBAT_MANA_REGEN, combatManaRegen.getValue(getUpgradeTier(item))));
    }
}