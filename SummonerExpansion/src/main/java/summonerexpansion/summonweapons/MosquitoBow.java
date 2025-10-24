package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.*;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import summonerexpansion.summonminions.BowMosquitoMinion;
import summonerexpansion.summonprojs.MosquitoBowProj;

import java.awt.*;
import java.awt.geom.Point2D;

public class MosquitoBow  extends BowProjectileToolItem
{
    public IntUpgradeValue maxMosquitos = new IntUpgradeValue(4, 0.0F);
    public int mosquitoStack;
    public int projectileMaxHeight;

    public MosquitoBow()
    {
        super(200, SummonWeaponsLootTable.summonWeapons);
        rarity = Rarity.COMMON;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(40.0F).setUpgradedValue(1, 75.0F);
        resilienceGain.setBaseValue(0F).setUpgradedValue(1, 0.5F);
        attackAnimTime.setBaseValue(1500);
        attackRange.setBaseValue(1600);
        velocity.setBaseValue(350);
        projectileMaxHeight = 600;
        attackXOffset = 12;
        attackYOffset = 28;
        canBeUsedForRaids = true;
        maxMosquitos.setBaseValue(4).setUpgradedValue(1, 6).setUpgradedValue(5, 8);
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        BowMosquitoMinion mob = new BowMosquitoMinion();
        attackerMob.serverFollowersManager.addFollower("mosquitobowminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, maxMosquitos.getValue(getUpgradeTier(item)), null, false);
        mob.updateDamage(getAttackDamage(item));
        mob.setEnchantment(getEnchantment(item));

        if (++mosquitoStack >= 20)
        {
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x, attackerMob.y);
            mosquitoStack = 0;
        }

        return super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
    }

    public Point getItemAttackerAttackPosition(Level level, ItemAttackerMob attackerMob, Mob target, int seed, InventoryItem item)
    {
        int travelTime = (int)Projectile.getTravelTimeMillis((float)this.getProjectileVelocity(item, attackerMob), (float)(this.projectileMaxHeight * 2));
        return this.applyInaccuracy(attackerMob, item, this.getPredictedItemAttackerAttackPositionMillis(target, travelTime));
    }

    public MosquitoBowProj getMosquitoBowProjectile(Level level, int x, int y, Mob owner, GameDamage damage, float velocity, int knockback, float resilienceGain)
    {
        Point2D.Float targetPoints = new Point2D.Float((float)x, (float)y);
        Point2D.Float normalizedVector = GameMath.normalize(targetPoints.x - owner.x, targetPoints.y - owner.y);
        RayLinkedList<LevelObjectHit> hits = GameUtils.castRay(level, owner.x, owner.y, normalizedVector.x, normalizedVector.y, targetPoints.distance(owner.x, owner.y), 0, (new CollisionFilter()).projectileCollision().addFilter((tp) -> tp.object().object.isWall || tp.object().object.isRock));
        if (!hits.isEmpty())
        {
            Ray<LevelObjectHit> first = hits.getLast();
            targetPoints.x = (float)first.x2;
            targetPoints.y = (float)first.y2;
        }
        return new MosquitoBowProj(level, owner, owner.x, owner.y, owner.x, owner.y - 1.0F, velocity, this.projectileMaxHeight, damage, resilienceGain, knockback, targetPoints, false);
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress)
    {
        drawOptions.rotation(-85.0F);
    }

    public Projectile getProjectile(Level level, int x, int y, ItemAttackerMob owner, InventoryItem item, int seed, ArrowItem arrow, boolean consumeAmmo, float velocity, int range, GameDamage damage, int knockback, float resilienceGain, GNDItemMap mapContent)
    {
        return this.getMosquitoBowProjectile(level, x, y, owner, damage, velocity, knockback, resilienceGain);
    }

    protected void addExtraBowTooltips(ListGameTooltips tooltips, InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        super.addExtraBowTooltips(tooltips, item, perspective, blackboard);
    }

    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item)
    {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "mosquitobowtip"), 900);
    }

    public Point getControllerAttackLevelPos(Level level, float aimDirX, float aimDirY, PlayerMob player, InventoryItem item)
    {
        float range = 500.0F;
        return new Point((int)(player.x + aimDirX * range), (int)(player.y + aimDirY * range));
    }
}