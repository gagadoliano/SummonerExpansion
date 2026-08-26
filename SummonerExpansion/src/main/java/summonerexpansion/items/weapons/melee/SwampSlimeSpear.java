package summonerexpansion.items.weapons.melee;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.PoisonSlimeFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.items.weapons.base.BaseSummonSpearWeapon;

import java.awt.geom.Point2D;

public class SwampSlimeSpear extends BaseSummonSpearWeapon
{
    public IntUpgradeValue minionGroupSize = (new IntUpgradeValue()).setBaseValue(2);

    public SwampSlimeSpear(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackDamage.setBaseValue(30F).setUpgradedValue(1, 100F);
        attackAnimTime.setBaseValue(500).setUpgradedValue(1, 400);
        resilienceGain.setBaseValue(0.5F).setUpgradedValue(1, 1.5F).setUpgradedValue(10, 4.0F);
        attackRange.setBaseValue(120);
        knockback.setBaseValue(15);
        minionGroupSize.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(5, 10);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        InventoryItem out = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
        if (animAttack == 0 && attackerMob.isServer() && GameRandom.globalRandom.getChance(0.20F) && attackerMob.serverFollowersManager.getFollowerCount("swampslimespearminion") < minionGroupSize.getValue(getUpgradeTier(item)))
        {
            Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y + (float)attackHeight);
            PoisonSlimeFollowingMob mob = new PoisonSlimeFollowingMob();
            attackerMob.serverFollowersManager.addFollower("swampslimespearminion", mob, FollowPosition.SLIME_CIRCLE_MOVEMENT, "summonedmob", 1, minionGroupSize.getValue(getUpgradeTier(item)), null, false);
            mob.updateDamage(getAttackDamage(item));
            mob.setEnchantment(getEnchantment(item));
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x + dir.x, attackerMob.y + dir.x);
        }
        return out;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "swampslimespeartip"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", minionGroupSize.getValue(getUpgradeTier(item))));
        return tooltips;
    }
}