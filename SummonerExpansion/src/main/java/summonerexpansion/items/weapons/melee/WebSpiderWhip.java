package summonerexpansion.items.weapons.melee;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.*;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import summonerexpansion.items.weapons.base.BaseWhipWeapon;
import summonerexpansion.projectiles.melee.WebWhipProj;

import java.awt.*;
import java.awt.geom.Point2D;

public class WebSpiderWhip extends BaseWhipWeapon
{
    public WebSpiderWhip(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier,  new Color(158, 155, 147));
        attackDamage.setBaseValue(15.0F).setUpgradedValue(1.0F, 150.0F);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        float frontAttackRange = (float)getAttackRange(item);
        Point2D.Float attackDir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y);
        CollisionFilter collisionFilter = (new CollisionFilter()).projectileCollision();
        Ray<LevelObjectHit> frontHit = GameUtils.castRayFirstHit(level, attackerMob.x, attackerMob.y, attackDir.x, attackDir.y, frontAttackRange, collisionFilter);
        if (frontHit != null)
        {
            frontAttackRange = GameMath.limit((float)GameMath.getExactDistance(attackerMob.x, attackerMob.y, frontHit.x2, frontHit.y2) + 32.0F, 32.0F, frontAttackRange);
        }
        Projectile projectile = new WebWhipProj(attackerMob, x, y, getAttackAnimTime(item, attackerMob), frontAttackRange, getAttackDamage(item), getKnockback(item, attackerMob), whipColor,item.item.getUpgradeTier(item) > 0.0F);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(getResilienceGain(item, projectileResilienceGain)));
        projectile.resetUniqueID(new GameRandom(seed));
        attackerMob.addAndSendAttackerProjectile(projectile);
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "webspiderwhiptip"));
        return tooltips;
    }
}