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

public class ShadowHorrorHood extends SetHelmetArmorItem
{
    public FloatUpgradeValue critSummon = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1F, 0.07F).setUpgradedValue(10F, 0.15F);

    public ShadowHorrorHood()
    {
        super(20, DamageTypeRegistry.SUMMON, 600, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.LEGENDARY, "shadowhorrorhood", "shadowhorrormantle", "shadowhorrorboots", "shadowhorrorsetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.MAX_SUMMONS, 2), new ModifierValue(BuffModifiers.SUMMON_CRIT_CHANCE, critSummon.getValue(getUpgradeTier(item))));
    }
}