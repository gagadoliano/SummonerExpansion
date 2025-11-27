package summonerexpansion.items.equips.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.BodyArmorLootTable;

public class SailorSummonShirt extends ChestArmorItem
{
    public FloatUpgradeValue attackSpeed = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.15F).setUpgradedValue(10, 0.30F);
    public FloatUpgradeValue projVelocity = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.15F).setUpgradedValue(10, 0.30F);

    public SailorSummonShirt()
    {
        super(20, 1200, Rarity.LEGENDARY, "sailorsummonshirt", "sailorsummonarms", BodyArmorLootTable.bodyArmor);
        defaultLootTier = 1.0F;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, attackSpeed.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, projVelocity.getValue(getUpgradeTier(item))));
    }
}