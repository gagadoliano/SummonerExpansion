package summonerexpansion.items.equips.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.FeetArmorLootTable;

public class RedSpiderBoots extends BootsArmorItem
{
    public FloatUpgradeValue moveSpd = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.25F).setUpgradedValue(10F, 0.35F);
    public FloatUpgradeValue summonSpd = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.35F);

    public RedSpiderBoots()
    {
        super(4, 50, Rarity.UNCOMMON, "redspiderboots", FeetArmorLootTable.feetArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SPEED, moveSpd.getValue(getUpgradeTier(item))), new ModifierValue(BuffModifiers.SUMMONS_SPEED, summonSpd.getValue(getUpgradeTier(item))));
    }
}