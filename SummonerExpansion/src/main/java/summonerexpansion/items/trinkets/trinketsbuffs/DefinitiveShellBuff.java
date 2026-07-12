package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class DefinitiveShellBuff extends TrinketBuff implements BuffAbility
{
    public void init(ActiveBuff activeBuff, BuffEventSubscriber eventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.ARMOR_FLAT, 10);
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        float active = 10;
        float cooldown = 35;
        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.GUARDIAN_SHELL_COOLDOWN, player, cooldown, null), false);
        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.GUARDIAN_SHELL_ACTIVE, player, active, null), false);
        player.buffManager.forceUpdateBuffs();
        int minHeight = 0;
        int maxHeight = 40;
        int particles = 40;

        for(int i = 0; i < particles; ++i)
        {
            float height = (float)minHeight + (float)(maxHeight - minHeight) * (float)i / (float)particles;
            AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float distance = 20.0F;
            player.getLevel().entityManager.addParticle(player.x + GameMath.sin(currentAngle.get()) * distance, player.y + GameMath.cos(currentAngle.get()) * distance * 0.75F, Particle.GType.CRITICAL).color(new Color(78, 112, 31)).height(height).moves((pos, delta, lifeTime, timeAlive, lifePercent) ->
            {
                float angle = currentAngle.accumulateAndGet(delta * 150.0F / 250.0F, Float::sum);
                float distY = distance * 0.75F;
                pos.x = player.x + GameMath.sin(angle) * distance;
                pos.y = player.y + GameMath.cos(angle) * distY * 0.75F;
            }).lifeTime((int)(active * 1000.0F)).sizeFades(16, 24);
        }
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.buffManager.hasBuff(BuffRegistry.Debuffs.GUARDIAN_SHELL_COOLDOWN);
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        if (!event.wasPrevented && buff.owner.isServer())
        {
            Mob attackOwner = event.attacker != null ? event.attacker.getAttackOwner() : null;
            boolean hasOwnerInChain = event.attacker != null && event.attacker.isInAttackOwnerChain(buff.owner);
            if (attackOwner != null && !hasOwnerInChain)
            {
                float dx = (float)(attackOwner.getX() - buff.owner.getX());
                float dy = (float)(attackOwner.getY() - buff.owner.getY());
                float damage = (float)event.damage;
                damage *= buff.owner.buffManager.getModifier(BuffModifiers.MAX_SUMMONS);
                attackOwner.isServerHit(new GameDamage(damage, 0.0F), dx, dy, 50.0F, buff.owner);
            }
        }

        if (!event.wasPrevented && buff.owner.isServer())
        {
            Mob attackOwner = event.attacker != null ? event.attacker.getAttackOwner() : null;
            if (attackOwner != null)
            {
                attackOwner.addBuff(new ActiveBuff(BuffRegistry.Debuffs.SPIDER_CHARM_POISON, attackOwner, 5.0F, buff.owner), true);
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "definitiveshelltip"));
        return tooltips;
    }
}