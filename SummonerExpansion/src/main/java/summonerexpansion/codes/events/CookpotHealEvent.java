package summonerexpansion.codes.events;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.levelEvent.explosionEvent.splashEvent.SplashEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.MobHealthChangeEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import necesse.gfx.ThemeColorRegistry;

import java.awt.*;
import java.util.stream.Stream;

public class CookpotHealEvent extends SplashEvent
{
    public CookpotHealEvent() {
        this(0.0F, 0.0F, 96, new GameDamage(0.0F), 0.0F, null);
    }

    public CookpotHealEvent(float x, float y, int range, GameDamage damage, float toolTier, Mob owner)
    {
        super(x, y, range, damage, false, toolTier, owner);
        this.destroysGrass = false;
    }

    protected boolean canHitMob(Mob target) {
        return target.canBeHit(this);
    }

    protected Stream<Mob> streamTargets()
    {
        return Stream.concat(this.level.entityManager.mobs.getInRegionByTileRange((int)this.x / 32, (int)this.y / 32, this.range / 32 + 2).stream(), GameUtils.streamServerClients(this.level).map((c) -> c.playerMob));
    }

    protected void onMobWasHit(Mob target, float distance)
    {
        int heal;
        if (!target.isHostile)
        {
            heal = (int)((float)target.getMaxHealth() * 0.01F);
            if (heal < 1)
            {
                heal = 1;
            }
            LevelEvent event = new MobHealthChangeEvent(target, heal);
            this.level.entityManager.events.add(event);
        }
    }

    protected void playExplosionEffects()
    {
        SoundManager.playSound(GameResources.splash, SoundEffect.effect(this.x, this.y).falloffDistance(1200).volume(0.1F).pitch(GameRandom.globalRandom.getFloatBetween(1.15F, 1.25F)));
    }

    protected Color getInnerSplashColor() {
        return ThemeColorRegistry.HEAL.getRandomColor();
    }

    protected Color getOuterSplashColor() {
        return this.getInnerSplashColor().brighter().brighter().brighter();
    }
}