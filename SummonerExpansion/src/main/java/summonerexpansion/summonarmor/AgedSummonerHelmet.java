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

public class AgedSummonerHelmet extends SetHelmetArmorItem
{
    public FloatUpgradeValue resDecay = (new FloatUpgradeValue()).setBaseValue(-0.5F).setUpgradedValue(1F, -0.6F).setUpgradedValue(5F, -1F).setUpgradedValue(10F, -1F);
    public IntUpgradeValue maxSummon = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 3);

    public AgedSummonerHelmet()
    {
        super(24, DamageTypeRegistry.SUMMON, 600, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.EPIC, "agedsummonerhelmet", "agedchampionchestplate", "agedchampiongreaves", "agedsummonersetbonus");
        hairDrawOptions = HairDrawMode.NO_HAIR;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_CRIT_CHANCE, 0.10F), new ModifierValue(BuffModifiers.MAX_SUMMONS, maxSummon.getValue(getUpgradeTier(item))), new ModifierValue(BuffModifiers.RESILIENCE_DECAY, resDecay.getValue(getUpgradeTier(item))));
    }
}