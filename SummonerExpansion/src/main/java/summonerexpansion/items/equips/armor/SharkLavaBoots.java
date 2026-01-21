package summonerexpansion.items.equips.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.FeetArmorLootTable;

public class SharkLavaBoots extends BootsArmorItem
{
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.15F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.35F);
    public FloatUpgradeValue speedSwim = (new FloatUpgradeValue()).setBaseValue(1.20F).setUpgradedValue(1, 1.50F).setUpgradedValue(10, 2.50F);

    public SharkLavaBoots(int enchantCost, Item.Rarity rarityTier)
    {
        super(4, enchantCost, "sharklavaboots", FeetArmorLootTable.feetArmor);
        rarity = rarityTier;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SPEED, speed.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.SWIM_SPEED, speedSwim.getValue(getUpgradeTier(item))));
    }
}