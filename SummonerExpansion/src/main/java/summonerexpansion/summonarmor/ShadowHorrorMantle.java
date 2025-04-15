package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class ShadowHorrorMantle extends ChestArmorItem
{
    public FloatUpgradeValue damageSummon = (new FloatUpgradeValue()).setBaseValue(0.15F).setUpgradedValue(1, 0.20F).setUpgradedValue(2, 0.25F).setUpgradedValue(3, 0.30F).setUpgradedValue(4, 0.35F).setUpgradedValue(5, 0.40F);

    public ShadowHorrorMantle()
    {
        super(25, 600, Rarity.LEGENDARY, "shadowhorrormantle", "shadowhorrorarms");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_DAMAGE, damageSummon.getValue(getUpgradeTier(item))));
    }
}