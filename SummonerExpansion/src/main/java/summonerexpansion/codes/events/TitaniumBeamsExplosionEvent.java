package summonerexpansion.codes.events;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.explosionEvent.ExplosionEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;

import java.awt.*;

import static summonerexpansion.codes.registries.RegistryArmors.registerArmorSets.TITANIUMMAGICSETBUFF;

public class TitaniumBeamsExplosionEvent extends ExplosionEvent implements Attacker
{
    protected ParticleTypeSwitcher explosionTypeSwitcher;

    public TitaniumBeamsExplosionEvent()
    {
        this(0.0F, 0.0F, 100, new GameDamage(100.0F), false, 0.0F, null);
    }

    public TitaniumBeamsExplosionEvent(float x, float y, int range, GameDamage damage, boolean destructive, float toolTier, Mob owner)
    {
        super(x, y, range, damage, destructive, toolTier, owner);
        this.explosionTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC, Particle.GType.CRITICAL);
        this.knockback = 100;
    }

    protected GameDamage getTotalObjectDamage(float targetDistance)
    {
        return super.getTotalObjectDamage(targetDistance).modDamage(10.0F);
    }

    protected void playExplosionEffects()
    {
        SoundManager.playSound(GameResources.magicExplosion, SoundEffect.effect(this.x, this.y).volume(0.4F).pitch(1.0F));
        this.level.getClient().startCameraShake(this.x, this.y, 300, 40, 2.0F, 2.0F, true);
    }

    public float getParticleCount(float currentRange, float lastRange)
    {
        return super.getParticleCount(currentRange, lastRange);
    }

    protected float getDistanceMod(float targetDistance) {
        return 1.0F;
    }

    protected boolean canHitMob(Mob target) {
        return super.canHitMob(target) && target != this.ownerMob;
    }

    protected void onMobWasHit(Mob mob, float distance)
    {
        super.onMobWasHit(mob, distance);
        if (this.ownerMob != null)
        {
            this.ownerMob.buffManager.addBuff(new ActiveBuff(TITANIUMMAGICSETBUFF, this.ownerMob, 10F, null), true);
        }
    }

    public void spawnExplosionParticle(float x, float y, float dirX, float dirY, int lifeTime, float range)
    {
        if (GameRandom.globalRandom.getChance(0.1F))
        {
            this.level.entityManager.addParticle(x + 4.0F, y - 10.0F, this.explosionTypeSwitcher.next()).sprite(GameResources.particles.sprite(0, 0, 8)).sizeFades(8, 14).movesConstant(dirX * 0.2F, dirY * 0.2F).height(10.0F).color(new Color(179, 179, 179, 255)).givesLight(166.0F, 0.7F).lifeTime(2000);
        }
    }
}