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

public class RedSpiderHelmet extends SetHelmetArmorItem
{
    public IntUpgradeValue summonMax = (new IntUpgradeValue()).setBaseValue(2).setUpgradedValue(1, 3);

    public RedSpiderHelmet()
    {
        super(5, DamageTypeRegistry.SUMMON, 50, Rarity.UNCOMMON, "redspiderhelmet", "redspiderchestplate", "redspiderboots", "redspidersetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
        canBeUsedForRaids = false;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.MAX_SUMMONS, summonMax.getValue(getUpgradeTier(item))));
    }
}