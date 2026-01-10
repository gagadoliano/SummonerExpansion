package summonerexpansion.items.equips.allweapons.magicsummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.codes.events.PineWoodStaffEvent;
import summonerexpansion.items.equips.allweapons.basesummon.BaseMagicWeapon;
import summonerexpansion.mobs.summonminions.magicminions.PineWoodMinion;

import java.awt.geom.Point2D;

public class PineWoodStaff extends BaseMagicWeapon implements ItemInteractAction
{
    public IntUpgradeValue maxMinion = new IntUpgradeValue().setBaseValue(2);
    public IntUpgradeValue attackStack = new IntUpgradeValue().setBaseValue(20);

    public PineWoodStaff(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackDamage.setBaseValue(40.0F).setUpgradedValue(1, 120F);
        manaCost.setBaseValue(5.0F).setUpgradedValue(1, 10F);
        resilienceGain.setBaseValue(0).setUpgradedValue(1, 1F);
        attackAnimTime.setBaseValue(800);
        attackRange.setBaseValue(500).setUpgradedValue(1, 1000);
        velocity.setBaseValue(150).setUpgradedValue(1, 200).setUpgradedValue(10, 500);
        maxMinion.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(10, 8);
        attackStack.setBaseValue(20).setUpgradedValue(1, 10).setUpgradedValue(10, 5);
        knockback.setBaseValue(50);
        canBeUsedForRaids = false;
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        int range = this.getAttackRange(item);
        Point2D.Float dir = new Point2D.Float((float)(x - attackerMob.getX()), (float)(y - attackerMob.getY()));
        PineWoodStaffEvent event = new PineWoodStaffEvent(attackerMob, attackerMob.getX(), attackerMob.getY(), new GameRandom(seed), GameMath.getAngle(dir), this.getAttackDamage(item), this.getResilienceGain(item), (float)this.getProjectileVelocity(item, attackerMob), (float)this.getKnockback(item, attackerMob), (float)range);
        attackerMob.addAndSendAttackerLevelEvent(event);
        this.consumeMana(attackerMob, item);
        attackerMob.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("pinewoodbuff"), attackerMob, 30F, null), false);
        return item;
    }

    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return attackerMob.buffManager.getStacks(BuffRegistry.getBuff("pinewoodbuff")) >= attackStack.getValue(getUpgradeTier(item));
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, final ItemAttackerMob attackerMob, int attackHeight, final InventoryItem item, ItemAttackSlot slot, final int seed, GNDItemMap mapContent)
    {
        if (attackerMob.isServer())
        {
            PineWoodMinion mob = new PineWoodMinion();
            attackerMob.serverFollowersManager.addFollower("pinewoodminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1, maxMinion.getValue(getUpgradeTier(item)), null, false);
            mob.updateDamage(getAttackDamage(item));
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x, attackerMob.y);
            attackerMob.buffManager.removeBuff("pinewoodbuff", true);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "pinewoodstafftip", "amount", attackStack.getValue(getUpgradeTier(item))));
        tooltips.add(Localization.translate("itemtooltip", "pinewoodstafftip2", "amount", maxMinion.getValue(getUpgradeTier(item))));
        return tooltips;
    }
}