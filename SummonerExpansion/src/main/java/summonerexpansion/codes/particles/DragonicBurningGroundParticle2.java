package summonerexpansion.codes.particles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryParticlesTextures.groundFireParticles2;

public class DragonicBurningGroundParticle2 extends Particle
{
    private int particleBuffer = 0;
    private final int randomAnimOffset;

    public DragonicBurningGroundParticle2(Level level, float x, float y, long lifeTime)
    {
        super(level, x, y, lifeTime);
        this.randomAnimOffset = 100 * GameRandom.globalRandom.getIntBetween(0, 6);
    }

    public void clientTick()
    {
        super.clientTick();
        ++this.particleBuffer;
        if (this.particleBuffer > 10)
        {
            this.drawParticles();
            this.particleBuffer -= 10;
        }
    }

    public void despawnNow()
    {
        if (this.getRemainingLifeTime() > 500L)
        {
            this.lifeTime = 500L;
            this.spawnTime = this.getWorldEntity().getLocalTime();
        }
    }

    private void drawParticles()
    {
        this.getLevel().entityManager.addParticle(this.x + GameRandom.globalRandom.floatGaussian() * 12.0F, this.y + GameRandom.globalRandom.floatGaussian() * 8.0F, GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.getIntBetween(0, 4), 0, 12)).color(new Color(18, 71, 182)).heightMoves(0.0F, 60.0F).givesLight(30.0F, 1.0F);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        int drawX = camera.getDrawX(this.getX()) - 16;
        int drawY = camera.getDrawY(this.getY()) - 16;
        long remainingLifeTime = this.getRemainingLifeTime();
        float alpha = GameMath.limit((float)remainingLifeTime / 500.0F, 0.0F, 1.0F);
        int anim = GameUtils.getAnim(this.getWorldEntity().getTime() + (long)this.randomAnimOffset, 6, 400);
        DrawOptions options = groundFireParticles2.initDraw().sprite(anim, 0, 32).pos(drawX, drawY).alpha(alpha);
        tileList.add((tm) -> options.draw());
    }
}