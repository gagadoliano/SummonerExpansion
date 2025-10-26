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

public class SpiderBrideHelmet extends SetHelmetArmorItem
{
    public FloatUpgradeValue summonDmg = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.30F);
    public FloatUpgradeValue summonSpd = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.30F);

    public SpiderBrideHelmet()
    {
        super(19, DamageTypeRegistry.SUMMON, 600, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.LEGENDARY, "spiderbridehelmet", "spiderbridechest", "spiderbrideboots", "spiderbridesetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, summonDmg.getValue(getUpgradeTier(item))), new ModifierValue(BuffModifiers.SUMMON_ATTACK_SPEED, summonSpd.getValue(getUpgradeTier(item))));
    }
}