package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class SummonPlagueRobe extends ChestArmorItem
{
    public FloatUpgradeValue damageSummon = (new FloatUpgradeValue()).setBaseValue(0.08F).setUpgradedValue(1, 0.12F).setUpgradedValue(2, 0.14F).setUpgradedValue(3, 0.16F).setUpgradedValue(4, 0.18F).setUpgradedValue(5, 0.20F);

    public SummonPlagueRobe()
    {
        super(15, 550, Rarity.UNCOMMON, "summonplaguerobe", "summonplaguearms");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_DAMAGE, damageSummon.getValue(getUpgradeTier(item))));
    }
}