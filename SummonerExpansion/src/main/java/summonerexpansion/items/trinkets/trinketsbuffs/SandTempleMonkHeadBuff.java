package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.MobHealthChangeEvent;
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

import static summonerexpansion.codes.registries.RegistryTrinkets.TrinketBuffs.MONKSTACKS;

public class SandTempleMonkHeadBuff extends TrinketBuff implements BuffAbility
{
    public SandTempleMonkHeadBuff()
    {
    }

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.SPEED, -0.15F);
        activeBuff.setModifier(BuffModifiers.SUMMONS_SPEED, -0.20F);
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON)
        {
            if (!buff.owner.buffManager.hasBuff(BuffRegistry.getBuff("sandtemplemonkheadcooldown")))
            {
                buff.owner.buffManager.addBuff(new ActiveBuff(MONKSTACKS, buff.owner, 11.0F, null), false);
            }
        }
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        float cooldown = 20.0F;
        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("sandtemplemonkheadcooldown"), player, cooldown, null), false);
        for(int bu = 0; bu < buff.owner.buffManager.getStacks(BuffRegistry.getBuff("monkstacksbuff")); ++bu)
        {
            float healthToAdd = (float)(player.getMaxHealth() - player.getHealth()) * 0.03F;
            LevelEvent healthEvent = new MobHealthChangeEvent(player, GameMath.max(1, (int)healthToAdd));
            player.getLevel().entityManager.events.add(healthEvent);
            player.buffManager.removeStack(BuffRegistry.getBuff("monkstacksbuff"), true, false);
            player.buffManager.forceUpdateBuffs();
        }
        float maxDist = 100.0F;
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
            player.getLevel().entityManager.addParticle(player.x + GameRandom.globalRandom.getFloatBetween(0.0F, GameMath.sin(currentAngle.get()) * maxDist), player.y + GameRandom.globalRandom.getFloatBetween(0.0F, GameMath.cos(currentAngle.get()) * maxDist * 0.75F), Particle.GType.CRITICAL).color(GameRandom.globalRandom.getOneOf(new Color(172, 146, 61), new Color(157, 132, 56), new Color(142, 119, 51), new Color(130, 108, 48))).height(height).moves((pos, delta, cLifeTime, timeAlive, lifePercent) -> {
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
        return !buff.owner.buffManager.hasBuff(BuffRegistry.getBuff("sandtemplemonkheadcooldown")) && buff.owner.buffManager.hasBuff(BuffRegistry.getBuff("monkstacksbuff"));
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "sandtemplemonkheadtip"));
        return tooltips;
    }
}