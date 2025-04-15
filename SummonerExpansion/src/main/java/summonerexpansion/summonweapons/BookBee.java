package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookBee extends SummonToolItem
{
    public BookBee()
    {
        super("beebookminion", FollowPosition.FLYING_CIRCLE, 1F, 200);
        summonType = "beebookminion";
        rarity = Rarity.COMMON;
        drawMaxSummons = false;
        canBeUsedForRaids = false;
        attackDamage.setBaseValue(10.0F).setUpgradedValue(1, 30.0F);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob)
    {
        return 6;
    }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        FlyingAttackingFollowingMob mob1 = (FlyingAttackingFollowingMob) MobRegistry.getMob("beebookminion", level);
        FlyingAttackingFollowingMob mob2 = (FlyingAttackingFollowingMob) MobRegistry.getMob("beebookminion", level);
        FlyingAttackingFollowingMob mob3 = (FlyingAttackingFollowingMob) MobRegistry.getMob("beebookminion", level);

        summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        summonServerMob(attackerMob, mob2, x, y, attackHeight, item);
        summonServerMob(attackerMob, mob3, x, y, attackHeight, item);
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bookbeetip"));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}