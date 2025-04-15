package summonerexpansion.summonothers;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.explosionEvent.ExplosionEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTextureSection;

public class SnowmanExplosionLevelEvent extends ExplosionEvent implements Attacker
{
    protected ParticleTypeSwitcher explosionTypeSwitcher;

    public SnowmanExplosionLevelEvent() {
        this(0.0F, 0.0F, 100, new GameDamage(100.0F), false, 0, (Mob)null);
    }

    public SnowmanExplosionLevelEvent(float x, float y, int range, GameDamage damage, boolean destructive, int toolTier, Mob owner)
    {
        super(x, y, range, damage, destructive, toolTier, owner);
        this.explosionTypeSwitcher = new ParticleTypeSwitcher(GType.IMPORTANT_COSMETIC, GType.COSMETIC, GType.CRITICAL);
        this.knockback = 100;
    }

    protected boolean canHitMob(Mob target)
    {
        return target.isPlayer || target instanceof HumanMob || super.canHitMob(target);
    }

    protected GameDamage getTotalObjectDamage(float targetDistance)
    {
        return super.getTotalObjectDamage(targetDistance).modDamage(10.0F);
    }

    protected void playExplosionEffects()
    {
        SoundManager.playSound(GameResources.pop, SoundEffect.effect(this.x, this.y).volume(1.5F).pitch(0.5F));
        this.level.getClient().startCameraShake(this.x, this.y, 300, 40, 1.5F, 1.5F, true);
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
        GameTextureSection snowmanSprites = GameResources.smokePuff;
        int res = snowmanSprites.getHeight();
        int sprite = GameRandom.globalRandom.nextInt(snowmanSprites.getWidth() / res);
        if (GameRandom.globalRandom.getChance(0.5F))
        {
            this.level.entityManager.addParticle(x + 4.0F, y - 10.0F, this.explosionTypeSwitcher.next()).sprite(snowmanSprites.sprite(sprite, 0, 32)).sizeFades(25, 40).movesConstant(dirX * 0.8F, dirY * 0.8F).height(10.0F).givesLight(75.0F, 0.5F).lifeTime(lifeTime);
        }
    }
}
