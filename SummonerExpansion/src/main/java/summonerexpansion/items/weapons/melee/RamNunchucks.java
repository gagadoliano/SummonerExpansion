package summonerexpansion.items.weapons.melee;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.items.weapons.base.BaseSummonSwordWeapon;
import summonerexpansion.mobs.minions.melee.RamMinion;

import java.awt.geom.Point2D;

public class RamNunchucks extends BaseSummonSwordWeapon
{
    public IntUpgradeValue minionGroupSize = (new IntUpgradeValue()).setBaseValue(1);

    public RamNunchucks(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackDamage.setBaseValue(25.0F).setUpgradedValue(1, 105.0F);
        attackAnimTime.setBaseValue(300).setUpgradedValue(1, 250);
        resilienceGain.setBaseValue(0.5F).setUpgradedValue(1, 1.5F).setUpgradedValue(10, 4.0F);
        attackRange.setBaseValue(55).setUpgradedValue(1, 60).setUpgradedValue(10, 80);
        knockback.setBaseValue(150).setUpgradedValue(1, 200);
        minionGroupSize.setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(5, 6);
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (level.isClient())
        {
            SoundManager.playSound(GameResources.nunchucks, SoundEffect.effect(attackerMob).volume(0.9F).pitch(GameRandom.globalRandom.getFloatBetween(0.95F, 1.05F)));
        }
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        if (attacker.isServer())
        {
            ActiveBuff ab = new ActiveBuff(BuffRegistry.getBuff("ramnunchuckstack"), attacker, 31F, attacker);
            attacker.addBuff(ab, true);
        }
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        InventoryItem out = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
        if (animAttack == 0 && attackerMob.isServer() && attackerMob.buffManager.getStacks(BuffRegistry.getBuff("ramnunchuckstack")) >= 50)
        {
            RamMinion mob = new RamMinion();
            Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y + (float)attackHeight);
            attackerMob.serverFollowersManager.addFollower("summonedramminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, minionGroupSize.getValue(getUpgradeTier(item)), null, false);
            mob.updateDamage(getAttackDamage(item));
            mob.setEnchantment(getEnchantment(item));
            mob.dx = dir.x * 300.0F;
            mob.dy = dir.y * 300.0F;
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x + dir.x, attackerMob.y + dir.y);
            attackerMob.buffManager.removeBuff("ramnunchuckstack", true);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "ramnunchuckstip"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", minionGroupSize.getValue(getUpgradeTier(item))));
        return tooltips;
    }
}