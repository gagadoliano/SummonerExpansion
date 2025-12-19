package summonerexpansion.items.equips.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.BodyArmorLootTable;

public class GhostCaptainShirt extends ChestArmorItem
{
    public FloatUpgradeValue damageSummon = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.15F).setUpgradedValue(10, 0.30F);

    public GhostCaptainShirt(int enchantCost, Item.Rarity rarityTier)
    {
        super(20, enchantCost, rarityTier, "ghostcaptainsshirt", "ghostcaptainsarms", BodyArmorLootTable.bodyArmor);
        defaultLootTier = 1.0F;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, damageSummon.getValue(getUpgradeTier(item))));
    }
}