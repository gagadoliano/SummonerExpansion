package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.FrostSentryProjectile;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.maps.Level;
import summonerexpansion.items.equips.allweapons.basesummon.DruidClaw;

public class FrostSentryCoreBuff extends TrinketBuff
{
    public FrostSentryCoreBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {

    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        if (!event.wasPrevented && buff.owner.isServer())
        {
            if (GameRandom.globalRandom.getChance(0.20F) && !event.attacker.getAttackOwner().buffManager.hasBuff(BuffRegistry.FROZEN_MOB) && !event.attacker.getAttackOwner().isBoss())
            {
                ActiveBuff freezeBuff = new ActiveBuff(BuffRegistry.FROZEN_MOB, event.attacker.getAttackOwner(), 3.0F, null);
                event.attacker.getAttackOwner().buffManager.addBuff(freezeBuff, true);
            }
        }
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, GNDItemMap attackMap)
    {
        super.onItemAttacked(buff, targetX, targetY, attackerMob, attackHeight, item, slot, animAttack, attackMap);
        Level level = buff.owner.getLevel();
        if (level.isServer() && item.item instanceof DruidClaw)
        {
            ToolItem toolItem = (ToolItem)item.item;
            if (toolItem.getDamageType(item) == DamageTypeRegistry.SUMMON)
            {
                String shotTimeKey = "frostshottime";
                GameDamage finalDamage = ((ToolItem)item.item).getAttackDamage(item).modDamage(0.20F);
                long shotTime = buff.getGndData().getLong(shotTimeKey);
                float totalModifier = DamageTypeRegistry.SUMMON.calculateTotalAttackSpeedModifier(attackerMob);
                int cooldown = Math.round(750.0F * (1.0F / totalModifier));
                if (shotTime + (long)cooldown < level.getWorldEntity().getTime())
                {
                    buff.getGndData().setLong(shotTimeKey, level.getWorldEntity().getTime());
                    GameRandom random = GameRandom.globalRandom;
                    float velocity = 50.0F * attackerMob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                    if (random.getChance(0.50F))
                    {
                        FrostSentryProjectile projectile = new FrostSentryProjectile(level, attackerMob, attackerMob.x, attackerMob.y, targetX, targetY, velocity, 200, finalDamage, 0);
                        level.entityManager.projectiles.add(projectile);
                    }
                }
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "frostsentrycoretip"));
        return tooltips;
    }
}