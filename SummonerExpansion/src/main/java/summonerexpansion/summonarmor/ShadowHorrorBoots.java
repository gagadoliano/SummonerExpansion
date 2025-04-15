package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class ShadowHorrorBoots extends BootsArmorItem
{
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1, 0.22F).setUpgradedValue(2, 0.24F).setUpgradedValue(3, 0.26F).setUpgradedValue(4, 0.28F).setUpgradedValue(5, 0.30F);

    public ShadowHorrorBoots()
    {
        super(15, 600, Rarity.LEGENDARY, "shadowhorrorboots");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SPEED, speed.getValue(getUpgradeTier(item))));
    }
}