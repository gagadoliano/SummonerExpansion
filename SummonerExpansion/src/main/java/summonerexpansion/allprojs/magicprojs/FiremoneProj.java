package summonerexpansion.allprojs.magicprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
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
import java.awt.geom.Point2D;
import java.util.List;

public class FiremoneProj extends Projectile
{
    protected int childProjs;

    public FiremoneProj() {}

    public FiremoneProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, int childProjectiles)
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
        this.childProjs = childProjectiles;
    }

    public void init()
    {
        super.init();
        height = 20;
        heightBasedOnDistance = true;
        setWidth(20.0F);
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextShortUnsigned(childProjs);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        childProjs = reader.getNextShortUnsigned();
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        Mob owner = getOwner();
        if (owner != null && isServer() && mob != null)
        {
            int projectiles = 4;
            if (childProjs > 0)
            {
                --childProjs;
                float startX = x - dx * 2.0F;
                float startY = y - dy * 2.0F;
                float angle = (float) GameRandom.globalRandom.nextInt(360);
                for(int i = 0; i < projectiles; ++i)
                {
                    Point2D.Float dir = GameMath.getAngleDir(angle + (float)i * 360.0F / (float)projectiles);
                    FiremoneProj projectile = new FiremoneProj(getLevel(), getOwner(), startX, startY, startX + dir.x * 100.0F, startY + dir.y * 100.0F, speed, 600, getDamage().modFinalMultiplier(0.50F), knockback, childProjs);
                    if (modifier != null)
                    {
                        modifier.initChildProjectile(projectile, 1.0F, projectiles / 2);
                    }
                    projectile.startHitCooldown(mob);
                    getLevel().entityManager.projectiles.add(projectile);
                }
            }
        }
    }

    public Trail getTrail()
    {
        return new Trail(this, getLevel(), new Color(225, 58, 1), 10.0F, 250, 18.0F);
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