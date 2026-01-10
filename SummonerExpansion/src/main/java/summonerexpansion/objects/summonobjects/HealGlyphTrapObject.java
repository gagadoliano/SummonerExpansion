package summonerexpansion.objects.summonobjects;

import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.GlyphObjectTrapEvent;
import necesse.level.gameObject.GlyphTrapObject;
import necesse.level.maps.Level;
import summonerexpansion.codes.events.HealGlyphTrapEvent;

public class HealGlyphTrapObject extends GlyphTrapObject
{
    public HealGlyphTrapObject()
    {
        super("glyphtrapheal", HealGlyphTrapEvent.particleHue);
    }

    protected void addLevelEvent(Level level, int x, int y)
    {
        GlyphObjectTrapEvent event = new HealGlyphTrapEvent(x, y, GameRandom.globalRandom);
        level.entityManager.events.add(event);
    }
}