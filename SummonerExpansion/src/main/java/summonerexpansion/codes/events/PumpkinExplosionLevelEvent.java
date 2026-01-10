package summonerexpansion.codes.events;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.explosionEvent.ExplosionEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTextureSection;

import static summonerexpansion.codes.registry.SummonerTextures.pumpkinExplosionParticles;

public class PumpkinExplosionLevelEvent extends ExplosionEvent
{
    protected ParticleTypeSwitcher explosionTypeSwitcher;

    public PumpkinExplosionLevelEvent() {
        this(0.0F, 0.0F, 100, new GameDamage(100.0F), null);
    }

    public PumpkinExplosionLevelEvent(float x, float y, int range, GameDamage damage, Mob owner)
    {
        super(x, y, range, damage, false, 0, owner);
        explosionTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC, Particle.GType.CRITICAL);
        knockback = 50;
    }

    protected boolean canHitMob(Mob target)
    {
        return !target.isPlayer && (target.isHostile || super.canHitMob(target));
    }

    protected void playExplosionEffects()
    {
        SoundManager.playSound(GameResources.pop, SoundEffect.effect(this.x, this.y).volume(1.5F).pitch(0.5F));
    }

    public float getParticleCount(float currentRange, float lastRange)
    {
        return super.getParticleCount(currentRange, lastRange) * 0.8F;
    }

    protected float getDistanceMod(float targetDistance) {
        return 1.0F;
    }

    public void spawnExplosionParticle(float x, float y, float dirX, float dirY, int lifeTime, float range)
    {
        GameTextureSection explosionSprites = pumpkinExplosionParticles;
        int res = explosionSprites.getHeight();
        int sprite = GameRandom.globalRandom.nextInt(explosionSprites.getWidth() / res);
        if (GameRandom.globalRandom.getChance(0.5F))
        {
            level.entityManager.addParticle(x + 4.0F, y - 10.0F, explosionTypeSwitcher.next()).sprite(explosionSprites.sprite(sprite, 0, 32)).sizeFades(25, 40).movesConstant(dirX * 0.8F, dirY * 0.8F).height(10.0F).givesLight(75.0F, 0.5F).lifeTime(lifeTime);
        }
    }
}
