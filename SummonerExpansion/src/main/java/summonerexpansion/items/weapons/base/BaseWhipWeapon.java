package summonerexpansion.items.weapons.base;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.*;
import necesse.engine.util.tween.Easings;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.entity.projectile.pathProjectile.SurvivorWhipProjectile;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import summonerexpansion.projectiles.melee.WhipBaseProj;

import java.awt.*;
import java.awt.geom.Point2D;

public class BaseWhipWeapon extends SwordToolItem
{
    protected FloatUpgradeValue projectileResilienceGain = new FloatUpgradeValue(0.0F, 0.0F);
    public Color whipColor;

    public BaseWhipWeapon(int enchantCost, Item.Rarity rarityTier, Color whipColor)
    {
        super(enchantCost, SummonWeaponsLootTable.summonWeapons);
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        rarity = rarityTier;
        damageType = DamageTypeRegistry.SUMMON;
        attackAnimTime.setBaseValue(800);
        attackDamage.setBaseValue(5F);
        attackRange.setBaseValue(200).setUpgradedValue(1F, 300).setUpgradedValue(10F, 500);
        knockback.setBaseValue(75);
        projectileResilienceGain.setBaseValue(1F).setUpgradedValue(1, 3F);
        canBeUsedForRaids = false;
        attackXOffset = 16;
        attackYOffset = 16;
        this.whipColor = whipColor;
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
        Projectile projectile = new WhipBaseProj(attackerMob, x, y, getAttackAnimTime(item, attackerMob), frontAttackRange, getAttackDamage(item), getKnockback(item, attackerMob), whipColor,item.item.getUpgradeTier(item) > 0.0F);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(getResilienceGain(item, projectileResilienceGain)));
        projectile.resetUniqueID(new GameRandom(seed));
        attackerMob.addAndSendAttackerProjectile(projectile);
        return item;
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress)
    {
        if (attackProgress < 0.5F)
        {
            attackProgress *= 2.0F;
            attackProgress = GameMath.limit(GameMath.lerp(attackProgress, 0.0F, 1.5F), 0.0F, 1.0F);
            attackProgress = Easings.CubicIn.ease(attackProgress);
            drawOptions.rotation(getSwingRotation(item, drawOptions.dir, attackProgress) - 110.0F);
        }
        else
        {
            attackProgress = (attackProgress - 0.5F) * 2.0F;
            attackProgress = GameMath.limit(GameMath.lerp(attackProgress, 0.0F, 1.5F), 0.0F, 1.0F);
            attackProgress = Easings.CubicIn.ease(attackProgress);
            drawOptions.rotation(getSwingRotation(item, drawOptions.dir, 1.0F - attackProgress) - 110.0F);
        }
    }

    public int getItemAttackerAttackRange(ItemAttackerMob mob, InventoryItem item)
    {
        return (int)((float)getAttackRange(item) * 0.9F);
    }

    protected SoundSettings getSwingSound() {
        return null;
    }

    protected SoundSettings getAttackSound() {
        return null;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/weapons/" + getStringID());
    }
}