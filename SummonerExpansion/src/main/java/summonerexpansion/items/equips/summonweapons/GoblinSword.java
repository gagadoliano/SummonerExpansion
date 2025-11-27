package summonerexpansion.items.equips.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.ItemControllerInteract;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.mobs.summonminions.meleeminions.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class GoblinSword extends SwordToolItem implements ItemInteractAction
{
    public GoblinSword()
    {
        super(400, SummonWeaponsLootTable.summonWeapons);
        keyWords.add("summon");
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        rarity = Rarity.UNCOMMON;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(20.0F).setUpgradedValue(1, 100.0F);
        attackAnimTime.setBaseValue(300);
        resilienceGain.setBaseValue(0F).setUpgradedValue(1, 0.1F).setUpgradedValue(10, 0.2F);
        attackRange.setBaseValue(60);
        knockback.setBaseValue(75);
        canBeUsedForRaids = true;
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker)
    {
        super.hitMob(item, event, level, target, attacker);
        if (attacker.isServer())
        {
            ActiveBuff ab = new ActiveBuff(BuffRegistry.getBuff("goblinswordstack"), attacker, 30.0F, attacker);
            attacker.addBuff(ab, true);
        }
    }

    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return !attackerMob.buffManager.hasBuff(BuffRegistry.getBuff("goblincooldowndebuff")) && attackerMob.buffManager.getStacks(BuffRegistry.getBuff("goblinswordstack")) >= 50;
    }

    public float getItemCooldownPercent(InventoryItem item, PlayerMob perspective)
    {
        return perspective.buffManager.getBuffDurationLeftSeconds(BuffRegistry.getBuff("goblincooldowndebuff")) / 12.0F;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, final ItemAttackerMob attackerMob, int attackHeight, final InventoryItem item, ItemAttackSlot slot, final int seed, GNDItemMap mapContent)
    {
        attackerMob.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("goblincooldowndebuff"), attackerMob, 60.0F, null), false);
        if (attackerMob.isServer() && attackerMob.buffManager.getStacks(BuffRegistry.getBuff("goblinswordstack")) >= 50)
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

            attackerMob.buffManager.removeBuff("goblinswordstack", true);
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
        return tooltips;
    }
}