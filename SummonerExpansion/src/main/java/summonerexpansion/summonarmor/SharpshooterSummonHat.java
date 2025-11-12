package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class SharpshooterSummonHat extends SetHelmetArmorItem
{
    public IntUpgradeValue maxSummon = (new IntUpgradeValue()).setBaseValue(2).setUpgradedValue(1F, 3).setUpgradedValue(10F, 4);
    public FloatUpgradeValue critSummon = (new FloatUpgradeValue()).setBaseValue(0.15F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.35F);

    public SharpshooterSummonHat()
    {
        super(22, DamageTypeRegistry.SUMMON, 600, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.UNCOMMON, "sharpshootersummonhat", "sharpshootercoat", "sharpshooterboots", "sharpshootersummonsetbonus");
        canBeUsedForRaids = true;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.MAX_SUMMONS, maxSummon.getValue(getUpgradeTier(item))), new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, critSummon.getValue(getUpgradeTier(item))));
    }
}