package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class SpiderBrideBoots extends BootsArmorItem
{
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1.0F, 0.25F);

    public SpiderBrideBoots()
    {
        super(17, 600, Rarity.LEGENDARY, "spiderbrideboots");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SPEED, this.speed.getValue(this.getUpgradeTier(item))));
    }
}