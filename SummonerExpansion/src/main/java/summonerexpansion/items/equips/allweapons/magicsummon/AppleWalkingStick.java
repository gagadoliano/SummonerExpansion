package summonerexpansion.items.equips.allweapons.magicsummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.level.maps.Level;
import summonerexpansion.allprojs.magicprojs.AppleWalkProj;
import summonerexpansion.codes.events.AppleWalkingStickHandler;
import summonerexpansion.codes.registry.SummonerBuffs;
import summonerexpansion.items.equips.allweapons.basesummon.BaseMagicWeapon;

import java.awt.geom.Point2D;

public class AppleWalkingStick extends BaseMagicWeapon
{
    public AppleWalkingStick(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, rarityTier);
        attackDamage.setBaseValue(15.0F).setUpgradedValue(1, 100F);
        manaCost.setBaseValue(5F).setUpgradedValue(1, 10F);
        resilienceGain.setBaseValue(0).setUpgradedValue(1, 1F);
        knockback.setBaseValue(10);
        attackCooldownTime.setBaseValue(800);
        attackAnimTime.setBaseValue(800);
        attackRange.setBaseValue(600).setUpgradedValue(1, 1000);
        velocity.setBaseValue(80).setUpgradedValue(1, 90).setUpgradedValue(10, 200);
        attackXOffset = 10;
        attackYOffset = 10;
        canBeUsedForRaids = false;
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress)
    {
        if (attackDirX < 0.0F)
        {
            drawOptions.rotation(-GameMath.getAngle(new Point2D.Float(attackDirX, attackDirY)) + 25.0F + 180.0F);
        }
        else
        {
            drawOptions.rotation(GameMath.getAngle(new Point2D.Float(attackDirX, attackDirY)) + 25.0F);
        }

    }

    public int getAttackAnimTime(InventoryItem item, ItemAttackerMob attackerMob)
    {
        return item.getGndData().getBoolean("charging") ? 2000 : super.getAttackAnimTime(item, attackerMob);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        attackerMob.startAttackHandler(new AppleWalkingStickHandler(attackerMob, slot, item, this, seed, x, y));
        if (attackerMob.isClient())
        {
            SoundManager.playSound((new SoundSettings(GameResources.swing1)).volume(0.4F).pitchVariance(0.1F), attackerMob);
        }
        return item;
    }

    public boolean shouldRunOnAttackedBuffEvent(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        return false;
    }

    public void fireProjectile(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item, int seed)
    {
        float distance = GameMath.diamondDistance(attackerMob.x, attackerMob.y, (float)x, (float)y);
        float t = 32.0F / distance;
        float projectileX = (1.0F - t) * attackerMob.x + t * (float)x;
        float projectileY = (1.0F - t) * attackerMob.y + t * (float)y;
        GameRandom random = new GameRandom(seed);
        GameRandom spreadRandom = new GameRandom(seed + 10);
        Projectile projectile = new AppleWalkProj(projectileX, projectileY, (float)x, (float)y, (float)random.getIntBetween(500, 250), 500, this.getAttackDamage(item), 0, attackerMob);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        projectile.getUniqueID(random);
        projectile.setAngle(projectile.getAngle() + spreadRandom.getFloatOffset(0.0F, 15.0F));
        attackerMob.addAndSendAttackerProjectile(projectile);
        this.consumeMana(attackerMob, item);
    }

    public boolean holdsItem(InventoryItem item, PlayerMob player)
    {
        return true;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "applesticktip"));
        return tooltips;
    }
}