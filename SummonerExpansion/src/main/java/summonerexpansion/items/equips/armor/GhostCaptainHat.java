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
import necesse.inventory.lootTable.presets.IncursionArmorSetsLootTable;
import necesse.inventory.lootTable.presets.IncursionHeadArmorLootTable;

public class GhostCaptainHat extends SetHelmetArmorItem
{
    public IntUpgradeValue maxMinion = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1F, 2).setUpgradedValue(10F, 4);
    public FloatUpgradeValue attackSpeedSummon = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.15F).setUpgradedValue(10F, 0.25F);

    public GhostCaptainHat(int enchantCost, Item.Rarity rarityTier)
    {
        super(16, DamageTypeRegistry.SUMMON, enchantCost, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, rarityTier, "ghostcaptainshat", "ghostcaptainsshirt", "ghostcaptainsboots", "ghostcaptainssetbonus");
        facialFeatureDrawOptions = FacialFeatureDrawMode.OVER_FACIAL_FEATURE;
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        defaultLootTier = 1.0F;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, maxMinion.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, attackSpeedSummon.getValue(getUpgradeTier(item))));
    }
}