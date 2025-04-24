package summonerexpansion.summonprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

import java.util.List;
import java.util.function.BiConsumer;

public class WormProj extends FollowingProjectile
{
    private long spawnTime;

    public WormProj() {}

    public WormProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
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
        this.turnSpeed = 0.5F;
        this.height = 18.0F;
        this.setWidth(8.0F);
        this.bouncing = 3;
    }

    public float getTurnSpeed(int targetX, int targetY, float delta)
    {
        return super.getTurnSpeed(targetX, targetY, delta);
    }

    public void updateTarget()
    {
        if (this.traveledDistance > 50.0F)
        {
            this.findTarget((m) ->
                    m.isHostile, 0.0F, 250.0F);
        }
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (mob != null && this.isServer())
        {
            AttackingFollowingMob mobFish = (AttackingFollowingMob)MobRegistry.getMob("fishcarpminion", getFirstAttackOwner().getLevel());

            switch (GameRandom.globalRandom.nextInt(5))
            {
                case 0:
                    mobFish = (AttackingFollowingMob)MobRegistry.getMob("fishcarpminion", getFirstAttackOwner().getLevel());
                    break;

                case 1:
                    mobFish = (AttackingFollowingMob)MobRegistry.getMob("fishherringminion", getFirstAttackOwner().getLevel());
                    break;

                case 2:
                    mobFish = (AttackingFollowingMob)MobRegistry.getMob("fishmackerelminion", getFirstAttackOwner().getLevel());
                    break;

                case 3:
                    mobFish = (AttackingFollowingMob)MobRegistry.getMob("fishsalmonminion", getFirstAttackOwner().getLevel());
                    break;

                case 4:
                    mobFish = (AttackingFollowingMob)MobRegistry.getMob("fishtroutminion", getFirstAttackOwner().getLevel());
                    break;
            }

            if (GameRandom.globalRandom.nextInt(100) == 10)
            {
                getFirstPlayerOwner().serverFollowersManager.addFollower("fishminion", mobFish, FollowPosition.PYRAMID, "summonedmob", 1.0F, (p) -> {return 3;}, (BiConsumer)null, false);
                mobFish.updateDamage(getDamage());
                mobFish.getLevel().entityManager.addMob(mobFish, mob.x, mob.y);
            }
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y) - this.texture.getHeight() / 2;
            float rotate = (float)(this.getWorldEntity().getTime() - this.spawnTime);
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(rotate, this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {public void draw(TickManager tickManager) {options.draw();}});
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), this.texture.getHeight() / 2);
        }
    }
}
