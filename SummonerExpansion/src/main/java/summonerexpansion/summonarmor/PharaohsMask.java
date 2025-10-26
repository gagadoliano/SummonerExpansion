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

public class PharaohsMask extends SetHelmetArmorItem
{
    public FloatUpgradeValue summonDamage = (new FloatUpgradeValue()).setBaseValue(0.15F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.40F);
    public IntUpgradeValue maxSummon = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 3);

    public PharaohsMask()
    {
        super(24, DamageTypeRegistry.SUMMON, 550, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.UNCOMMON, "pharaohsmask", "pharaohsrobe", "pharaohssandals", "pharaohsmasksetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_DAMAGE, summonDamage.getValue(getUpgradeTier(item))), new ModifierValue(BuffModifiers.MAX_SUMMONS, maxSummon.getValue(getUpgradeTier(item))));
    }
}