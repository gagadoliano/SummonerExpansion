package summonerexpansion.particles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.titaniumLightningGlyph;

public class TitaniumLightningGlyphParticle extends Particle
{
    public int sprite;

    public TitaniumLightningGlyphParticle(Level level, float x, float y, long lifeTime)
    {
        super(level, x, y, lifeTime);
        this.sprite = GameRandom.globalRandom.nextInt(4);
    }

    private float getChargeUpAlpha()
    {
        return (float)this.getLifeCycleTime() / 500.0F;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel(this.getTileX(), this.getTileY());
        int drawX = camera.getDrawX(this.getX());
        int drawY = camera.getDrawY(this.getY());
        long remainingLifeTime = this.getRemainingLifeTime();
        float alpha;
        if (remainingLifeTime < 100L)
        {
            alpha = Math.max(0.0F, (float)remainingLifeTime / 100.0F);
        }
        else if (this.getChargeUpAlpha() != 1.0F)
        {
            alpha = this.getChargeUpAlpha();
        }
        else
        {
            alpha = 1.0F;
        }

        DrawOptions options = titaniumLightningGlyph.initDraw().light(light).alpha(alpha).posMiddle(drawX, drawY);
        tileList.add((tm) -> options.draw());
    }

    public void clientTick()
    {
        super.clientTick();
        if (GameRandom.globalRandom.getChance(0.35F))
        {
            float rndX = this.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)6.0F);
            float rndY = this.y + 45.0F + (float)(GameRandom.globalRandom.nextGaussian() * (double)6.0F);
            this.getLevel().entityManager.addParticle(rndX, rndY, GType.IMPORTANT_COSMETIC).sprite(GameResources.particles.sprite(0, 0, 8)).color(new Color(177, 177, 177, 185)).height(46.0F).givesLight(50).movesConstant(0.0F, -4.0F).fadesAlphaTimeToCustomAlpha(250, 250, 1.0F).sizeFades(8, 12).lifeTime(1000);
        }
    }
}