package summonerexpansion.summonparticles;

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

import java.util.List;

import static summonerexpansion.summonothers.SummonerTextures.MosquitoBowVisual;

public class MosquitoBowParticle extends Particle
{
    public int sprite;

    public MosquitoBowParticle(Level level, float x, float y, long lifeTime)
    {
        super(level, x, y, lifeTime);
        this.sprite = GameRandom.globalRandom.nextInt(4);
    }

    public void despawnNow()
    {
        if (this.getRemainingLifeTime() > 500L)
        {
            this.lifeTime = 500L;
            this.spawnTime = this.getWorldEntity().getLocalTime();
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel(this.getX() / 32, this.getY() / 32);
        int drawX = camera.getDrawX(this.getX());
        int drawY = camera.getDrawY(this.getY());
        long remainingLifeTime = this.getRemainingLifeTime();
        float alpha = 1.0F;
        if (remainingLifeTime < 500L)
        {
            alpha = Math.max(0.0F, (float)remainingLifeTime / 500.0F);
        }
        DrawOptions options = MosquitoBowVisual.initDraw().light(light).alpha(alpha).posMiddle(drawX, drawY);
        tileList.add((tm) -> {
            options.draw();
        });
    }
}
