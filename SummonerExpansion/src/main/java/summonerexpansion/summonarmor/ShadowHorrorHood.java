package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class ShadowHorrorHood extends SetHelmetArmorItem
{
    public FloatUpgradeValue critSummon = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1, 0.07F).setUpgradedValue(2, 0.09F).setUpgradedValue(3, 0.11F).setUpgradedValue(4, 0.13F).setUpgradedValue(5, 0.15F);

    public ShadowHorrorHood()
    {
        super(20, DamageTypeRegistry.SUMMON, 600, Rarity.LEGENDARY, "shadowhorrorhood", "shadowhorrormantle", "shadowhorrorboots", "shadowhorrorsetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
        canBeUsedForRaids = false;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.MAX_SUMMONS, 2), new ModifierValue(BuffModifiers.SUMMON_CRIT_CHANCE, critSummon.getValue(getUpgradeTier(item))));
    }
}