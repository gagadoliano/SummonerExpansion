package summonerexpansion.items.equips.allweapons.meleesummon;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.projectile.CaveSpiderWebProjectile;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.lootTable.presets.CloseRangeWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.codes.registry.SummonerBuffs;
import summonerexpansion.items.equips.allweapons.basesummon.DruidClaw;

public class DruidSpiderClaw extends DruidClaw implements ItemInteractAction
{
    public DruidSpiderClaw(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, CloseRangeWeaponsLootTable.closeRangeWeapons);
        rarity = rarityTier;
        attackAnimTime.setBaseValue(285);
        attackDamage.setBaseValue(25.0F).setUpgradedValue(1.0F, 100F);
        attackRange.setBaseValue(100);
        knockback.setBaseValue(25);
        maxDashStacks.setBaseValue(4).setUpgradedValue(1, 5);
        dashRange.setBaseValue(130).setUpgradedValue(1, 300);
        debuffDuration.setBaseValue(10f).setUpgradedValue(1, 30f);
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);

        Buff spiderBuff = SummonerBuffs.SummonBuffs.CLAWSPIDERWEB;
        attacker.buffManager.addBuff(new ActiveBuff(spiderBuff, attacker, debuffDuration.getValue(getUpgradeTier(item)), null), true);
        if (attacker.buffManager.getStacks(spiderBuff) >= 5)
        {
            attacker.buffManager.removeBuff(spiderBuff, true);
            attacker.getLevel().entityManager.projectiles.add(new CaveSpiderWebProjectile(attacker.x, attacker.y, target.x, target.y, new GameDamage(DamageTypeRegistry.SUMMON, attackDamage.getValue(getUpgradeTier(item))), attacker, 100));
        }
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "druidspiderclawtip"));
        return tooltips;
    }
}