package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.FeetArmorLootTable;

public class GhostCaptainBoots extends BootsArmorItem
{
    public FloatUpgradeValue speedSummon = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.15F).setUpgradedValue(10F, 0.25F);
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1F, 0.25F).setUpgradedValue(10F, 0.35F);

    public GhostCaptainBoots()
    {
        super(16, 1200, Rarity.LEGENDARY, "ghostcaptainsboots", FeetArmorLootTable.feetArmor);
        drawBodyPart = false;
        defaultLootTier = 1.0F;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SPEED, speed.getValue(getUpgradeTier(item))), new ModifierValue(BuffModifiers.SUMMONS_SPEED, speedSummon.getValue(getUpgradeTier(item))));
    }
}