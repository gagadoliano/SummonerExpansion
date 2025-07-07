package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class RedSpiderBoots extends BootsArmorItem
{
    public FloatUpgradeValue moveSpd = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1.0F, 0.25F);
    public FloatUpgradeValue summonSpd = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1.0F, 0.20F);

    public RedSpiderBoots()
    {
        super(4, 50, Rarity.UNCOMMON, "redspiderboots");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SPEED, moveSpd.getValue(this.getUpgradeTier(item))), new ModifierValue(BuffModifiers.SUMMONS_SPEED, summonSpd.getValue(this.getUpgradeTier(item))));
    }
}