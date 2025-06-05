package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.summonminions.SunflowerSentry;
import summonerexpansion.summonminions.SunflowerSentryT1;
import summonerexpansion.summonminions.SunflowerSentryT5;

public class SunflowerStaff extends MagicProjectileToolItem implements ItemInteractAction
{
    public IntUpgradeValue sentryLevel = (new IntUpgradeValue()).setBaseValue(2);

    public SunflowerStaff()
    {
        super(200);
        rarity = Rarity.COMMON;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(20.0F).setUpgradedValue(1, 150F);
        manaCost.setBaseValue(1.75F).setUpgradedValue(1, 4F);
        resilienceGain.setBaseValue(0).setUpgradedValue(1, 1F);
        attackAnimTime.setBaseValue(800);
        attackRange.setBaseValue(500).setUpgradedValue(1, 1000);
        velocity.setBaseValue(70).setUpgradedValue(1, 80).setUpgradedValue(5, 150);
        sentryLevel.setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(5, 3);
        knockback.setBaseValue(5);
        attackXOffset = 20;
        attackYOffset = 20;
        itemAttackerProjectileCanHitWidth = 5.0F;
        itemAttackerPredictionDistanceOffset = -20.0F;
        canBeUsedForRaids = false;
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (level.isClient())
        {
            SoundManager.playSound(GameResources.magicbolt2, SoundEffect.effect(attackerMob).volume(0.4F).pitch(GameRandom.globalRandom.getFloatBetween(0.8F, 0.9F)));
        }
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        Projectile projectile = ProjectileRegistry.getProjectile("sunflowerproj", level, attackerMob.x, attackerMob.y, (float)x, (float)y, (float)getProjectileVelocity(item, attackerMob), getAttackRange(item), getAttackDamage(item), getKnockback(item, attackerMob), attackerMob);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(getResilienceGain(item)));
        GameRandom random = new GameRandom(seed);
        projectile.resetUniqueID(random);
        attackerMob.addAndSendAttackerProjectile(projectile, 20);
        consumeMana(attackerMob, item);
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
            if (sentryLevel.getValue(getUpgradeTier(item)) == 1)
            {
                SunflowerSentry mob1 = new SunflowerSentry();
                attackerMob.serverFollowersManager.addFollower("sunflowersentry", mob1, FollowPosition.WALK_CLOSE, "summonedmob", 1, 1, null, false);
                attackerMob.getLevel().entityManager.addMob(mob1, attackerMob.x, attackerMob.y);
            }

            if (sentryLevel.getValue(getUpgradeTier(item)) == 2)
            {
                SunflowerSentryT1 mob2 = new SunflowerSentryT1();
                attackerMob.serverFollowersManager.addFollower("sunflowersentryt1", mob2, FollowPosition.WALK_CLOSE, "summonedmob", 1, 1, null, false);
                attackerMob.getLevel().entityManager.addMob(mob2, attackerMob.x, attackerMob.y);
            }

            if (sentryLevel.getValue(getUpgradeTier(item)) > 2)
            {
                SunflowerSentryT5 mob3 = new SunflowerSentryT5();
                attackerMob.serverFollowersManager.addFollower("sunflowersentryt5", mob3, FollowPosition.WALK_CLOSE, "summonedmob", 1, 1, null, false);
                attackerMob.getLevel().entityManager.addMob(mob3, attackerMob.x, attackerMob.y);
            }
        }
        return item;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "clicksentrytip"));
        tooltips.add(Localization.translate("itemtooltip", "sunflowersentrytip"));
        return tooltips;
    }
}