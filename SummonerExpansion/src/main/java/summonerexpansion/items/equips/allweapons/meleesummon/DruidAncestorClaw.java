package summonerexpansion.items.equips.allweapons.meleesummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.lootTable.presets.CloseRangeWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.codes.registry.SummonerBuffs;
import summonerexpansion.items.equips.allweapons.basesummon.DruidClaw;
import summonerexpansion.mobs.summonminions.meleeminions.ClawAncestorKnightMinion;

import java.awt.*;

public class DruidAncestorClaw extends DruidClaw implements ItemInteractAction
{
    public DruidAncestorClaw(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, CloseRangeWeaponsLootTable.closeRangeWeapons);
        rarity = rarityTier;
        attackAnimTime.setBaseValue(270);
        attackDamage.setBaseValue(50.0F).setUpgradedValue(1.0F, 100F);
        attackRange.setBaseValue(100);
        knockback.setBaseValue(50);
        maxDashStacks.setBaseValue(5).setUpgradedValue(1, 5);
        dashRange.setBaseValue(200).setUpgradedValue(1, 300);
        debuffDuration.setBaseValue(10f).setUpgradedValue(1, 30f);
        slashColor = new Color(0, 243, 239);
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        attacker.buffManager.addBuff(new ActiveBuff(SummonerBuffs.SummonBuffs.CLAWANCESTOR, attacker, debuffDuration.getValue(getUpgradeTier(item)), null), true);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        InventoryItem out = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);

        Buff ancestorBuff = SummonerBuffs.SummonBuffs.CLAWANCESTOR;
        if (animAttack == 0 && attackerMob.isServer() && attackerMob.buffManager.getStacks(ancestorBuff) >= 5 && attackerMob.serverFollowersManager.getFollowerCount("clawancestor") < 3)
        {
            attackerMob.buffManager.removeBuff(ancestorBuff, true);
            ClawAncestorKnightMinion mob = new ClawAncestorKnightMinion();
            attackerMob.serverFollowersManager.addFollower("clawancestor", mob, FollowPosition.LARGE_PYRAMID, "summonedmob", 1.0F, 3, null, false);
            mob.updateDamage(getAttackDamage(item).modFinalMultiplier(0.3F));
            mob.setEnchantment(getEnchantment(item));
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x, attackerMob.y);
        }
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "druidancestorclawtip"));
        return tooltips;
    }
}