package summonerexpansion.items.weapons.melee;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.items.weapons.base.BaseSummonSpearWeapon;
import summonerexpansion.mobs.minions.melee.ApprenticeMinion;

import java.awt.geom.Point2D;

public class VoidLaserSpear extends BaseSummonSpearWeapon
{
    public IntUpgradeValue minionGroupSize = (new IntUpgradeValue()).setBaseValue(1);
    public IntUpgradeValue laserDistance = (new IntUpgradeValue()).setBaseValue(400);

    public VoidLaserSpear(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackDamage.setBaseValue(30F).setUpgradedValue(1, 90F);
        attackAnimTime.setBaseValue(400).setUpgradedValue(1, 300);
        resilienceGain.setBaseValue(1.5F).setUpgradedValue(1, 2.5F).setUpgradedValue(10, 5.0F);
        attackRange.setBaseValue(140);
        knockback.setBaseValue(15);
        minionGroupSize.setBaseValue(1).setUpgradedValue(1, 3).setUpgradedValue(5, 10);
        laserDistance.setBaseValue(400).setUpgradedValue(1, 600).setUpgradedValue(5, 1000);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        InventoryItem out = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
        if (animAttack == 0)
        {
            Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y + (float)attackHeight);
            Projectile projectile = ProjectileRegistry.getProjectile("voidlaser", level, attackerMob.x + dir.x, attackerMob.y + dir.y, attackerMob.x + dir.x * 1000.0F, attackerMob.y + dir.y * 1000.0F, 150.0F, laserDistance.getValue(getUpgradeTier(item)), getAttackDamage(item), attackerMob);
            projectile.resetUniqueID(new GameRandom(seed));
            projectile.moveDist(this.getAttackRange(item) - 35);
            projectile.setModifier(new ResilienceOnHitProjectileModifier(getResilienceGain(item)));
            projectile.traveledDistance = 0.0F;
            attackerMob.addAndSendAttackerProjectile(projectile);

            if (attackerMob.isServer() && GameRandom.globalRandom.getChance(0.10F) && attackerMob.serverFollowersManager.getFollowerCount("apprenticeminion") < minionGroupSize.getValue(getUpgradeTier(item)))
            {
                ApprenticeMinion mob = new ApprenticeMinion();
                attackerMob.serverFollowersManager.addFollower("apprenticeminion", mob, FollowPosition.PYRAMID, "summonedmob", 1, minionGroupSize.getValue(getUpgradeTier(item)), null, false);
                mob.updateDamage(getAttackDamage(item));
                mob.setEnchantment(getEnchantment(item));
                attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x + dir.x, attackerMob.y + dir.x);
            }
        }
        return out;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "voidlaserspeartip"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", minionGroupSize.getValue(getUpgradeTier(item))));
        return tooltips;
    }
}