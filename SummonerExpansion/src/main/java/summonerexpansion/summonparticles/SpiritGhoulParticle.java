package summonerexpansion.summonparticles;

import necesse.engine.GlobalData;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.RuneSpiritPoolEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.Renderer;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.summonregistry.SummonerTextures.SpiritGhoulPool;

public class SpiritGhoulParticle extends Particle
{
    public int sprite;
    public boolean mirror;

    public SpiritGhoulParticle(Level level, float x, float y, long lifeTime)
    {
        super(level, x, y, lifeTime);
        this.sprite = GameRandom.globalRandom.nextInt(8);
        this.mirror = GameRandom.globalRandom.nextBoolean();
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel(this.getTileX(), this.getTileY());
        int drawX = camera.getDrawX(this.getX());
        int drawY = camera.getDrawY(this.getY());
        long remainingLifeTime = this.getRemainingLifeTime();
        float alpha = 1.0F;
        if (remainingLifeTime < 500L) {
            alpha = (float)remainingLifeTime / 500.0F;
        }

        DrawOptions options = SpiritGhoulPool.initDraw().sprite(this.sprite, 0, 32).mirror(this.mirror, false).light(light).alpha(alpha).posMiddle(drawX, drawY);
        tileList.add((tm) -> {
            if (GlobalData.debugActive()) {
                Rectangle hitBox = RuneSpiritPoolEvent.hitBox;
                Renderer.drawShape(new Rectangle(this.getX() + hitBox.x, this.getY() + hitBox.y, hitBox.width, hitBox.height), camera, false, 1.0F, 0.0F, 0.0F, 1.0F);
            }

            options.draw();
        });
    }
}