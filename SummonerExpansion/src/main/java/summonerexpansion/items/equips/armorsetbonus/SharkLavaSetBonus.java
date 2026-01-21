package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffManager;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import summonerexpansion.codes.registry.SummonerBuffs;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SharkLavaSetBonus extends SetBonusBuff
{
    public final ParticleTypeSwitcher particleTypeSwitcher;
    public IntUpgradeValue maxSummons;
    public FloatUpgradeValue buffDuration;
    public FloatUpgradeValue buffDMG;
    public List<Mob> bleedingMobs;

    public SharkLavaSetBonus()
    {
        particleTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
        buffDuration = (new FloatUpgradeValue()).setBaseValue(5.0F).setUpgradedValue(1, 10.0F).setUpgradedValue(10, 20.0F);
        buffDMG = (new FloatUpgradeValue()).setBaseValue(1.0F).setUpgradedValue(1, 5.0F).setUpgradedValue(10, 10.0F);
        maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
        buff.setMaxModifier(BuffModifiers.FIRE_DAMAGE, 0F);
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        globalTick(buff);
    }

    public void clientTick(ActiveBuff buff)
    {
        super.clientTick(buff);
        globalTick(buff);
    }

    protected void globalTick(ActiveBuff buff)
    {
        int tick = buff.owner.getLevel().tickManager().getTick();
        if (tick % 10 == 0)
        {
            List<Mob> collected = buff.owner.getLevel().entityManager.streamAreaMobsAndPlayersTileRange(buff.owner.getX(), buff.owner.getY(), 12).filter((m) -> m != buff.owner).filter((m) -> m.buffManager.getModifier(BuffModifiers.BLEED_DAMAGE_FLAT) > 0.0F).collect(Collectors.toList());
            BuffManager buffManager = buff.owner.buffManager;
            Buff speedBuff = SummonerBuffs.SummonBuffs.SHARKLAVAFRENZY;
            if (buffManager.hasBuff(speedBuff))
            {
                if (collected.size() < buffManager.getStacks(speedBuff))
                {
                    for(int i = 0; i < collected.size(); ++i)
                    {
                        buffManager.removeStack(speedBuff, true, false);
                    }
                }
                else if (!collected.isEmpty())
                {
                    buffManager.removeBuff(speedBuff, false);
                }
            }
            for(int i = 0; i < collected.size(); ++i)
            {
                buffManager.addBuff(new ActiveBuff(speedBuff, buff.owner, buffDuration.getValue(buff.getUpgradeTier()), null), false);
            }
            bleedingMobs = collected;
        }
        if (bleedingMobs != null)
        {
            for(Mob bleedingMob : bleedingMobs)
            {
                if (bleedingMob != null && !bleedingMob.removed() && bleedingMob.isVisible() && bleedingMob.isClient() && (tick % 10 == 0 || GameRandom.globalRandom.getEveryXthChance(12)))
                {
                    spawnBloodPathParticles(buff.owner, bleedingMob);
                }
            }
        }
    }

    protected void spawnBloodPathParticles(Mob ownerMob, Mob targetMob)
    {
        int particleLifetime = (int) GameMath.limit(500.0F + ownerMob.getDistance(targetMob), 500.0F, 1100.0F);
        ownerMob.getLevel().entityManager.addParticle(targetMob.x, targetMob.y, particleTypeSwitcher.next()).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.getIntBetween(0, 4), 0, 12)).color(new Color(168, 16, 16, 116)).height(0.0F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
            pos.x = GameMath.lerp(lifePercent, targetMob.x, ownerMob.x);
            pos.y = GameMath.lerp(lifePercent, targetMob.y, ownerMob.y);
        }).size((options, lifeTime, timeAlive, lifePercent) ->
        {
            int size = (int)((1.0F - lifePercent) * 16.0F);
            options.size(size, size);
        }).lifeTime(particleLifetime);
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON)
        {
            ActiveBuff bleedDebuff = new ActiveBuff(SummonerBuffs.SummonerDebuffs.SHARKLAVABLEED, event.target, buffDuration.getValue(buff.getUpgradeTier()), buff.owner);
            bleedDebuff.getGndData().setFloat("damagepersec", buffDMG.getValue(buff.getUpgradeTier()));
            event.target.buffManager.addBuff(bleedDebuff, false);
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        float durationDebuff = buffDuration.getValue(ab.getUpgradeTier());
        tooltips.add(Localization.translate("itemtooltip", "sharklavasettip", "value", (int)((buffDMG.getValue(ab.getUpgradeTier()) * buffDuration.getValue(ab.getUpgradeTier())) * 2), "duration", (int)durationDebuff));
        tooltips.add(Localization.translate("itemtooltip", "sharklavasettip2"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}