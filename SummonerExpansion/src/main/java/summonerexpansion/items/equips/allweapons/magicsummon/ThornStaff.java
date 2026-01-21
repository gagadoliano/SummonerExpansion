package summonerexpansion.items.equips.allweapons.magicsummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.level.maps.Level;
import summonerexpansion.allprojs.magicprojs.ThornSpikesProj;
import summonerexpansion.items.equips.allweapons.basesummon.BaseMagicWeapon;

public class ThornStaff extends BaseMagicWeapon 
{
    public ThornStaff(int enchantCost, Item.Rarity rarityTier) 
    {
        super(enchantCost, rarityTier);
        attackAnimTime.setBaseValue(300);
        attackDamage.setBaseValue(30.0F).setUpgradedValue(1, 100F);
        knockback.setBaseValue(40);
        attackXOffset = 4;
        attackYOffset = 4;
        velocity.setBaseValue(100);
        attackCooldownTime.setBaseValue(600);
        attackRange.setBaseValue(300).setUpgradedValue(1, 500).setUpgradedValue(1, 800);
        manaCost.setBaseValue(3.5F).setUpgradedValue(1, 5.0F);
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress)
    {
        if (getAnimInverted(item))
        {
            drawOptions.swingRotationInv(attackProgress);
        }
        else
        {
            drawOptions.swingRotation(attackProgress);
        }
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "thornstafftip"));
        return tooltips;
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        ThornSpikesProj projectile = new ThornSpikesProj(level, attackerMob, attackerMob.x, attackerMob.y, (float)x, (float)y, (float)getProjectileVelocity(item, attackerMob), getAttackRange(item), getAttackDamage(item), getKnockback(item, attackerMob));
        projectile.setModifier(new ResilienceOnHitProjectileModifier(getResilienceGain(item)));
        projectile.resetUniqueID(new GameRandom(seed));
        attackerMob.addAndSendAttackerProjectile(projectile);
        consumeMana(attackerMob, item);
        return item;
    }

    protected SoundSettings getAttackSound() {
        return (new SoundSettings(GameResources.jingle)).volume(0.4F);
    }
}