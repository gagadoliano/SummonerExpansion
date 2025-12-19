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

public class SpiderBrideChest extends ChestArmorItem
{
    public FloatUpgradeValue damageSummon = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.15F).setUpgradedValue(10F, 0.35F);

    public SpiderBrideChest(int enchantCost, Item.Rarity rarityTier)
    {
        super(25, enchantCost, rarityTier, "spiderbridechest", "spiderbridearms", BodyArmorLootTable.bodyArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, damageSummon.getValue(getUpgradeTier(item))));
    }
}