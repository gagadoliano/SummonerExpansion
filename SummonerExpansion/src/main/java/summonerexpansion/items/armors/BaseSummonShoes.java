package summonerexpansion.items.armors;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.FeetArmorLootTable;

public class BaseSummonShoes extends BootsArmorItem
{
    public FloatUpgradeValue speed;

    public BaseSummonShoes(FloatUpgradeValue speed, String name, int armor, int enchantCost, Item.Rarity rarityTier)
    {
        super(armor, enchantCost, rarityTier, name, FeetArmorLootTable.feetArmor);
        this.speed = speed;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SPEED, speed.getValue(getUpgradeTier(item))));
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/armors/" + getStringID());
    }
}