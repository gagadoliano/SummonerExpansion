package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.NecroticSoulSkullPushEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.*;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.particle.Particle;

import java.util.concurrent.atomic.AtomicReference;

import static summonerexpansion.codes.registries.RegistrySummonModifiers.SENTRY_ATTACK_SPEED;

public class BattleSkullBuff extends TrinketBuff implements BuffAbility
{
    protected float cooldown = 6F;

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0.15F);
        buff.setModifier(SENTRY_ATTACK_SPEED, 0.40F);
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.NECROTIC_SOUL_SKULL_COOLDOWN, player, cooldown, null), false);
        player.getLevel().entityManager.events.add(new NecroticSoulSkullPushEvent(player));
        player.buffManager.forceUpdateBuffs();
        float maxDist = 128.0F;
        int lifeTime = 1100;
        int minHeight = 0;
        int maxHeight = 30;
        int particles = 77;

        for(int i = 0; i < particles; ++i)
        {
            float height = (float)minHeight + (float)(maxHeight - minHeight) * (float)i / (float)particles;
            AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float outDistance = GameRandom.globalRandom.getFloatBetween(60.0F, maxDist + 32.0F);
            boolean counterclockwise = GameRandom.globalRandom.nextBoolean();
            player.getLevel().entityManager.addParticle(player.x + GameRandom.globalRandom.getFloatBetween(0.0F, GameMath.sin(currentAngle.get()) * maxDist), player.y + GameRandom.globalRandom.getFloatBetween(0.0F, GameMath.cos((Float)currentAngle.get()) * maxDist * 0.75F), Particle.GType.CRITICAL).color(NecroticPoisonBuff.getNecroticParticleColor()).height(height).moves((pos, delta, cLifeTime, timeAlive, lifePercent) ->
            {
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

    public void serverTick(ActiveBuff buff)
    {
        updateModifiers(buff);
    }

    public void clientTick(ActiveBuff buff)
    {
        updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        if (!buff.owner.isAttacking)
        {
            buff.setModifier(BuffModifiers.TARGET_RANGE, -0.60F);
        }
        else
        {
            buff.setModifier(BuffModifiers.TARGET_RANGE, 0.60F);
        }
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.buffManager.hasBuff(BuffRegistry.Debuffs.NECROTIC_SOUL_SKULL_COOLDOWN);
    }
}