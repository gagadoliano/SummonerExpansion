package summonerexpansion.summonarmor;

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

public class ShadowHelmet extends SetHelmetArmorItem
{
    public FloatUpgradeValue summonDamage = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.15F).setUpgradedValue(10F, 0.30F);

    public ShadowHelmet()
    {
        super(5, DamageTypeRegistry.SUMMON, 600, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.UNCOMMON, "shadowhelmet", "shadowmantle", "shadowboots", "shadowhelmetsetbonus");
        hairDrawOptions = HairDrawMode.NO_HAIR;
        facialFeatureDrawOptions = FacialFeatureDrawMode.NO_FACIAL_FEATURE;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_DAMAGE, summonDamage.getValue(getUpgradeTier(item))));
    }
}