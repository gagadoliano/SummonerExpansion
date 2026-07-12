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
import necesse.inventory.lootTable.presets.FeetArmorLootTable;

public class SpiderBrideBoots extends BootsArmorItem
{
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.40F);

    public SpiderBrideBoots(int enchantCost, Item.Rarity rarityTier)
    {
        super(17, enchantCost, rarityTier, "spiderbrideboots", FeetArmorLootTable.feetArmor);
        drawBodyPart = false;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SPEED, speed.getValue(getUpgradeTier(item))));
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/armors/" + getStringID());
    }
}
