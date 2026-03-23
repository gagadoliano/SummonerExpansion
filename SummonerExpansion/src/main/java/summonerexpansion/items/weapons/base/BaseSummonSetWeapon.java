package summonerexpansion.items.weapons.base;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

import java.awt.*;

public class BaseSummonSetWeapon extends SummonToolItem
{
    public String weaponTooltip;
    public String weaponArmorSet;
    public Color tooltipColor;

    public BaseSummonSetWeapon(float baseDamage, float t1Damage, float space, int enchantCost, FollowPosition minionPosition, Item.Rarity rarityTier, String minion, String weaponTooltip, String weaponArmorSet, Color tooltipColor)
    {
        super(minion, minionPosition, space, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(baseDamage).setUpgradedValue(1, t1Damage);
        canBeUsedForRaids = false;
        this.weaponTooltip = weaponTooltip;
        this.weaponArmorSet = weaponArmorSet;
        this.tooltipColor = tooltipColor;
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