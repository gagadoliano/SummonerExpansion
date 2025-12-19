package summonerexpansion.codes.events;

import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.attackHandler.GreatswordAttackHandler;
import necesse.entity.mobs.attackHandler.GreatswordChargeLevel;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import summonerexpansion.mobs.summonminions.meleeminions.*;

import java.awt.geom.Point2D;
import java.util.function.BiConsumer;

public class HorrorScytheHandler extends GreatswordAttackHandler
{
    public HorrorScytheHandler(ItemAttackerMob attackerMob, ItemAttackSlot slot, InventoryItem item, GreatswordToolItem toolItem, int seed, int startX, int startY, GreatswordChargeLevel... chargeLevels)
    {
        super(attackerMob, slot, item, toolItem, seed, startX, startY, chargeLevels);
    }

    public void onEndAttack(boolean bySelf)
    {
        super.onEndAttack(bySelf);
        Point2D.Float dir = GameMath.getAngleDir(currentAngle);
        switch (currentChargeLevel)
        {
            case 2:
                strongSummonHorrorMob(dir);
            case 1:
                summonHorrorMob(dir);
            default:
        }
    }

    private void strongSummonHorrorMob(Point2D.Float dir)
    {
        if (attackerMob.isServer())
        {
            HorrorBullMinion mob = new HorrorBullMinion();
            attackerMob.serverFollowersManager.addFollower("summonedhorrorbullminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 1, null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(mob, this.attackerMob.getLevel(), this.attackerMob.x, this.attackerMob.y);
            mob.updateDamage(toolItem.getAttackDamage(item).modFinalMultiplier(0.8f));
            mob.setEnchantment(toolItem.getEnchantment(item));
            if (!this.attackerMob.isPlayer) {
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("horrorscythe"), CheckSlotType.WEAPON);
            }
            mob.dx = dir.x * 300.0F;
            mob.dy = dir.y * 300.0F;
            attackerMob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
        }
    }

    private void summonHorrorMob(Point2D.Float dir)
    {
        if (attackerMob.isServer())
        {
            HorrorWolfMinion mob = new HorrorWolfMinion();
            attackerMob.serverFollowersManager.addFollower("summonedhorrorwolfminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 3, (BiConsumer)null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(mob, this.attackerMob.getLevel(), this.attackerMob.x, this.attackerMob.y);
            mob.updateDamage(toolItem.getAttackDamage(item).modFinalMultiplier(0.2f));
            mob.setEnchantment(toolItem.getEnchantment(item));
            if (!this.attackerMob.isPlayer) {
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("horrorscythe"), CheckSlotType.WEAPON);
            }
            mob.dx = dir.x * 300.0F;
            mob.dy = dir.y * 300.0F;
            attackerMob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
        }
    }
}
