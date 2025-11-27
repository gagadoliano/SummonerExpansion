package summonerexpansion.items.equips.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class FrostCrown extends SetHelmetArmorItem
{
    public FloatUpgradeValue summonDamage = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.15F).setUpgradedValue(10F, 0.30F);

    public FrostCrown()
    {
        super(5, DamageTypeRegistry.SUMMON, 50, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.COMMON, "frostcrown", "frostchestplate", "frostboots", "frostcrownsetbonus");
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        facialFeatureDrawOptions = FacialFeatureDrawMode.OVER_FACIAL_FEATURE;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_DAMAGE, summonDamage.getValue(getUpgradeTier(item))));
    }
}