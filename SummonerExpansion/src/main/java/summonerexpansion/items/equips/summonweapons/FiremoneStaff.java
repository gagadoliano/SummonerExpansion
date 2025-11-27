package summonerexpansion.items.equips.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.EnchantmentRegistry;
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
import necesse.inventory.enchants.Enchantable;
import necesse.inventory.enchants.ItemEnchantment;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.mobs.summonminions.magicminions.*;

import java.util.Set;

public class FiremoneStaff extends MagicProjectileToolItem implements ItemInteractAction
{
    public IntUpgradeValue sentryLevel = (new IntUpgradeValue()).setBaseValue(2);

    public FiremoneStaff()
    {
        super(200, SummonWeaponsLootTable.summonWeapons);
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        rarity = Rarity.COMMON;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 160F);
        manaCost.setBaseValue(1.75F).setUpgradedValue(1, 4F);
        resilienceGain.setBaseValue(0).setUpgradedValue(1, 1F);
        attackAnimTime.setBaseValue(800);
        attackRange.setBaseValue(600).setUpgradedValue(1, 1000);
        velocity.setBaseValue(80).setUpgradedValue(1, 90).setUpgradedValue(5, 150);
        sentryLevel.setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(5, 3);
        knockback.setBaseValue(10);
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
        Projectile projectile = ProjectileRegistry.getProjectile("firemoneproj", level, attackerMob.x, attackerMob.y, (float)x, (float)y, (float)getProjectileVelocity(item, attackerMob), getAttackRange(item), getAttackDamage(item), getKnockback(item, attackerMob), attackerMob);
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
                FiremoneSentry mob1 = new FiremoneSentry();
                attackerMob.serverFollowersManager.addFollower("firemonesentry", mob1, FollowPosition.WALK_CLOSE, "summonedmob", 1, 1, null, false);
                attackerMob.getLevel().entityManager.addMob(mob1, attackerMob.x, attackerMob.y);
            }

            if (sentryLevel.getValue(getUpgradeTier(item)) == 2)
            {
                FiremoneSentryT1 mob2 = new FiremoneSentryT1();
                attackerMob.serverFollowersManager.addFollower("firemonesentryt1", mob2, FollowPosition.WALK_CLOSE, "summonedmob", 1, 1, null, false);
                attackerMob.getLevel().entityManager.addMob(mob2, attackerMob.x, attackerMob.y);
            }

            if (sentryLevel.getValue(getUpgradeTier(item)) > 2)
            {
                FiremoneSentryT5 mob3 = new FiremoneSentryT5();
                attackerMob.serverFollowersManager.addFollower("firemonesentryt5", mob3, FollowPosition.WALK_CLOSE, "summonedmob", 1, 1, null, false);
                attackerMob.getLevel().entityManager.addMob(mob3, attackerMob.x, attackerMob.y);
            }
        }
        return item;
    }

    public ToolItemEnchantment getRandomEnchantment(GameRandom random, InventoryItem item)
    {
        return Enchantable.getRandomEnchantment(random, EnchantmentRegistry.magicItemEnchantments, this.getEnchantmentID(item), ToolItemEnchantment.class);
    }

    public boolean isValidEnchantment(InventoryItem item, ItemEnchantment enchantment)
    {
        return EnchantmentRegistry.magicItemEnchantments.contains(enchantment.getID());
    }

    public Set<Integer> getValidEnchantmentIDs(InventoryItem item)
    {
        return EnchantmentRegistry.magicItemEnchantments;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "clicksentrytip"));
        tooltips.add(Localization.translate("itemtooltip", "firemonesentrytip"));
        return tooltips;
    }
}