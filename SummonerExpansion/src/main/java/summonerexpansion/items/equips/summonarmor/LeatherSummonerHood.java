package summonerexpansion.items.equips.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class LeatherSummonerHood extends SetHelmetArmorItem
{
    public FloatUpgradeValue summonAttackSpeed = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.25F).setUpgradedValue(10, 0.50F);

    public LeatherSummonerHood()
    {
        super(1, DamageTypeRegistry.SUMMON, 50, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.COMMON, "leathersummonerhood", "leathershirt", "leatherboots", "leathersummonersetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_ATTACK_SPEED, summonAttackSpeed.getValue(getUpgradeTier(item))));
    }
}