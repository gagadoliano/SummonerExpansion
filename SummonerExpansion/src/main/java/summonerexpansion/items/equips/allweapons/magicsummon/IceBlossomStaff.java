package summonerexpansion.items.equips.allweapons.magicsummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.allprojs.magicprojs.IceBlossomProj;
import summonerexpansion.items.equips.allweapons.basesummon.BaseMagicWeapon;
import summonerexpansion.mobs.summonminions.magicminions.*;

public class IceBlossomStaff extends BaseMagicWeapon implements ItemInteractAction
{
    protected IntUpgradeValue ricochets = (new IntUpgradeValue()).setBaseValue(1);

    public IceBlossomStaff(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackDamage.setBaseValue(25.0F).setUpgradedValue(1, 155F);
        manaCost.setBaseValue(1.75F).setUpgradedValue(1, 4F);
        resilienceGain.setBaseValue(0).setUpgradedValue(1, 1F);
        knockback.setBaseValue(20);
        attackAnimTime.setBaseValue(800);
        attackRange.setBaseValue(700).setUpgradedValue(1, 1000);
        velocity.setBaseValue(60).setUpgradedValue(1, 70).setUpgradedValue(5, 150);
        ricochets.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(10, 10);
        canBeUsedForRaids = false;
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        GameRandom random = new GameRandom(seed);
        IceBlossomProj projectile = new IceBlossomProj(level, attackerMob, attackerMob.x, attackerMob.y, (float)x, (float)y, (float)getProjectileVelocity(item, attackerMob), getAttackRange(item), getAttackDamage(item), getKnockback(item, attackerMob), ricochets.getValue(getUpgradeTier(item)));
        projectile.setModifier(new ResilienceOnHitProjectileModifier(getResilienceGain(item)));
        projectile.resetUniqueID(random);
        attackerMob.addAndSendAttackerProjectile(projectile, 55);
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
            IceBlossomSentry mob1 = new IceBlossomSentry();
            attackerMob.serverFollowersManager.addFollower("iceblossomsentry", mob1, FollowPosition.WALK_CLOSE, "summonedmob", 1, 1, null, false);
            attackerMob.getLevel().entityManager.addMob(mob1, attackerMob.x, attackerMob.y);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "clicksentrytip"));
        tooltips.add(Localization.translate("itemtooltip", "iceblossomsentrytip"));
        return tooltips;
    }
}