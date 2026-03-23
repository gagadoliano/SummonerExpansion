package summonerexpansion.items.weapons.base;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

public class BaseSummonWeapon extends SummonToolItem
{
    public String weaponTooltip;

    public BaseSummonWeapon(float baseDamage, float t1Damage, float space, int enchantCost, FollowPosition minionPosition, Item.Rarity rarityTier, String minion, String weaponTooltip)
    {
        super(minion, minionPosition, space, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(baseDamage).setUpgradedValue(1, t1Damage);
        canBeUsedForRaids = false;
        this.weaponTooltip = weaponTooltip;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/weapons/" + getStringID());
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", weaponTooltip));
        return tooltips;
    }
}