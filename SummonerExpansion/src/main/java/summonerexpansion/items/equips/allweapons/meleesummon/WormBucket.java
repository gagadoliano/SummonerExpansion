package summonerexpansion.items.equips.allweapons.meleesummon;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.EnchantmentRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.enchants.Enchantable;
import necesse.inventory.enchants.ItemEnchantment;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem.BoomerangToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.items.equips.allweapons.basesummon.BaseBoomerangWeapon;

import java.util.Set;

public class WormBucket extends BaseBoomerangWeapon
{
    public IntUpgradeValue boomerangAmount = (new IntUpgradeValue()).setBaseValue(1);

    public WormBucket(int enchantCost, Item.Rarity rarityTier, String projID)
    {
        super(enchantCost, rarityTier, projID);
        attackDamage.setBaseValue(10.0F).setUpgradedValue(1, 35.0F);
        attackAnimTime.setBaseValue(300).setUpgradedValue(1, 280);
        resilienceGain.setBaseValue(0.5F).setUpgradedValue(1, 1.5F).setUpgradedValue(10, 2.0F);
        attackRange.setBaseValue(400);
        velocity.setBaseValue(100).setUpgradedValue(1, 120);
        boomerangAmount.setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
        canBeUsedForRaids = true;
    }

    public String canAttack(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return attackerMob.getBoomerangsUsage() < boomerangAmount.getValue(getUpgradeTier(item)) ? null : "";
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "wormbuckettip"));
        return tooltips;
    }
}