package summonerexpansion.items.weapons.base;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

import java.awt.*;

public class BaseSummonSecondarySetWeapon extends SummonToolItem
{
    public String weaponTooltip;
    public String weaponArmorSet;
    public Color tooltipColor;
    public IntUpgradeValue maxSummon = (new IntUpgradeValue()).setBaseValue(1);

    public BaseSummonSecondarySetWeapon(float baseDamage, float t1Damage, int maxSummon, int t1MaxSummon, int enchantCost, FollowPosition minionPosition, Item.Rarity rarityTier, String minion, String weaponTooltip, String weaponArmorSet, Color tooltipColor)
    {
        super(minion, minionPosition, 1f, enchantCost, SummonWeaponsLootTable.summonWeapons);
        summonType = minion;
        rarity = rarityTier;
        attackDamage.setBaseValue(baseDamage).setUpgradedValue(1, t1Damage);
        canBeUsedForRaids = false;
        this.weaponTooltip = weaponTooltip;
        this.maxSummon.setBaseValue(maxSummon).setUpgradedValue(1, t1MaxSummon);
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob)
    {
        return maxSummon.getValue(getUpgradeTier(item));
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/weapons/" + getStringID());
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", weaponTooltip));
        if (perspective == null)
        {
            return tooltips;
        }
        else if (perspective.buffManager.hasBuff(weaponArmorSet))
        {
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", weaponTooltip + "2"), tooltipColor));
        }
        return tooltips;
    }
}