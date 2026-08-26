package summonerexpansion.items.armors;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.IncursionArmorSetsLootTable;
import necesse.inventory.lootTable.presets.IncursionHeadArmorLootTable;
import necesse.level.maps.incursion.IncursionData;

public class SlimeHood extends SetHelmetArmorItem
{
    public FloatUpgradeValue summonSpeed = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1, 0.25F).setUpgradedValue(10, 0.50F);
    public FloatUpgradeValue manaRegen = (new FloatUpgradeValue()).setBaseValue(1.5F).setUpgradedValue(1, 2.0F).setUpgradedValue(1, 5.0F);

    public SlimeHood(int enchantCost, Item.Rarity rarityTier)
    {
        super(20, DamageTypeRegistry.SUMMON, enchantCost, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, rarityTier, "slimehood", "slimechestplate", "slimeboots", "slimehoodsetbonus");
        facialFeatureDrawOptions = FacialFeatureDrawMode.OVER_FACIAL_FEATURE;
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        hairMaskTextureName = "snowhood_leatherhood_hairmask";
        canBeUsedForRaids = true;
        useForRaidsOnlyIfObtained = true;
        minRaidTier = 1;
        maxRaidTier = IncursionData.ITEM_TIER_UPGRADE_CAP;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, summonSpeed.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.COMBAT_MANA_REGEN, manaRegen.getValue(getUpgradeTier(item))));
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/armors/" + getStringID());
    }
}