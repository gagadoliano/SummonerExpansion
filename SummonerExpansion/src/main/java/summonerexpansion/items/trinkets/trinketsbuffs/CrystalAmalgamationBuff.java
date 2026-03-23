package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.maps.Level;
import summonerexpansion.items.weapons.base.BaseDruidClaw;

import java.awt.geom.Point2D;

public class CrystalAmalgamationBuff extends TrinketBuff
{
    public CrystalAmalgamationBuff() {}

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber) {
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, GNDItemMap attackMap)
    {
        super.onItemAttacked(buff, targetX, targetY, attackerMob, attackHeight, item, slot, animAttack, attackMap);
        Level level = buff.owner.getLevel();
        if (level.isServer() && item.item instanceof BaseDruidClaw)
        {
            ToolItem toolItem = (ToolItem)item.item;
            if (toolItem.getDamageType(item) == DamageTypeRegistry.SUMMON)
            {
                String shotTimeKey = "crystalshottime";
                GameDamage finalDamage = ((ToolItem)item.item).getAttackDamage(item).modDamage(0.10F);
                long shotTime = buff.getGndData().getLong(shotTimeKey);
                float totalModifier = DamageTypeRegistry.SUMMON.calculateTotalAttackSpeedModifier(attackerMob);
                int cooldown = Math.round(750.0F * (1.0F / totalModifier));
                if (shotTime + (long)cooldown < level.getWorldEntity().getTime())
                {
                    buff.getGndData().setLong(shotTimeKey, level.getWorldEntity().getTime());
                    GameRandom random = GameRandom.globalRandom;
                    Point2D.Float dir = GameMath.normalize(attackerMob.x - (float)targetX, attackerMob.y - (float)targetY);
                    int offsetDistance = random.getIntBetween(30, 50);
                    Point2D.Float offset = new Point2D.Float(dir.x * (float)offsetDistance, dir.y * (float)offsetDistance);
                    float velocity = 150.0F * attackerMob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                    if (random.getChance(0.5F))
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystaltrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);
                        level.entityManager.projectiles.add(projectile);
                    }
                    else if (random.getChance(0.5F))
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystalamethysttrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);
                        level.entityManager.projectiles.add(projectile);
                    }
                    else
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystalsapphiretrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                    if (random.getChance(0.5F))
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystalemeraldtrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                    else if (random.getChance(0.5F))
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystaltopaztrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                    else
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystalrubytrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                    if (random.getChance(0.5F))
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystalamethysttrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                    else if (random.getChance(0.5F))
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystalsapphiretrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                    else
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystalemeraldtrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                    if (random.getChance(0.5F))
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystaltopaztrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                    else if (random.getChance(0.5F))
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystalrubytrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                    else
                    {
                        offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-60, 60), dir);
                        Projectile projectile = ProjectileRegistry.getProjectile("crystalamethysttrinketproj", level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float)targetX, (float)targetY, velocity * random.getFloatBetween(0.5f, 1.5f), 500, finalDamage, buff.owner);level.entityManager.projectiles.add(projectile);
                    }
                }
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "crystalamalgamationtip"));
        return tooltips;
    }
}