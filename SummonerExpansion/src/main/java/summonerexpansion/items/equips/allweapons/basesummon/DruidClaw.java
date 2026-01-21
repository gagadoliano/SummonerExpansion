package summonerexpansion.items.equips.allweapons.basesummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.SwordCleanSliceAttackEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.trails.Trail;
import necesse.entity.trails.TrailVector;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.ItemControllerInteract;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.lootItem.OneOfLootItems;
import necesse.inventory.lootTable.presets.CloseRangeWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.codes.events.DruidClawDashHandler;
import summonerexpansion.codes.registry.SummonerBuffs;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;

public class DruidClaw extends SwordToolItem implements ItemInteractAction
{
    public FloatUpgradeValue debuffDuration;
    public IntUpgradeValue maxDashStacks;
    public IntUpgradeValue dashRange;
    public GameTexture invertedAttackTexture;
    public Color slashColor;

    public DruidClaw(int enchantCost, OneOfLootItems lootTableCategory)
    {
        super(enchantCost, lootTableCategory);

        damageType = DamageTypeRegistry.SUMMON;
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        canBeUsedForRaids = false;
        resilienceGain.setBaseValue(1.5F).setUpgradedValue(1.0F, 1.5F).setUpgradedValue(10.0F, 2.0F);

        maxDashStacks = new IntUpgradeValue(3, 0.0F);
        dashRange = new IntUpgradeValue(100, 0.0F);
        debuffDuration = new FloatUpgradeValue(10f, 0.0F);
        slashColor = new Color(221, 235, 243);
    }

    public DruidClaw()
    {
        this(10, CloseRangeWeaponsLootTable.closeRangeWeapons);

        damageType = DamageTypeRegistry.SUMMON;
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        canBeUsedForRaids = false;

        attackDamage.setBaseValue(10.0F);
        attackRange.setBaseValue(100);
        knockback.setBaseValue(15);
        resilienceGain.setBaseValue(1.5F).setUpgradedValue(1.0F, 2.5F).setUpgradedValue(10.0F, 5.0F);
        attackAnimTime.setBaseValue(400);

        maxDashStacks = new IntUpgradeValue(3, 0F);
        dashRange = new IntUpgradeValue(100, 0F);
        debuffDuration = new FloatUpgradeValue(10f, 0F);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "clawweapontip", "stacks", maxDashStacks.getValue(getUpgradeTier(item))));
        return tooltips;
    }

    protected void loadAttackTexture()
    {
        super.loadAttackTexture();

        try
        {
            invertedAttackTexture = GameTexture.fromFileRaw("player/weapons/" + getStringID() + "_inverted");
        }
        catch (FileNotFoundException var2)
        {
            invertedAttackTexture = null;
        }
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress)
    {
        if (item.getGndData().getBoolean("chargeUp"))
        {
            float chargePercent = Math.min(item.getGndData().getFloat("chargePercent"), 1.0F);
            if (drawOptions.dir == 2)
            {
                drawOptions.rotation(GameMath.lerp(chargePercent, -20.0F, 0.0F));
            }
            else
            {
                drawOptions.rotation(GameMath.lerp(chargePercent, 80.0F, 100.0F));
            }
        }
        else if (item.getGndData().getBoolean("sliceDash"))
        {
            if (drawOptions.dir == 2)
            {
                drawOptions.rotation(GameMath.lerp(attackProgress, 0.0F, -170.0F));
            }
            else
            {
                drawOptions.rotation(GameMath.lerp(attackProgress, 70.0F, -100.0F));
            }
        }
        else if (item.getGndData().getBoolean("slash"))
        {
            drawOptions.rotation(getSwingRotation(item, drawOptions.dir, attackProgress) - 60.0F - (float)(drawOptions.dir * 10));
        }
        else
        {
            drawOptions.rotation(getSwingRotation(item, drawOptions.dir, attackProgress) - 10.0F - (float)(drawOptions.dir * 10));
        }
    }

    public ItemAttackDrawOptions setupItemSpriteAttackDrawOptions(ItemAttackDrawOptions options, InventoryItem item, PlayerMob player, int mobDir, float attackDirX, float attackDirY, float attackProgress, Color itemColor)
    {
        ItemAttackDrawOptions.AttackItemSprite itemSprite = options.itemSprite(getAttackSprite(item, player));
        return applySpriteOffsets(options, item, itemSprite);
    }

    protected ItemAttackDrawOptions applySpriteOffsets(ItemAttackDrawOptions options, InventoryItem item, ItemAttackDrawOptions.AttackItemSprite itemSprite)
    {
        boolean chargeUp = item.getGndData().getBoolean("chargeUp");
        boolean sliceDash = item.getGndData().getBoolean("sliceDash");
        if (chargeUp || sliceDash)
        {
            if (options.dir == 0)
            {
                itemSprite.itemRotatePoint(12, 12);
            }
            else if (options.dir == 2)
            {
                itemSprite.itemRotatePoint(14, 14);
            }
            else
            {
                itemSprite.itemRotatePoint(14, 14);
            }
        }
        if (chargeUp)
        {
            float chargePercent = Math.min(item.getGndData().getFloat("chargePercent"), 1.0F);
            if (options.dir == 0)
            {
                itemSprite.itemRotateOffsetAdd(GameMath.lerp(chargePercent, 0.0F, 10.0F));
            }
            else if (options.dir == 2)
            {
                itemSprite.itemRotateOffsetAdd(GameMath.lerp(chargePercent, 0.0F, 70.0F));
                options.addedArmRotationOffset(GameMath.lerp(chargePercent, 0.0F, 70.0F));
            }
            else
            {
                itemSprite.itemRotateOffsetAdd(GameMath.lerp(chargePercent, 0.0F, 60.0F));
            }
            return itemSprite.itemEnd();
        }
        else if (sliceDash)
        {
            if (options.dir == 0)
            {
                itemSprite.itemRotateOffsetAdd(10.0F);
            }
            else if (options.dir == 2)
            {
                itemSprite.itemRotateOffsetAdd(70.0F);
                options.addedArmRotationOffset(70.0F);
            }
            else
            {
                itemSprite.itemRotateOffsetAdd(60.0F);
            }
            return itemSprite.itemEnd();
        }
        else
        {
            if (item.getGndData().getBoolean("slash"))
            {
                itemSprite.itemRotatePoint(14, 14);
            }
            else
            {
                options.addedArmRotationOffset(-50.0F);
                if (options.dir != 0 && options.dir != 1)
                {
                    if (options.dir == 2)
                    {
                        itemSprite.itemRotatePoint(22, 0);
                        itemSprite.itemRotateOffsetAdd(20.0F);
                    }
                    else
                    {
                        itemSprite.itemRotatePoint(18, 8);
                        itemSprite.itemRotateOffsetAdd(-25.0F);
                    }
                }
                else
                {
                    itemSprite.itemRotatePoint(18, 8);
                    itemSprite.itemRotateOffsetAdd(-45.0F);
                }
            }
            return itemSprite.itemEnd();
        }
    }

    public float getSwingRotationOffset(InventoryItem item, int dir, float swingAngle)
    {
        float offset = super.getSwingRotationOffset(item, dir, swingAngle);
        if (item.getGndData().getBoolean("chargeUp"))
        {
            return offset;
        }
        else if (item.getGndData().getBoolean("sliceDash"))
        {
            return offset;
        }
        else
        {
            return item.getGndData().getBoolean("slash") ? offset : offset - 40.0F;
        }
    }

    public float getHitboxSwingAngleOffset(InventoryItem item, int dir, float swingAngle)
    {
        if (item.getGndData().getBoolean("chargeUp"))
        {
            return 0.0F;
        }
        else if (item.getGndData().getBoolean("sliceDash"))
        {
            return 0.0F;
        }
        else
        {
            return item.getGndData().getBoolean("slash") ? 0.0F : -40.0F;
        }
    }

    public boolean animDrawBehindHand(InventoryItem item)
    {
        return item.getGndData().getBoolean("chargeUp") || super.animDrawBehindHand(item);
    }

    public boolean getAnimInverted(InventoryItem item)
    {
        return item.getGndData().getBoolean("slash") || item.getGndData().getBoolean("sliceDash");
    }

    public GameSprite getAttackSprite(InventoryItem item, PlayerMob player)
    {
        return (getAnimInverted(item) || item.getGndData().getBoolean("chargeUp") || item.getGndData().getBoolean("sliceDash")) && invertedAttackTexture != null ? new GameSprite(invertedAttackTexture) : super.getAttackSprite(item, player);
    }

    public void showClawAttack(Level level, final AttackAnimMob mob, final int seed, final InventoryItem item)
    {
        level.entityManager.events.addHidden(new SwordCleanSliceAttackEvent(mob, seed, 12, null)
        {
            Trail[] trails = null;
            public void tick(float angle, float currentAttackProgress)
            {
                int attackRange = getAttackRange(item);
                Point2D.Float base = new Point2D.Float(mob.x, mob.y);
                int attackDir = mob.getDir();
                if (attackDir == 0)
                {
                    base.x += 8.0F;
                }
                else if (attackDir == 2)
                {
                    base.x -= 8.0F;
                }

                int minTrailRange = 60;
                int distancePerTrail = 5;
                boolean strictTrailAngles = item.getGndData().getBoolean("sliceDash");
                if (strictTrailAngles)
                {
                    attackRange -= 20;
                    minTrailRange -= 20;
                    angle = getSwingDirection(item, mob).apply(currentAttackProgress);
                }
                else
                {
                    angle = getSwingDirection(item, mob).apply(currentAttackProgress);
                }

                Point2D.Float dir = GameMath.getAngleDir(angle);
                int sliceDirOffset = getAnimInverted(item) ? -90 : 90;
                if (attackDir == 3)
                {
                    sliceDirOffset = -sliceDirOffset;
                }

                Point2D.Float sliceDir = GameMath.getAngleDir(angle + (float)sliceDirOffset);
                if (trails == null)
                {
                    int fadeTime = strictTrailAngles ? 1000 : 500;
                    int trailCount = Math.max(1, (attackRange - minTrailRange - 10) / distancePerTrail);
                    trails = new Trail[trailCount];

                    for(int i = 0; i < trails.length; ++i)
                    {
                        Trail trail = new Trail(getVector(currentAttackProgress, attackRange, i, distancePerTrail, base, dir, sliceDir), level, slashColor, fadeTime);
                        trails[i] = trail;
                        trail.removeOnFadeOut = false;
                        trail.sprite = new GameSprite(GameResources.chains, 7, 0, 32);
                        level.entityManager.addTrail(trail);
                    }
                }
                else
                {
                    for(int i = 0; i < trails.length; ++i)
                    {
                        if (strictTrailAngles)
                        {
                            trails[i].addPointIfSameDirection(getVector(currentAttackProgress, attackRange, i, distancePerTrail, base, dir, sliceDir), 0.2F, 20.0F, 50.0F);
                        }
                        else
                        {
                            trails[i].addPoint(getVector(currentAttackProgress, attackRange, i, distancePerTrail, base, dir, sliceDir));
                        }
                    }
                }
            }

            public TrailVector getVector(float currentAttackProgress, int attackRange, int index, int distancePerTrail, Point2D.Float base, Point2D.Float dir, Point2D.Float sliceDir)
            {
                float thickness = GameMath.lerp((float)index / (float)(trails.length - 1), 25.0F, 10.0F);
                if (currentAttackProgress < 0.33F)
                {
                    thickness *= 3.0F * currentAttackProgress;
                }
                else if (currentAttackProgress > 0.66F)
                {
                    thickness *= 3.0F * (1.0F - currentAttackProgress);
                }
                int distanceOffset = attackRange - index * distancePerTrail;
                GameRandom random = (new GameRandom(seed)).nextSeeded(index + 5);
                float xOffset = random.getFloatOffset(0.0F, 10.0F);
                float yOffset = random.getFloatOffset(0.0F, 10.0F);
                Point2D.Float edgePos = new Point2D.Float(base.x + dir.x * (float)distanceOffset + xOffset, base.y + dir.y * (float)distanceOffset + yOffset);
                return new TrailVector(edgePos.x, edgePos.y, sliceDir.x, sliceDir.y, thickness, 0.0F);
            }

            public void onDispose()
            {
                super.onDispose();
                if (trails != null)
                {
                    for(Trail trail : trails)
                    {
                        trail.removeOnFadeOut = true;
                    }
                }

            }
        });
    }

    public int getFlatItemCooldownTime(InventoryItem item)
    {
        if (!item.getGndData().getBoolean("chargeUp") && !item.getGndData().getBoolean("sliceDash"))
        {
            return item.getGndData().getBoolean("slash") ? (int)((float)getFlatAttackAnimTime(item) * getSecondSliceAttackCooldownModifier()) : super.getFlatItemCooldownTime(item);
        }
        else
        {
            return 0;
        }
    }

    public float getSecondSliceAttackCooldownModifier() {
        return 2.5F;
    }

    public boolean canItemAttackerHitTarget(ItemAttackerMob attackerMob, float fromX, float fromY, Mob target, InventoryItem item)
    {
        return itemAttackerHasLineOfSightToTarget(attackerMob, fromX, fromY, target, 5.0F);
    }

    public int getItemAttackerAttackRange(ItemAttackerMob mob, InventoryItem item)
    {
        return !mob.isPlayer && canDash(mob) ? (int)((float)dashRange.getValue(getUpgradeTier(item)) * 0.8F) : super.getItemAttackerAttackRange(mob, item);
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (!item.getGndData().getBoolean("chargeUp") && !mapContent.getBoolean("chargeUp"))
        {
            boolean isDash = item.getGndData().getBoolean("sliceDash");
            if (!isDash)
            {
                super.showAttack(level, x, y, attackerMob, attackHeight, item, animAttack, seed, mapContent);
            }
            if (level.isClient())
            {
                showClawAttack(level, attackerMob, seed, item);
            }
        }
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (!attackerMob.isPlayer && canDash(attackerMob))
        {
            float stacksPercent = (float)attackerMob.buffManager.getStacks(SummonerBuffs.SummonBuffs.CLAW_DASH_STACKS) / (float)maxDashStacks.getValue(getUpgradeTier(item));
            float animModifier = (float)GameMath.lerp(Math.min(Math.pow(stacksPercent * 2.0F, 0.5F), 1.0F), 8L, 1L);
            int animTime = (int)((float)getAttackAnimTime(item, attackerMob) * animModifier);
            mapContent.setBoolean("chargeUp", true);
            attackerMob.startAttackHandler(new DruidClawDashHandler(attackerMob, slot, item, this, animTime, new Color(190, 220, 220), seed, x, y));
            return item;
        }
        else
        {
            boolean isSlash = item.getGndData().getBoolean("slash");
            item.getGndData().setBoolean("slash", !isSlash);
            item.getGndData().setBoolean("chargeUp", false);
            item.getGndData().setBoolean("sliceDash", false);
            if (animAttack == 0)
            {
                int animTime = getAttackAnimTime(item, attackerMob);
                ToolItemMobAbilityEvent event = new ToolItemMobAbilityEvent(attackerMob, seed, item, x - attackerMob.getX(), y - attackerMob.getY() + attackHeight, animTime, animTime, isSlash ? new HashMap<>() : null);
                attackerMob.addAndSendAttackerLevelEvent(event);
            }
            return item;
        }
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        if (event.totalHits == 0)
        {
            attacker.buffManager.removeStack(SummonerBuffs.SummonBuffs.CLAW_DASH_STACKS, true, level.isServer());
        }
    }

    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return canDash(attackerMob);
    }

    public boolean canDash(ItemAttackerMob attackerMob)
    {
        return !attackerMob.isRiding() && !attackerMob.buffManager.hasBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_COOLDOWN);
    }

    public int getLevelInteractCooldownTime(InventoryItem item, ItemAttackerMob attackerMob) {
        return 0;
    }

    public boolean getConstantInteract(InventoryItem item) {
        return true;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int seed, GNDItemMap mapContent)
    {
        float stacksPercent = (float)attackerMob.buffManager.getStacks(SummonerBuffs.SummonBuffs.CLAW_DASH_STACKS) / (float)maxDashStacks.getValue(getUpgradeTier(item));
        float animModifier = (float)GameMath.lerp(Math.min(Math.pow(stacksPercent * 2.0F, 0.5F), 1.0F), 8L, 1L);
        int animTime = (int)((float)getAttackAnimTime(item, attackerMob) * animModifier);
        mapContent.setBoolean("chargeUp", true);
        attackerMob.startAttackHandler((new DruidClawDashHandler(attackerMob, slot, item, this, animTime, new Color(190, 220, 220), seed, x, y)).startFromInteract());
        return item;
    }

    public ItemControllerInteract getControllerInteract(Level level, PlayerMob player, InventoryItem item, boolean beforeObjectInteract, int interactDir, LinkedList<Rectangle> mobInteractBoxes, LinkedList<Rectangle> tileInteractBoxes)
    {
        Point2D.Float controllerAimDir = player.getControllerAimDir();
        Point levelPos = getControllerAttackLevelPos(level, controllerAimDir.x, controllerAimDir.y, player, item);
        return new ItemControllerInteract(levelPos.x, levelPos.y)
        {
            public DrawOptions getDrawOptions(GameCamera camera) {
                return null;
            }
            public void onCurrentlyFocused(GameCamera camera) {
            }
        };
    }

    protected SoundSettings getAttackSound() {
        return (new SoundSettings(GameResources.katanaSwing)).volume(1.3F);
    }
}