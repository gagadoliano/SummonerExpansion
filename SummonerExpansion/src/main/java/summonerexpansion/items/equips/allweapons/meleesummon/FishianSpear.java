package summonerexpansion.items.equips.allweapons.meleesummon;

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
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.spearToolItem.SpearToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.mobs.summonminions.meleeminions.*;

import java.awt.geom.Point2D;

public class FishianSpear extends SpearToolItem
{
    public IntUpgradeValue maxFishians = (new IntUpgradeValue()).setBaseValue(2);

    public FishianSpear(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, SummonWeaponsLootTable.summonWeapons);
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        rarity = rarityTier;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 45.0F);
        attackAnimTime.setBaseValue(500);
        resilienceGain.setBaseValue(0.5F).setUpgradedValue(1, 1.5F).setUpgradedValue(10, 4.0F);
        attackRange.setBaseValue(80);
        knockback.setBaseValue(25);
        width = 80.0F;
        maxFishians.setBaseValue(2).setUpgradedValue(1, 4).setUpgradedValue(5, 6);
        canBeUsedForRaids = true;
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
            attackerMob.serverFollowersManager.addFollower("summonedfishianminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, maxFishians.getValue(getUpgradeTier(item)), null, false);
            mob.updateDamage(getAttackDamage(item));
            mob.setEnchantment(getEnchantment(item));
            mob.dx = dir.x * 300.0F;
            mob.dy = dir.y * 300.0F;
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x + dir.x, attackerMob.y + dir.y);

            attackerMob.buffManager.removeBuff("fishianstack", true);
        }
        return item;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "fishianspeartip"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", maxFishians.getValue(getUpgradeTier(item))));
        return tooltips;
    }
}