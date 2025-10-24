package summonerexpansion.summonprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ProjectileHitStuckParticle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.ThemeColorRegistry;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class GolemRubyProj extends Projectile
{
    public GolemRubyProj() {}

    public GolemRubyProj(float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner)
    {
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDistance(distance);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setOwner(owner);
    }

    public GolemRubyProj(Level level, float x, float y, float angle, float speed, GameDamage damage, Mob owner)
    {
        this.x = x;
        this.y = y;
        this.setLevel(level);
        this.setAngle(angle);
        this.setDamage(damage);
        this.setOwner(owner);
        this.setDistance(500);
        this.speed = speed;
    }

    public void init()
    {
        super.init();
        this.isSolid = false;
        this.setWidth(10.0F);
    }

    protected Color getWallHitColor() {
        return ThemeColorRegistry.RUBY.getRandomColor();
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (this.isClient() && this.bounced == this.getTotalBouncing())
        {
            this.getLevel().entityManager.addParticle(new ProjectileHitStuckParticle(mob, this, x, y, (float) GameRandom.globalRandom.getIntBetween(10, 20), 5000L)
            {
                public void addDrawables(Mob target, float x, float y, float angle, List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
                    GameLight light = level.getLightLevel(this);
                    int drawX = camera.getDrawX(x) - 2;
                    int drawY = camera.getDrawY(y - GolemRubyProj.this.height) - 2;
                    float alpha = 1.0F;
                    long lifeCycleTime = this.getLifeCycleTime();
                    int fadeTime = 250;
                    if (lifeCycleTime >= this.lifeTime - (long)fadeTime)
                    {
                        alpha = Math.abs((float)(lifeCycleTime - (this.lifeTime - (long)fadeTime)) / (float)fadeTime - 1.0F);
                    }

                    final TextureDrawOptions options = GolemRubyProj.this.texture.initDraw().light(light).rotate(GolemRubyProj.this.getAngle(), 2, 2).alpha(alpha).pos(drawX, drawY);
                    EntityDrawable drawable = new EntityDrawable(this)
                    {
                        public void draw(TickManager tickManager) {
                            options.draw();
                        }
                    };
                    if (target != null)
                    {
                        topList.add(drawable);
                    }
                    else
                    {
                        list.add(drawable);
                    }
                }
            }, Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {public void draw(TickManager tickManager) {
                    options.draw();
                }});
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), 0);
        }
    }
}
