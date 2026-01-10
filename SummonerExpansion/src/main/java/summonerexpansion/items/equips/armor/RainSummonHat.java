package summonerexpansion.items.equips.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

import java.awt.*;

public class RainSummonHat extends SetHelmetArmorItem
{
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
    public IntUpgradeValue fishPower = (new IntUpgradeValue()).setBaseValue(5).setUpgradedValue(1, 15).setUpgradedValue(10, 20);

    public RainSummonHat(int enchantCost, Item.Rarity rarityTier)
    {
        super(2, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "rainsummonhat", "rainsummoncoat", "rainsummonboots", "rainsummonsetbonus");
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        facialFeatureDrawOptions = FacialFeatureDrawMode.OVER_FACIAL_FEATURE;
        hairMaskTextureName = "rainhat_hairmask";
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.FISHING_POWER, fishPower.getValue(getUpgradeTier(item))));
    }
}