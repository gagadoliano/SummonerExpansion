package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class BloodplateMask extends SetHelmetArmorItem
{
    public FloatUpgradeValue healthRegen = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1.0F, 0.25F);

    public BloodplateMask()
    {
        super(5, DamageTypeRegistry.SUMMON, 500, Rarity.UNCOMMON, "bloodplatemask", "bloodplatechestplate", "bloodplateboots", "bloodplatecowlsetbonus");
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        hairMaskTextureName = "bloodplatecowl_hairmask";
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, healthRegen.getValue(this.getUpgradeTier(item))));
    }
}