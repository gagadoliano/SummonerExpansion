package summonerexpansion.items.equips.allweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

import java.awt.*;

public class RedSpiderStaff extends SummonToolItem
{
    public int alterType = 0;

    public RedSpiderStaff(int enchantCost, Item.Rarity rarityTier)
    {
        super("redspidermeleeminion", FollowPosition.PYRAMID, 1F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(14.0F).setUpgradedValue(1, 40.0F);
        canBeUsedForRaids = true;
    }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (alterType > 0)
        {
            AttackingFollowingMob mob = (AttackingFollowingMob)MobRegistry.getMob("redspidermeleeminion", level);
            summonServerMob(attackerMob, mob, x, y, attackHeight, item);
            alterType = 0;
        }
        else
        {
            AttackingFollowingMob mob = (AttackingFollowingMob)MobRegistry.getMob("redspiderrangeminion", level);
            summonServerMob(attackerMob, mob, x, y, attackHeight, item);
            alterType++;
        }
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "redspiderstafftip"));
        if (perspective == null)
        {
            return tooltips;
        }
        else if (perspective.buffManager.hasBuff("redspidersetbonus"))
        {
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "redspiderstafftip2"), new Color(200, 15, 15)));
        }
        return tooltips;
    }
}