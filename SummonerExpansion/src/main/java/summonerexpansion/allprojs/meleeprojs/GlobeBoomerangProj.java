package summonerexpansion.allprojs.meleeprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.ParticleOption;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
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

public class GlobeBoomerangProj extends FollowingProjectile
{
    public GlobeBoomerangProj() {
    }

    public GlobeBoomerangProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
    {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.knockback = knockback;
    }

    public void init()
    {
        super.init();
        isBoomerang = true;
        bouncing = 10;
        turnSpeed = 0.5F;
        height = 18.0F;
        setWidth(8.0F);
        spawnTime = getWorldEntity().getTime();
        trailOffset = 0.0F;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (mob != null && this.isServer())
        {
            FlyingAttackingFollowingMob mobPlanet = (FlyingAttackingFollowingMob)MobRegistry.getMob("planetmarsproj", getFirstAttackOwner().getLevel());
            float count = getFirstPlayerOwner().serverFollowersManager.getFollowerCount("summonedplanetminion");
            if (count < 3F)
            {
                switch (GameRandom.globalRandom.nextInt(4))
                {
                    case 0:
                        mobPlanet = (FlyingAttackingFollowingMob)MobRegistry.getMob("planetmarsproj", getFirstAttackOwner().getLevel());
                        break;

                    case 1:
                        mobPlanet = (FlyingAttackingFollowingMob)MobRegistry.getMob("planetneptuneproj", getFirstAttackOwner().getLevel());
                        break;

                    case 2:
                        mobPlanet = (FlyingAttackingFollowingMob)MobRegistry.getMob("planetsaturnproj", getFirstAttackOwner().getLevel());
                        break;

                    case 3:
                        mobPlanet = (FlyingAttackingFollowingMob)MobRegistry.getMob("planetvenusproj", getFirstAttackOwner().getLevel());
                        break;
                }
                getFirstPlayerOwner().serverFollowersManager.addFollower("summonedplanetminion", mobPlanet, FollowPosition.WIDE_CIRCLE_MOVEMENT, "summonedmob", 1.0F, (p) -> 3, null, false);
                mobPlanet.updateDamage(getDamage());
                mobPlanet.getLevel().entityManager.addMob(mobPlanet, mob.x, mob.y);
            }
        }
    }

    public float getTurnSpeed(int targetX, int targetY, float delta)
    {
        return super.getTurnSpeed(targetX, targetY, delta);
    }

    public Color getParticleColor() {
        return new Color(89, 197, 244);
    }

    public Trail getTrail() {
        return null;
    }

    protected int getExtraSpinningParticles() {
        return super.getExtraSpinningParticles() + 1;
    }

    protected void modifySpinningParticle(ParticleOption particle) {
        particle.lifeTime(500);
    }

    public void updateTarget() {
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y) - texture.getHeight() / 2;
            float rotate = (float)(getWorldEntity().getTime() - spawnTime);
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(rotate, texture.getWidth() / 2, texture.getHeight() / 2).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }
}