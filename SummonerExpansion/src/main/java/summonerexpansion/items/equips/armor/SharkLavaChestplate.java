package summonerexpansion.items.equips.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTexture.GameSprite;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.BodyArmorLootTable;
import necesse.level.maps.Level;

public class SharkLavaChestplate extends ChestArmorItem
{
    public FloatUpgradeValue resilienceGain = (new FloatUpgradeValue()).setBaseValue(0.25F).setUpgradedValue(1, 0.75F).setUpgradedValue(10, 2.00F);
    public FloatUpgradeValue summonSpeed = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1, 0.40F).setUpgradedValue(10, 0.80F);

    public SharkLavaChestplate(int enchantCost, Item.Rarity rarityTier)
    {
        super(7, enchantCost, "sharklavachestplate", "sharklavachestplatearms", BodyArmorLootTable.bodyArmor);
        rarity = rarityTier;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, summonSpeed.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.RESILIENCE_GAIN, resilienceGain.getValue(getUpgradeTier(item))));
    }

    public GameSprite getAttackArmSprite(InventoryItem item, Level level, PlayerMob player, InventoryItem headItem, InventoryItem chestItem, InventoryItem feetItem)
    {
        if (player != null && player.getDir() == 3)
        {
            return armorTexture == null ? null : new GameSprite(armorTexture, 0, 9, 32);
        }
        else
        {
            return super.getAttackArmSprite(item, level, player, headItem, chestItem, feetItem);
        }
    }
}