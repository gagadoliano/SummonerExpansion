package summonerexpansion.items.equips.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.BodyArmorLootTable;

public class RainSummonCoat extends ChestArmorItem
{
    public FloatUpgradeValue summonDMG = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.40F);
    public IntUpgradeValue fishPower = (new IntUpgradeValue()).setBaseValue(5).setUpgradedValue(1, 15).setUpgradedValue(10, 20);

    public RainSummonCoat(int enchantCost, Item.Rarity rarityTier)
    {
        super(2, enchantCost, rarityTier, "rainsummoncoat", "rainsummoncoatarms", BodyArmorLootTable.bodyArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, summonDMG.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.FISHING_POWER, fishPower.getValue(getUpgradeTier(item))));
    }
}