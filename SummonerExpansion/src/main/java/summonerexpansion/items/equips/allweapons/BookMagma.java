package summonerexpansion.items.equips.allweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingJumpingMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

public class BookMagma extends SummonToolItem
{
    public IntUpgradeValue maxSummons = new IntUpgradeValue(3, 0.0F);

    public BookMagma(int enchantCost, Item.Rarity rarityTier)
    {
        super("magmaslimeminion", FollowPosition.SLIME_CIRCLE_MOVEMENT, 1F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        summonType = "summonedmagmaslime";
        rarity = rarityTier;
        drawMaxSummons = false;
        canBeUsedForRaids = true;
        attackAnimTime.setBaseValue(400);
        attackDamage.setBaseValue(22.0F).setUpgradedValue(1, 35.0F);
        maxSummons.setBaseValue(3).setUpgradedValue(1, 6).setUpgradedValue(10,10);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob){return maxSummons.getValue(getUpgradeTier(item));}

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        AttackingFollowingJumpingMob mob1 = (AttackingFollowingJumpingMob) MobRegistry.getMob("magmaslimeminion", level);
        summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (level.isClient())
        {
            SoundManager.playSound(GameResources.magicbolt4, SoundEffect.effect(attackerMob).volume(0.3F).pitch(GameRandom.globalRandom.getFloatBetween(1.6F, 1.8F)));
        }
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bookmagmatip"));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}