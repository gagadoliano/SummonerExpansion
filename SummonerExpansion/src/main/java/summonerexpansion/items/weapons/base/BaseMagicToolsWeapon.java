package summonerexpansion.items.weapons.base;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

public class BaseMagicToolsWeapon extends SummonToolItem
{
    public String minion1;
    public String minion2;
    public String weaponTooltip;
    public IntUpgradeValue maxSummons = new IntUpgradeValue(3, 0.0F);

    public BaseMagicToolsWeapon(float baseDamage, float t1Damage, int knock, int enchantCost, Item.Rarity rarityTier, String minion1, String minion2, String weaponTooltip)
    {
        super(minion1, FollowPosition.FLYING_CIRCLE, 1F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        summonType = "summonedmagictoolminion";
        rarity = rarityTier;
        drawMaxSummons = true;
        canBeUsedForRaids = false;
        knockback.setBaseValue(knock);
        attackDamage.setBaseValue(baseDamage).setUpgradedValue(1, t1Damage);
        maxSummons.setBaseValue(3).setUpgradedValue(1, 6).setUpgradedValue(10, 9);
        this.weaponTooltip = weaponTooltip;
        this.minion1 = minion1;
        this.minion2 = minion2;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/weapons/" + getStringID());
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob) {return maxSummons.getValue(getUpgradeTier(item));}

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        FlyingAttackingFollowingMob mob1 = (FlyingAttackingFollowingMob) MobRegistry.getMob(minion1, level);
        FlyingAttackingFollowingMob mob2 = (FlyingAttackingFollowingMob) MobRegistry.getMob(minion2, level);

        if (GameRandom.globalRandom.nextInt(2) == 1)
        {
            summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        }
        else
        {
            summonServerMob(attackerMob, mob2, x, y, attackHeight, item);
        }
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", weaponTooltip));
        tooltips.add(Localization.translate("itemtooltip", "secondarysummon"));
        tooltips.add(Localization.translate("itemtooltip", "minionactivecap", "amount", this.getMaxSummons(item, perspective)));
        return tooltips;
    }
}