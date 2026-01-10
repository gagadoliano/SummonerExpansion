package summonerexpansion.items.equips.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.BodyArmorLootTable;

public class RedSpiderChestplate extends ChestArmorItem
{
    public FloatUpgradeValue summonSpeed = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.40F);

    public RedSpiderChestplate(int enchantCost, Item.Rarity rarityTier)
    {
        super(6, enchantCost, rarityTier, "redspiderchestplate", "redspiderarms", BodyArmorLootTable.bodyArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, summonSpeed.getValue(getUpgradeTier(item))));
    }
}