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
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class CopperMinerHat extends SetHelmetArmorItem
{
    public FloatUpgradeValue mineSpeed = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.50F);
    public FloatUpgradeValue summonDMG = (new FloatUpgradeValue()).setBaseValue(0F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.50F);

    public CopperMinerHat(int enchantCost, Item.Rarity rarityTier)
    {
        super(3, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "copperminerhat", "copperchestplate", "copperboots", "copperminersetbonus");
        facialFeatureDrawOptions = FacialFeatureDrawMode.OVER_FACIAL_FEATURE;
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        hairMaskTextureName = "safarihat_hardhat_minerhat_hairmask";
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.MINING_SPEED, mineSpeed.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, summonDMG.getValue(getUpgradeTier(item))));
    }
}