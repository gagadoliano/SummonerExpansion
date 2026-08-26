package summonerexpansion.codes.events;

import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.explosionEvent.splashEvent.BuffSplashEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import summonerexpansion.buffs.debuffs.SwampFlaskDebuff;

import java.awt.*;

import static summonerexpansion.codes.registries.RegistryDebuffs.WeaponDebuffs.SWAMPFLASKPOISON;

public class SwampSlimeFlaskExplosionEvent extends BuffSplashEvent
{
    public static float poisonDuration = 10F;

    public SwampSlimeFlaskExplosionEvent() {
        this(0.0F, 0.0F, 96, new GameDamage(0.0F), 0.0F, null);
    }

    public SwampSlimeFlaskExplosionEvent(float x, float y, int range, GameDamage damage, float toolTier, Mob owner)
    {
        super(x, y, range, damage, toolTier, owner);
        this.knockback = 50;
    }

    protected float getDistanceMod(float targetDistance) {
        return 1.0F;
    }

    protected ActiveBuff getBuff(Mob buffOwner)
    {
        float pDamage = this.damage.getBuffedDamage(buffOwner);
        ActiveBuff poisonDebuff = new ActiveBuff(SWAMPFLASKPOISON, buffOwner, poisonDuration, this);
        poisonDebuff.getGndData().setFloat("damagepersec", pDamage / 20.0F * poisonDuration);
        return poisonDebuff;
    }

    protected Color getInnerSplashColor()
    {
        return GameRandom.globalRandom.getOneOfWeighted(Color.class, 5, new Color(150, 154, 38), 4, new Color(97, 115, 8), 1, new Color(47, 79, 8));
    }

    protected Color getOuterSplashColor() {
        return SwampFlaskDebuff.getSwampFlaskParticleColor();
    }
}