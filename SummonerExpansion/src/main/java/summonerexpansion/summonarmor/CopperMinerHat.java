package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class CopperMinerHat extends SetHelmetArmorItem
{
    public FloatUpgradeValue mineUp = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1.0F, 0.25F);

    public CopperMinerHat()
    {
        super(4, DamageTypeRegistry.SUMMON, 50, Rarity.COMMON, "copperminerhat", "copperchestplate", "copperboots", "copperminersetbonus");
        facialFeatureDrawOptions = FacialFeatureDrawMode.OVER_FACIAL_FEATURE;
        hairDrawOptions = HairDrawMode.OVER_HAIR;
        hairMaskTextureName = "safarihat_hardhat_minerhat_hairmask";
        canBeUsedForRaids = false;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.MINING_SPEED, mineUp.getValue(this.getUpgradeTier(item))));
    }
}