package summonerexpansion.items.equips.allweapons.supportsummon;

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
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.items.equips.allweapons.basesummon.BaseSupportWeapon;

public class XmasTreeScepter extends BaseSupportWeapon
{
    public XmasTreeScepter(int enchantCost, Item.Rarity rarityTier)
    {
        super("xmastreesentry", FollowPosition.WALK_CLOSE, 1F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        summonType = "summonedxmastree";
        rarity = rarityTier;
        drawMaxSummons = false;
        canBeUsedForRaids = false;
        attackDamage.setBaseValue(0F).setUpgradedValue(1, 0F);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob) { return 1; }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        AttackingFollowingMob mob1 = (AttackingFollowingMob) MobRegistry.getMob("xmastreesentry", level);
        summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "supportsentrytip"));
        tooltips.add(Localization.translate("itemtooltip", "xmastreetip"));
        return tooltips;
    }
}