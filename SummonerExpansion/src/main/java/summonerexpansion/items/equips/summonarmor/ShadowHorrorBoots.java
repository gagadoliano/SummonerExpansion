package summonerexpansion.items.equips.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.FeetArmorLootTable;

public class ShadowHorrorBoots extends BootsArmorItem
{
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1F, 0.25F).setUpgradedValue(10F, 0.35F);

    public ShadowHorrorBoots()
    {
        super(15, 600, Rarity.LEGENDARY, "shadowhorrorboots", FeetArmorLootTable.feetArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SPEED, speed.getValue(getUpgradeTier(item))));
    }
}