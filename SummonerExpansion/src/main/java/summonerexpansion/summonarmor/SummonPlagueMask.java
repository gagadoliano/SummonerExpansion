package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class SummonPlagueMask extends SetHelmetArmorItem
{
    public FloatUpgradeValue speedSummon = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1, 0.10F).setUpgradedValue(2, 0.15F).setUpgradedValue(3, 0.20F).setUpgradedValue(4, 0.25F).setUpgradedValue(5, 0.30F);

    public SummonPlagueMask()
    {
        super(15, DamageTypeRegistry.SUMMON, 550, Rarity.UNCOMMON, "summonplaguemask", "summonplaguerobe", "summonplagueboots", "summonplaguesetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
        canBeUsedForRaids = false;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.SUMMONS_SPEED, speedSummon.getValue(getUpgradeTier(item))));
    }
}