package summonerexpansion.items.weapons.melee;

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
import summonerexpansion.items.weapons.base.BaseSummonSpearWeapon;
import summonerexpansion.mobs.minions.melee.FishianMinion;

import java.awt.geom.Point2D;

public class FishianSpear extends BaseSummonSpearWeapon
{
    public IntUpgradeValue minionGroupSize = (new IntUpgradeValue()).setBaseValue(2);

    public FishianSpear(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 45.0F);
        attackAnimTime.setBaseValue(500);
        resilienceGain.setBaseValue(2.5F).setUpgradedValue(1, 3.0F).setUpgradedValue(10, 5.0F);
        attackRange.setBaseValue(80);
        knockback.setBaseValue(25);
        width = 80.0F;
        minionGroupSize.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(5, 6);
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        if (attacker.isServer())
        {
            ActiveBuff ab = new ActiveBuff(BuffRegistry.getBuff("fishianstack"), attacker, 30.0F, attacker);
            attacker.addBuff(ab, true);
        }
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        InventoryItem out = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
        if (animAttack == 0 && attackerMob.isServer() && attackerMob.buffManager.getStacks(BuffRegistry.getBuff("fishianstack")) >= 100)
        {
            FishianMinion mob = new FishianMinion();
            Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y + (float)attackHeight);
            attackerMob.serverFollowersManager.addFollower("summonedfishianminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, minionGroupSize.getValue(getUpgradeTier(item)), null, false);
            mob.updateDamage(getAttackDamage(item));
            mob.setEnchantment(getEnchantment(item));
            mob.dx = dir.x * 300.0F;
            mob.dy = dir.y * 300.0F;
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x + dir.x, attackerMob.y + dir.y);

            attackerMob.buffManager.removeBuff("fishianstack", true);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "fishianspeartip"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", minionGroupSize.getValue(getUpgradeTier(item))));
        return tooltips;
    }
}