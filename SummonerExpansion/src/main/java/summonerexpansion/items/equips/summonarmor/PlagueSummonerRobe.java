package summonerexpansion.items.equips.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.BodyArmorLootTable;

public class PlagueSummonerRobe extends ChestArmorItem
{
    public FloatUpgradeValue damageSummon = (new FloatUpgradeValue()).setBaseValue(0.08F).setUpgradedValue(1F, 0.12F).setUpgradedValue(10F, 0.30F);

    public PlagueSummonerRobe()
    {
        super(15, 550, Rarity.UNCOMMON, "summonplaguerobe", "summonplaguearms", BodyArmorLootTable.bodyArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_DAMAGE, damageSummon.getValue(getUpgradeTier(item))));
    }
}