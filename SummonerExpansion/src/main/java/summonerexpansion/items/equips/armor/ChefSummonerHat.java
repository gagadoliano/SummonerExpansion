package summonerexpansion.items.equips.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.IncursionArmorSetsLootTable;
import necesse.inventory.lootTable.presets.IncursionHeadArmorLootTable;

public class ChefSummonerHat extends SetHelmetArmorItem
{
    public IntUpgradeValue maxHealth = (new IntUpgradeValue()).setBaseValue(10).setUpgradedValue(1F, 15).setUpgradedValue(10F, 50);
    public IntUpgradeValue maxSummon = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 3);

    public ChefSummonerHat(int enchantCost, Item.Rarity rarityTier)
    {
        super(18, DamageTypeRegistry.SUMMON, enchantCost, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, rarityTier, "chefsummonerhat", "battlechefchestplate", "battlechefboots", "chefsummonerhatsetbonus");
        hairDrawOptions = HairDrawMode.OVER_HAIR;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMONS_SPEED, -0.25F), new ModifierValue(BuffModifiers.MAX_SUMMONS, maxSummon.getValue(getUpgradeTier(item))), new ModifierValue(BuffModifiers.MAX_HEALTH_FLAT, maxHealth.getValue(getUpgradeTier(item))));
    }
}