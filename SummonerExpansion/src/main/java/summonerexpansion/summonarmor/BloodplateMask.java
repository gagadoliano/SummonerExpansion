package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item.Rarity;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.armorItem.ArmorItem.HairDrawMode;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class BloodplateMask extends SetHelmetArmorItem
{
    public FloatUpgradeValue healthRegen = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1.0F, 0.25F);
    public IntUpgradeValue maxMinion = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);

    public BloodplateMask()
    {
        super(5, DamageTypeRegistry.SUMMON, 50, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.UNCOMMON, "bloodplatemask", "bloodplatechestplate", "bloodplateboots", "bloodplatecowlsetbonus");
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        hairMaskTextureName = "bloodplatecowl_hairmask";
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.MAX_SUMMONS, maxMinion.getValue(this.getUpgradeTier(item))), new ModifierValue(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, healthRegen.getValue(this.getUpgradeTier(item))));
    }
}