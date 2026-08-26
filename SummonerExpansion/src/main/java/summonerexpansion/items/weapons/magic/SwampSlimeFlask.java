package summonerexpansion.items.weapons.magic;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.*;
import necesse.entity.Entity;
import necesse.entity.levelEvent.explosionEvent.splashEvent.NecroPoisonSplashEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import summonerexpansion.items.weapons.base.BaseSummonMagicWeapon;
import summonerexpansion.mobs.minions.magic.SwampWitchMinion;
import summonerexpansion.projectiles.magic.SwampSlimeFlaskProj;

import java.awt.*;
import java.awt.geom.Point2D;

public class SwampSlimeFlask extends BaseSummonMagicWeapon implements ItemInteractAction
{
    protected IntUpgradeValue swampSplashRange = (new IntUpgradeValue()).setBaseValue(1);

    public SwampSlimeFlask(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackAnimTime.setBaseValue(600).setUpgradedValue(1, 500);
        attackDamage.setBaseValue(50F).setUpgradedValue(1, 100F);
        attackXOffset = 8;
        attackYOffset = 10;
        velocity.setBaseValue(800);
        attackCooldownTime.setBaseValue(600).setUpgradedValue(1, 500);
        attackRange.setBaseValue(500).setUpgradedValue(1, 800).setUpgradedValue(10, 1200);
        manaCost.setBaseValue(5F).setUpgradedValue(1, 10F);
        swampSplashRange.setBaseValue(100).setUpgradedValue(1, 120).setUpgradedValue(10, 300);
        canBeUsedForRaids = false;
        itemAttackerProjectileCanHitWidth = 5.0F;
    }

    protected Projectile getProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, GameRandom random, InventoryItem item)
    {
        return new SwampSlimeFlaskProj(level, owner, x, y, targetX, targetY, speed, distance, damage, knockback, swampSplashRange.getValue(getUpgradeTier(item)));
    }

    public GameSprite getAttackSprite(InventoryItem item, PlayerMob player) {
        return null;
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress)
    {
        if (this.getAnimInverted(item))
        {
            drawOptions.swingRotationInv(attackProgress);
        }
        else
        {
            drawOptions.swingRotation(attackProgress);
        }
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (level.isClient())
        {
            SoundManager.playSound(GameResources.swing1, SoundEffect.effect(attackerMob).volume(0.7F).pitch(GameRandom.globalRandom.getFloatBetween(0.9F, 1.0F)));
        }
    }

    public Point getItemAttackerAttackPosition(Level level, ItemAttackerMob attackerMob, Mob target, int seed, InventoryItem item)
    {
        int velocity = this.getProjectileVelocity(item, attackerMob);
        float velocityPercent = 1.0F / ((float)velocity / 500.0F);
        int flaskAirTime = (int)(500.0F * velocityPercent);
        float predictedX = target.x + Entity.getPositionAfterMillis(target.dx, (float)flaskAirTime);
        float predictedY = target.y + Entity.getPositionAfterMillis(target.dy, (float)flaskAirTime);
        return this.applyInaccuracy(attackerMob, item, new Point((int)predictedX, (int)predictedY));
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        int attackRange = this.getAttackRange(item);
        float distanceFromTarget = GameMath.getExactDistance(attackerMob.x, attackerMob.y, (float)x, (float)y);
        int distance = (int)GameMath.limit(distanceFromTarget, 0.0F, (float)attackRange);
        Point2D.Float normalizedVector = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y);
        CollisionFilter collisionFilter = (new CollisionFilter()).projectileCollision().addFilter((tp) -> tp.object().object.isWall || tp.object().object.isRock);
        RayLinkedList<LevelObjectHit> hits = GameUtils.castRay(level, attackerMob.x, attackerMob.y, normalizedVector.x, normalizedVector.y, distance, 0, collisionFilter);
        Ray<LevelObjectHit> first = hits.getLast();
        if (first != null && first.targetHit != null)
        {
            x = (int)first.x2;
            y = (int)first.y2;
            distance = (int)GameMath.getExactDistance(attackerMob.x, attackerMob.y, (float)x, (float)y);
        }
        float baseDistance = (float)this.attackRange.getValue(0.0F);
        float distancePercent = (float)distance / baseDistance;
        int velocity = this.getProjectileVelocity(item, attackerMob);
        float baseVelocity = (float)this.velocity.getValue(0.0F);
        float velocityPercent = 1.0F / ((float)velocity / baseVelocity);
        int flaskAirTime = (int)(baseVelocity * velocityPercent * distancePercent);
        float speed = Entity.getTravelSpeedForMillis(flaskAirTime, (float)distance);
        Projectile projectile = this.getProjectile(level, attackerMob, attackerMob.x, attackerMob.y, (float)x, (float)y, speed, distance, this.getAttackDamage(item), this.getKnockback(item, attackerMob), new GameRandom((long)seed * 2548L), item);
        projectile.resetUniqueID(new GameRandom(seed));
        attackerMob.addAndSendAttackerProjectile(projectile, 20);
        this.consumeMana(attackerMob, item);
        return item;
    }

    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return true;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, final ItemAttackerMob attackerMob, int attackHeight, final InventoryItem item, ItemAttackSlot slot, final int seed, GNDItemMap mapContent)
    {
        if (attackerMob.isServer())
        {
            SwampWitchMinion mob1 = new SwampWitchMinion();
            attackerMob.serverFollowersManager.addFollower("swampflaskminion", mob1, FollowPosition.WALK_CLOSE, "summonedmob", 1, 1, null, false);
            attackerMob.getLevel().entityManager.addMob(mob1, attackerMob.x, attackerMob.y);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        float seconds = NecroPoisonSplashEvent.poisonDuration;
        float totalDotDamage = this.getAttackDamage(item).getBuffedDamage(perspective);
        tooltips.add(Localization.translate("itemtooltip", "swampslimeflasktip", "value1", (int)totalDotDamage, "value2", (int)seconds));
        return tooltips;
    }
}