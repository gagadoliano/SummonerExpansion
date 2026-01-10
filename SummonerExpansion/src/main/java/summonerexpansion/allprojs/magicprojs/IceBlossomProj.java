package summonerexpansion.allprojs.magicprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.TrainingDummyMob;
import necesse.entity.projectile.Projectile;
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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class IceBlossomProj extends Projectile
{
    protected HashSet<Integer> ignoredTargets = new HashSet<>();
    protected int remainingRicochets;

    public IceBlossomProj() {}

    public IceBlossomProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, int ricochets)
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
        this.remainingRicochets = ricochets;
    }

    public void init()
    {
        super.init();
        height = 26;
        heightBasedOnDistance = true;
        setWidth(32.0F);
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextShortUnsigned(ignoredTargets.size());
        for(Integer ignoredTarget : ignoredTargets)
        {
            writer.putNextInt(ignoredTarget);
        }
        writer.putNextShortUnsigned(remainingRicochets);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        int ignoredTargetsTotal = reader.getNextShortUnsigned();
        for(int i = 0; i < ignoredTargetsTotal; ++i)
        {
            ignoredTargets.add(reader.getNextInt());
        }
        remainingRicochets = reader.getNextShortUnsigned();
    }

    public boolean canHit(Mob mob)
    {
        return !ignoredTargets.contains(mob.getUniqueID()) && super.canHit(mob);
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        Mob owner = getOwner();
        if (mob != null && owner != null)
        {
            ignoredTargets.add(mob.getUniqueID());
            if (isServer() && remainingRicochets > 0)
            {
                Mob ricochetTarget = getValidRicochetTarget(mob);
                if (ricochetTarget != null)
                {
                    --remainingRicochets;
                    IceBlossomProj projectile = new IceBlossomProj(getLevel(), getOwner(), x, y, ricochetTarget.x, ricochetTarget.y, speed, 600, getDamage(), knockback, remainingRicochets);
                    if (modifier != null)
                    {
                        modifier.initChildProjectile(projectile, 1.0F, 1);
                    }
                    projectile.setTargetPrediction(ricochetTarget);
                    projectile.ignoredTargets.addAll(ignoredTargets);
                    projectile.ignoredTargets.add(mob.getUniqueID());
                    getLevel().entityManager.projectiles.add(projectile);
                    setAngle(getAngleToTarget(x, y, ricochetTarget.x, ricochetTarget.y));
                }
            }
        }

    }

    private Mob getValidRicochetTarget(Mob mob)
    {
        int checkForMobsRange = 200;
        Mob owner = getOwner();
        return GameUtils.streamTargets(getOwner(), GameUtils.rangeBounds(x, y, checkForMobsRange)).filter((m) -> owner.isHostile || m.isHostile || m instanceof TrainingDummyMob).filter((m) -> !ignoredTargets.contains(m.getUniqueID())).filter((m) -> m.getDistance(mob) <= (float)checkForMobsRange).min(Comparator.comparing((m) -> m.getDistance(x, y))).orElse(null);
    }

    public Trail getTrail() {
        return new Trail(this, getLevel(), new Color(36, 174, 214), 10.0F, 250, 18.0F);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y);
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(getAngle(), texture.getWidth() / 2, 0).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this) {public void draw(TickManager tickManager) {
                    options.draw();
                }});
            addShadowDrawables(tileList, drawX, drawY, light, getAngle(), 0);
        }
    }
}
