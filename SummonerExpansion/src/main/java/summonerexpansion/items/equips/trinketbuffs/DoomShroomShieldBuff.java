package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.*;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import summonerexpansion.codes.events.DoomShroomEvent;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class DoomShroomShieldBuff extends TrinketBuff implements BuffAbility
{
    public DoomShroomShieldBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0.10F);
        buff.setModifier(BuffModifiers.TARGET_RANGE, 0.50F);
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        float cooldown = 10.0F;
        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("doomshroomshieldcooldown"), player, cooldown, null), false);
        player.getLevel().entityManager.events.add(new DoomShroomEvent(player));
        player.buffManager.forceUpdateBuffs();
        float maxDist = 400.0F;
        int lifeTime = 1100;
        int minHeight = 0;
        int maxHeight = 30;
        int particles = 70;
        for(int i = 0; i < particles; ++i)
        {
            float height = (float)minHeight + (float)(maxHeight - minHeight) * (float)i / (float)particles;
            AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float outDistance = GameRandom.globalRandom.getFloatBetween(60.0F, maxDist + 32.0F);
            boolean counterclockwise = GameRandom.globalRandom.nextBoolean();
            player.getLevel().entityManager.addParticle(player.x + GameRandom.globalRandom.getFloatBetween(0.0F, GameMath.sin(currentAngle.get()) * maxDist), player.y + GameRandom.globalRandom.getFloatBetween(0.0F, GameMath.cos(currentAngle.get()) * maxDist * 0.75F), Particle.GType.CRITICAL).color(GameRandom.globalRandom.getOneOf(new Color(18, 18, 19), new Color(33, 33, 33), new Color(163, 39, 39), new Color(90, 33, 33))).height(height).moves((pos, delta, cLifeTime, timeAlive, lifePercent) -> {
                float angle = currentAngle.accumulateAndGet(delta * 150.0F / 250.0F, Float::sum);
                if (counterclockwise)
                {
                    angle = -angle;
                }
                float linearDown = GameMath.lerpExp(lifePercent, 0.525F, 0.0F, 1.0F);
                pos.x = player.x + outDistance * GameMath.sin(angle) * linearDown;
                pos.y = player.y + outDistance * GameMath.cos(angle) * linearDown * 0.75F;
            }).lifeTime(lifeTime).sizeFades(14, 18);
        }
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.buffManager.hasBuff(BuffRegistry.getBuff("doomshroomshieldcooldown"));
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "doomshroomshieldtip"));
        return tooltips;
    }
}