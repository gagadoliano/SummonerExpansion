package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class SpiderBrideHelmet extends SetHelmetArmorItem
{
    public FloatUpgradeValue summonDmg = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1.0F, 0.20F);
    public FloatUpgradeValue summonSpd = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1.0F, 0.20F);

    public SpiderBrideHelmet()
    {
        super(19, DamageTypeRegistry.SUMMON, 600, Rarity.UNCOMMON, "spiderbridehelmet", "spiderbridechest", "spiderbrideboots", "spiderbridehelmetsetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
        canBeUsedForRaids = true;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, summonDmg.getValue(getUpgradeTier(item))), new ModifierValue(BuffModifiers.SUMMON_ATTACK_SPEED, summonSpd.getValue(getUpgradeTier(item))));
    }
}