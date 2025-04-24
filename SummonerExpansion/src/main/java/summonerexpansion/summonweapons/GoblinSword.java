package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.NecroticGreatswordAttackHandler;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemControllerInteract;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.level.maps.Level;
import summonerexpansion.summonminions.GoblinChestMinion;
import summonerexpansion.summonminions.GoblinHeadMinion;
import summonerexpansion.summonminions.GoblinLegMinion;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public class GoblinSword extends SwordToolItem implements ItemInteractAction
{
    public int goblinCharge;

    public GoblinSword()
    {
        super(400);
        rarity = Rarity.UNCOMMON;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(20.0F).setUpgradedValue(1.0F, 50.0F);
        attackAnimTime.setBaseValue(300);
        resilienceGain.setBaseValue(0F);
        attackRange.setBaseValue(60);
        knockback.setBaseValue(75);
        canBeUsedForRaids = false;
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (goblinCharge == 99)
        {
            int particleCount = 25;
            GameRandom random = GameRandom.globalRandom;
            ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
            float anglePerParticle = 360.0F / (float)particleCount;
            for(int i = 0; i < particleCount; ++i)
            {
                int angle = (int)((float)i * anglePerParticle + random.nextFloat() * anglePerParticle);
                float dx = (float)Math.sin(Math.toRadians(angle)) * 50.0F;
                float dy = (float)Math.cos(Math.toRadians(angle)) * 50.0F;
                attackerMob.getLevel().entityManager.addParticle(attackerMob, typeSwitcher.next()).sprite(GameResources.starParticles.sprite(random.nextInt(4), 0, 22)).sizeFades(22, 44).movesFriction(dx * 2.0F, dy * 2.0F, 0.8F).color(new Color(168, 184, 170)).givesLight(247.0F, 0.3F).heightMoves(0.0F, 30.0F).lifeTime(1500);
            }
            goblinCharge++;
        }
        return super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        if (attacker.isServer())
        {
            if (goblinCharge < 99)
            {
                goblinCharge++;
            }
        }
    }

    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return !attackerMob.buffManager.hasBuff(BuffRegistry.getBuff("goblincooldowndebuff")) && goblinCharge >= 100;
    }

    public float getItemCooldownPercent(InventoryItem item, PlayerMob perspective)
    {
        return perspective.buffManager.getBuffDurationLeftSeconds(BuffRegistry.getBuff("goblincooldowndebuff")) / 12.0F;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, final ItemAttackerMob attackerMob, int attackHeight, final InventoryItem item, ItemAttackSlot slot, final int seed, GNDItemMap mapContent)
    {
        attackerMob.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("goblincooldowndebuff"), attackerMob, 12.0F, (Attacker)null), false);
        if (goblinCharge >= 100 && attackerMob.isServer())
        {
            GoblinHeadMinion mob1 = new GoblinHeadMinion();
            attackerMob.serverFollowersManager.addFollower("goblinheadminion", mob1, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 1, null, false);
            mob1.updateDamage(this.getAttackDamage(item));
            mob1.setEnchantment(this.getEnchantment(item));
            attackerMob.getLevel().entityManager.addMob(mob1, attackerMob.x, attackerMob.y - 50);

            GoblinChestMinion mob2 = new GoblinChestMinion();
            attackerMob.serverFollowersManager.addFollower("goblinchestminion", mob2, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 1, null, false);
            mob2.updateDamage(this.getAttackDamage(item));
            mob2.setEnchantment(this.getEnchantment(item));
            attackerMob.getLevel().entityManager.addMob(mob2, attackerMob.x - 50, attackerMob.y + 50);

            GoblinLegMinion mob3 = new GoblinLegMinion();
            attackerMob.serverFollowersManager.addFollower("goblinlegminion", mob3, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 1, null, false);
            mob3.updateDamage(this.getAttackDamage(item));
            mob3.setEnchantment(this.getEnchantment(item));
            attackerMob.getLevel().entityManager.addMob(mob3, attackerMob.x + 50, attackerMob.y + 50);

            goblinCharge = 0;
        }
        return item;
    }

    public ItemControllerInteract getControllerInteract(Level level, PlayerMob player, InventoryItem item, boolean beforeObjectInteract, int interactDir, LinkedList<Rectangle> mobInteractBoxes, LinkedList<Rectangle> tileInteractBoxes)
    {
        Point2D.Float controllerAimDir = player.getControllerAimDir();
        Point levelPos = this.getControllerAttackLevelPos(level, controllerAimDir.x, controllerAimDir.y, player, item);
        return new ItemControllerInteract(levelPos.x, levelPos.y)
        {
            public DrawOptions getDrawOptions(GameCamera camera) {
                return null;
            }
            public void onCurrentlyFocused(GameCamera camera) {}
        };
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "goblinswordtip"));
        tooltips.add(Localization.translate("itemtooltip", "chargetip", "amount", goblinCharge));
        return tooltips;
    }
}