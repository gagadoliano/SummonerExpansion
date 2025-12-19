package summonerexpansion.items.equips.allweapons.meleesummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.GlaiveShowAttackEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.toolItem.glaiveToolItem.GlaiveToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.mobs.summonminions.meleeminions.*;

import java.awt.*;
import java.awt.geom.Point2D;

public class HorrorGlaive extends GlaiveToolItem implements ItemInteractAction
{
    public HorrorGlaive(int enchantCost, Item.Rarity rarityTier)
    {
        super(enchantCost, SummonWeaponsLootTable.summonWeapons);
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        rarity = rarityTier;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(35.0F).setUpgradedValue(1.0F, 75.0F);
        attackAnimTime.setBaseValue(600);
        resilienceGain.setBaseValue(0.5F).setUpgradedValue(1, 1.5F).setUpgradedValue(1, 5.0F);
        attackRange.setBaseValue(200);
        knockback.setBaseValue(150);
        attackXOffset = 74;
        attackYOffset = 74;
        width = 20.0F;
        canBeUsedForRaids = true;
        tierTwoEssencesUpgradeRequirement = "purehorror";
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        super.showAttack(level, x, y, attackerMob, attackHeight, item, animAttack, seed, mapContent);
        if (level.isClient())
        {
            level.entityManager.addLevelEventHidden(new GlaiveShowAttackEvent(attackerMob, x, y, seed, 10.0F) {
                public void tick(float angle)
                {
                    GameRandom gameRandom = new GameRandom();
                    float colorModifier = gameRandom.getFloatBetween(0.0F, 1.0F);
                    Color randomColor = HorrorGlaive.this.getParticleColor(colorModifier);
                    Point2D.Float angleDir = this.getAngleDir(angle);
                    this.level.entityManager.addParticle(this.attackMob.x + angleDir.x * 85.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y + angleDir.y * 85.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), Particle.GType.COSMETIC).sprite(GameResources.bubbleParticle.sprite(0, 0, 12)).color(randomColor).movesConstant(angleDir.x * 40.0F, angleDir.y * 40.0F).lifeTime(400);
                    this.level.entityManager.addParticle(this.attackMob.x - angleDir.x * 85.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y - angleDir.y * 85.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), Particle.GType.COSMETIC).sprite(GameResources.bubbleParticle.sprite(0, 0, 12)).color(randomColor).movesConstant(angleDir.x * -40.0F, angleDir.y * -40.0F).lifeTime(400);
                }
            });
        }
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (attackerMob.buffManager.getStacks(BuffRegistry.getBuff("horrorglaivestack")) >= 100)
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
                attackerMob.getLevel().entityManager.addParticle(attackerMob, typeSwitcher.next()).sprite(GameResources.magicSparkParticles.sprite(random.nextInt(4), 0, 22)).sizeFades(22, 44).movesFriction(dx * 2.0F, dy * 2.0F, 0.8F).color(new Color(98, 0, 0)).givesLight(247.0F, 0.3F).heightMoves(0.0F, 30.0F).lifeTime(1500);
            }
        }
        return super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        if (attacker.isServer())
        {
            ActiveBuff ab = new ActiveBuff(BuffRegistry.getBuff("horrorglaivestack"), attacker, 30.0F, attacker);
            attacker.addBuff(ab, true);
        }
    }

    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return !attackerMob.buffManager.hasBuff(BuffRegistry.getBuff("horrorglaivecooldowndebuff")) && attackerMob.buffManager.getStacks(BuffRegistry.getBuff("horrorglaivestack")) >= 100;
    }

    public float getItemCooldownPercent(InventoryItem item, PlayerMob perspective)
    {
        return perspective.buffManager.getBuffDurationLeftSeconds(BuffRegistry.getBuff("horrorglaivecooldowndebuff")) / 12.0F;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, final ItemAttackerMob attackerMob, int attackHeight, final InventoryItem item, ItemAttackSlot slot, final int seed, GNDItemMap mapContent)
    {
        attackerMob.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("horrorglaivecooldowndebuff"), attackerMob, 12.0F, null), false);
        if (attackerMob.isServer() && attackerMob.buffManager.getStacks(BuffRegistry.getBuff("horrorglaivestack")) >= 100)
        {
            HorrorSentry mob1 = new HorrorSentry();
            attackerMob.serverFollowersManager.addFollower("summonedhorrorspikesentry", mob1, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 5, null, false);
            mob1.updateDamage(this.getAttackDamage(item));
            mob1.setEnchantment(this.getEnchantment(item));
            attackerMob.getLevel().entityManager.addMob(mob1, attackerMob.x, attackerMob.y);

            attackerMob.buffManager.removeBuff("horrorglaivestack", true);
        }
        return item;
    }

    private Color getParticleColor(float modifier)
    {
        return new Color((int)(10.0F * (1.0F + 1.8F * modifier)), (int)(10.0F * (1.0F + 0.3F * modifier)), (int)(10.0F * (1.0F + 0.2F * modifier)));
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "horrorglaivetip"));
        return tooltips;
    }
}