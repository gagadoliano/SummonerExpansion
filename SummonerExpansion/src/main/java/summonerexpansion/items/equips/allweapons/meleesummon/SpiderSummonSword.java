package summonerexpansion.items.equips.allweapons.meleesummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.level.maps.Level;
import summonerexpansion.allprojs.meleeprojs.SpiderSwordProj;
import summonerexpansion.items.equips.allweapons.basesummon.BaseSwordWeapon;

public class SpiderSummonSword extends BaseSwordWeapon
{
    public SpiderSummonSword(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackDamage.setBaseValue(20.0F).setUpgradedValue(1, 70.0F);
        attackAnimTime.setBaseValue(300).setUpgradedValue(1, 250);
        resilienceGain.setBaseValue(0.5F).setUpgradedValue(1, 1.5F).setUpgradedValue(10, 4.0F);
        attackRange.setBaseValue(50).setUpgradedValue(1, 70).setUpgradedValue(10, 100);
        knockback.setBaseValue(70).setUpgradedValue(1, 100);
    }

    public int getItemAttackerAttackRange(ItemAttackerMob mob, InventoryItem item)
    {
        return getAttackRange(item) * 5;
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        Projectile projectile = new SpiderSwordProj(level, attackerMob.x, attackerMob.y, (float)x, (float)y, (int)((float)getAttackRange(item) * 2F), getAttackDamage(item).modFinalMultiplier(0.2F), attackerMob);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(getResilienceGain(item)));
        projectile.resetUniqueID(new GameRandom(seed));
        attackerMob.addAndSendAttackerProjectile(projectile);
        return item;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "spidersummonswordtip"));
        return tooltips;
    }
}