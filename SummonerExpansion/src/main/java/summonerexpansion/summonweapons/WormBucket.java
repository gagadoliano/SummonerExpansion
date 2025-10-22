package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem.BoomerangToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

public class WormBucket extends BoomerangToolItem
{
    public IntUpgradeValue wormAmount = (new IntUpgradeValue()).setBaseValue(1);

    public WormBucket()
    {
        super(200, SummonWeaponsLootTable.summonWeapons, "wormproj");
        rarity = Rarity.COMMON;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(10.0F).setUpgradedValue(1, 35.0F);
        attackAnimTime.setBaseValue(300).setUpgradedValue(1, 280);
        resilienceGain.setBaseValue(0F).setUpgradedValue(1, 0.1F).setUpgradedValue(10, 0.2F);
        attackRange.setBaseValue(400);
        velocity.setBaseValue(100).setUpgradedValue(1, 120);
        wormAmount.setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(3, 3).setUpgradedValue(5, 4);
        canBeUsedForRaids = true;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "wormbuckettip"));
        return tooltips;
    }

    public String canAttack(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return attackerMob.getBoomerangsUsage() < wormAmount.getValue(getUpgradeTier(item)) ? null : "";
    }
}