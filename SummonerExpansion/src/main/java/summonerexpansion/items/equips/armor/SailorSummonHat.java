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
import necesse.inventory.lootTable.presets.IncursionArmorSetsLootTable;
import necesse.inventory.lootTable.presets.IncursionHeadArmorLootTable;

public class SailorSummonHat extends SetHelmetArmorItem
{
    public FloatUpgradeValue summonDMG = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.15F).setUpgradedValue(10F, 0.25F);
    public FloatUpgradeValue projVelocity = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.15F).setUpgradedValue(10F, 0.25F);

    public SailorSummonHat(int enchantCost, Item.Rarity rarityTier)
    {
        super(20, DamageTypeRegistry.SUMMON, enchantCost, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, rarityTier, "sailorsummonhat", "sailorsummonshirt", "sailorsummonshoes", "sailorsummonsetbonus");
        facialFeatureDrawOptions = FacialFeatureDrawMode.OVER_FACIAL_FEATURE;
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        hairMaskTextureName = "turban_sailorhat_hunterhood_hairmask";
        defaultLootTier = 1.0F;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, summonDMG.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, projVelocity.getValue(getUpgradeTier(item))));
    }
}