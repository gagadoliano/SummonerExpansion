package summonerexpansion.items.equips.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class SharkLavaHelmet extends SetHelmetArmorItem
{
    public FloatUpgradeValue resilienceRegen = (new FloatUpgradeValue()).setBaseValue(-0.50F).setUpgradedValue(1, -0.25F).setUpgradedValue(10, 1.00F);
    public FloatUpgradeValue summonDMG = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1, 0.40F).setUpgradedValue(10, 0.80F);
    public IntUpgradeValue maxResilience = (new IntUpgradeValue()).setBaseValue(10).setUpgradedValue(1, 25).setUpgradedValue(10, 100);

    public SharkLavaHelmet(int enchantCost, Item.Rarity rarityTier)
    {
        super(5, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "sharklavahelmet", "sharklavachestplate", "sharklavaboots", "sharklavasetbonus");
        hairDrawOptions = HairDrawMode.NO_HAIR;
        canBeUsedForRaids = true;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, summonDMG.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.RESILIENCE_REGEN_FLAT, resilienceRegen.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.MAX_RESILIENCE_FLAT, maxResilience.getValue(getUpgradeTier(item))));
    }
}