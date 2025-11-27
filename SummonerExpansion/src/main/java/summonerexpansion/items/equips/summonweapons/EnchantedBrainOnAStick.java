package summonerexpansion.items.equips.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

public class EnchantedBrainOnAStick extends SummonToolItem
{
    public EnchantedBrainOnAStick()
    {
        super("enchantedbabyzombieminion", FollowPosition.PYRAMID, 0.5F, 200, SummonWeaponsLootTable.summonWeapons);
        rarity = Rarity.COMMON;
        attackDamage.setBaseValue(12.0F).setUpgradedValue(1, 32.0F);
        canBeUsedForRaids = true;
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        AttackingFollowingMob mob1 = (AttackingFollowingMob) MobRegistry.getMob("enchantedbabyzombieminion", level);
        summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        AttackingFollowingMob mob2 = (AttackingFollowingMob) MobRegistry.getMob("enchantedbabyzombiearcherminion", level);
        summonServerMob(attackerMob, mob2, x, y, attackHeight, item);
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "enchantedbrainonasticktip"));
        return tooltips;
    }
}