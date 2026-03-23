package summonerexpansion.items.weapons.melee;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.MouseProjectileAttackHandler;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.level.maps.Level;
import summonerexpansion.items.weapons.base.BaseSummonBoomerangWeapon;
import summonerexpansion.projectiles.melee.GlobeBoomerangProj;

import java.awt.geom.Point2D;

public class GlobeBoomerang extends BaseSummonBoomerangWeapon
{
    public GlobeBoomerang(int enchantCost, Item.Rarity rarityTier, String projID)
    {
        super(30, 50, 300, 180, 4000, 1, enchantCost, rarityTier, projID);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        int boomerangs = GameMath.limit(Math.min(item.getAmount(), item.itemStackSize() - attackerMob.getBoomerangsUsage()), 1, amount.getValue(getUpgradeTier(item)));
        GameRandom random = new GameRandom(seed);
        FollowingProjectile[] projectiles = new FollowingProjectile[boomerangs];
        float angle = GameMath.getAngle(new Point2D.Float((float)x - attackerMob.x, (float)y - attackerMob.y)) + 90.0F;
        float anglePerProjectile = 60.0F;
        float angleOffset = (float)(-boomerangs) * anglePerProjectile / 2.0F + anglePerProjectile / 2.0F;

        for(int i = 0; i < projectiles.length; ++i)
        {
            GlobeBoomerangProj projectile = new GlobeBoomerangProj(level, attackerMob, attackerMob.x, attackerMob.y, (float)x, (float)y, (float)this.getThrowingVelocity(item, attackerMob), this.getAttackRange(item), this.getAttackDamage(item), this.getKnockback(item, attackerMob));
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.setAngle(angle + angleOffset + (float)i * anglePerProjectile);
            attackerMob.boomerangs.add(projectile);
            projectile.resetUniqueID(random);
            projectiles[i] = projectile;
        }
        if (attackerMob.isAttackHandlerFrom(item, slot))
        {
            ((MouseProjectileAttackHandler)attackerMob.getAttackHandler()).addProjectiles(projectiles);
        }
        else
        {
            attackerMob.startAttackHandler(new MouseProjectileAttackHandler(attackerMob, slot, this.getAttackRange(item), 100, x, y, projectiles));
        }
        for(FollowingProjectile projectile : projectiles)
        {
            attackerMob.addAndSendAttackerProjectile(projectile);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "globeboomerangtip"));
        return tooltips;
    }
}