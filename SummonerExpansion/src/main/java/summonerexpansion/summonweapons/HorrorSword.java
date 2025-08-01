package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.summonminions.HorrorCrawlingZombieMinion;

import java.awt.geom.Point2D;

public class HorrorSword extends SwordToolItem
{
    public IntUpgradeValue maxCrawlers = (new IntUpgradeValue()).setBaseValue(2);

    public HorrorSword()
    {
        super(800);
        rarity = Rarity.EPIC;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(40.0F).setUpgradedValue(1.0F, 65.0F);
        attackAnimTime.setBaseValue(1000);
        resilienceGain.setBaseValue(0F).setUpgradedValue(1, 0.1F);
        attackRange.setBaseValue(50);
        knockback.setBaseValue(80);
        maxCrawlers.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(5, 6);
        canBeUsedForRaids = false;
    }

    @Override
    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        if (attacker.isServer())
        {
            ActiveBuff ab = new ActiveBuff(BuffRegistry.getBuff("horrorswordstack"), attacker, 30.0F, attacker);
            attacker.addBuff(ab, true);
        }
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        InventoryItem out = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
        if (animAttack == 0 && attackerMob.isServer() && attackerMob.buffManager.getStacks(BuffRegistry.getBuff("horrorswordstack")) >= 100)
        {
            HorrorCrawlingZombieMinion mob = new HorrorCrawlingZombieMinion();
            Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y + (float)attackHeight);
            attackerMob.serverFollowersManager.addFollower("horrorcrawlingzombieminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, maxCrawlers.getValue(getUpgradeTier(item)), null, false);
            mob.updateDamage(getAttackDamage(item));
            mob.setEnchantment(getEnchantment(item));
            mob.dx = dir.x * 300.0F;
            mob.dy = dir.y * 300.0F;
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x + dir.x, attackerMob.y + dir.y);
            attackerMob.buffManager.removeBuff("horrorswordstack", true);
        }
        return item;
    }

    @Override
    public int getAttackAnimTime(InventoryItem item, ItemAttackerMob attackerMob)
    {
        int horrorBuff = attackerMob.buffManager.getStacks(BuffRegistry.getBuff("horrorswordstack"));
        int horrorStack = 1000 - (horrorBuff * 2);
        int horrorStackT1 = 800 - (horrorBuff * 2);
        attackAnimTime.setBaseValue(horrorStack).setUpgradedValue(1, horrorStackT1);
        return super.getAttackAnimTime(item, attackerMob);
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "horrorswordtip"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", maxCrawlers.getValue(getUpgradeTier(item))));
        return tooltips;
    }
}