package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class RedSpiderChestplate extends ChestArmorItem
{
    public FloatUpgradeValue summonSpd = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1.0F, 0.20F);

    public RedSpiderChestplate()
    {
        super(6, 50, Rarity.UNCOMMON, "redspiderchestplate", "redspiderarms");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMONS_SPEED, summonSpd.getValue(this.getUpgradeTier(item))));
    }
}