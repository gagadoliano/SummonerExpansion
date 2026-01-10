package summonerexpansion.codes.events;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.GlyphObjectTrapEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTexture;
import summonerexpansion.codes.registry.SummonerBuffs;
import summonerexpansion.codes.registry.SummonerTextures;

import java.awt.*;

public class HealGlyphTrapEvent extends GlyphObjectTrapEvent
{
    public static Color particleColor = new Color(27, 164, 62);
    public static float particleHue = 135.0F;

    public HealGlyphTrapEvent() {
    }

    public HealGlyphTrapEvent(int x, int y, GameRandom uniqueIDRandom)
    {
        super(x, y, uniqueIDRandom);
    }

    public GameTexture getParticleTexture()
    {
        return SummonerTextures.healGlyphParticle;
    }

    public Color getParticleColor()
    {
        return particleColor;
    }

    public float getParticleColorHue()
    {
        return particleHue;
    }

    public void playGlyphSound()
    {
        SoundManager.playSound(GameResources.glyphTrapReverseDamage, SoundEffect.effect((float)this.x, (float)this.y).pitch(GameRandom.globalRandom.getFloatBetween(0.9F, 1.1F)));
    }

    public void applyGlyphServer(Mob target)
    {
        target.buffManager.addBuff(new ActiveBuff(SummonerBuffs.SummonBuffs.HEALGLYPH, target, 15000, null), true, true);
    }
}