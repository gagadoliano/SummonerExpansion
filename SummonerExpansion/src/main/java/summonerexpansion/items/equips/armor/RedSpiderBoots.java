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

public class RedSpiderBoots extends BootsArmorItem
{
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.25F).setUpgradedValue(10F, 0.35F);
    public FloatUpgradeValue summonSpeed = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.35F);

    public RedSpiderBoots(int enchantCost, Item.Rarity rarityTier)
    {
        super(4, enchantCost, rarityTier, "redspiderboots", FeetArmorLootTable.feetArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SPEED, speed.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, summonSpeed.getValue(getUpgradeTier(item))));
    }
}