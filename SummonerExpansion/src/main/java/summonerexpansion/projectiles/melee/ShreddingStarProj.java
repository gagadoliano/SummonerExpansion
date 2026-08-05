package summonerexpansion.projectiles.melee;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonWalkBase;

import java.awt.*;
import java.util.List;

public class ShreddingStarProj extends FollowingProjectile
{
    private long spawnTime;

    public ShreddingStarProj() {}

    public ShreddingStarProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
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
        this.spawnTime = this.getWorldEntity().getTime();
        this.isBoomerang = true;
        this.trailOffset = 0.0F;
        this.turnSpeed = 0.2F;
        this.height = 38.0F;
        this.setWidth(38.0F);
        this.bouncing = 1;
    }

    public float getTurnSpeed(int targetX, int targetY, float delta)
    {
        return super.getTurnSpeed(targetX, targetY, delta);
    }

    public void updateTarget()
    {
        if (traveledDistance > 100.0F)
        {
            findTarget((m) ->
                    m.isHostile, 0.0F, 250.0F);
        }
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        ItemAttackerMob attackerMob = (ItemAttackerMob)getOwner();
        if (mob != null && isServer())
        {
            SummonWalkBase botMob = (SummonWalkBase) MobRegistry.getMob("haywireshredderminion", getLevel());
            if (GameRandom.globalRandom.nextInt(100) <= 10)
            {
                attackerMob.serverFollowersManager.addFollower("haywireshredderminion", botMob, FollowPosition.CIRCLE_FAR, "summonedmob", 1.0F, (p) -> 3, null, false);
                botMob.updateDamage(getDamage());
                getLevel().entityManager.addMob(botMob, mob.x, mob.y);
            }
        }
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
            list.add(new EntityDrawable(this) {public void draw(TickManager tickManager) {options.draw();}});
        }
    }
}