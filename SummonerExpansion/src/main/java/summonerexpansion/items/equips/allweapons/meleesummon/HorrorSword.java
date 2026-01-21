package summonerexpansion.items.equips.allweapons.meleesummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
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
import necesse.inventory.item.Item;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.items.equips.allweapons.basesummon.BaseSwordWeapon;
import summonerexpansion.mobs.summonminions.meleeminions.*;

import java.awt.geom.Point2D;

public class HorrorSword extends BaseSwordWeapon
{
    public IntUpgradeValue minionGroupSize = (new IntUpgradeValue()).setBaseValue(2);

    public HorrorSword(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackDamage.setBaseValue(40.0F).setUpgradedValue(1.0F, 65.0F);
        attackAnimTime.setBaseValue(800).setUpgradedValue(1, 600);
        resilienceGain.setBaseValue(0.5F).setUpgradedValue(1, 1.5F).setUpgradedValue(10, 4.0F);
        attackRange.setBaseValue(50).setBaseValue(80);
        knockback.setBaseValue(80).setBaseValue(100);
        minionGroupSize.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(5, 6);
        tierTwoEssencesUpgradeRequirement = "purehorror";
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        if (attacker.isServer())
        {
            ActiveBuff ab = new ActiveBuff(BuffRegistry.getBuff("horrorswordstack"), attacker, 30F, attacker);
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
            attackerMob.serverFollowersManager.addFollower("summonedhorrorcrawlingzombieminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, minionGroupSize.getValue(getUpgradeTier(item)), null, false);
            mob.updateDamage(getAttackDamage(item));
            mob.setEnchantment(getEnchantment(item));
            mob.dx = dir.x * 300.0F;
            mob.dy = dir.y * 300.0F;
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x + dir.x, attackerMob.y + dir.y);
            attackerMob.buffManager.removeBuff("horrorswordstack", true);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "horrorswordtip"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", minionGroupSize.getValue(getUpgradeTier(item))));
        return tooltips;
    }
}