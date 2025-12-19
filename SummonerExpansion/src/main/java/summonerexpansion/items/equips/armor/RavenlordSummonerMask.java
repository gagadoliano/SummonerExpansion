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

public class RavenlordSummonerMask extends SetHelmetArmorItem
{
    public IntUpgradeValue maxSummon = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1F, 2).setUpgradedValue(10F, 4);
    public FloatUpgradeValue walkSpeed = (new FloatUpgradeValue()).setBaseValue(0.25F).setUpgradedValue(1F, 0.30F).setUpgradedValue(10F, 0.50F);

    public RavenlordSummonerMask(int enchantCost, Item.Rarity rarityTier)
    {
        super(15, DamageTypeRegistry.SUMMON, enchantCost, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, rarityTier, "ravenlordsummonmask", "ravenlordschestplate", "ravenlordsboots", "ravenlordsummonsetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
        defaultLootTier = 1F;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.MAX_SUMMONS, maxSummon.getValue(getUpgradeTier(item))), new ModifierValue(BuffModifiers.SPEED, walkSpeed.getValue(getUpgradeTier(item))));
    }
}