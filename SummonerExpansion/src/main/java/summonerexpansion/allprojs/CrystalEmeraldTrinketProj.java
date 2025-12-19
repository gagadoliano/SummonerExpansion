package summonerexpansion.allprojs;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.trails.Trail;
import necesse.level.maps.Level;

import java.awt.*;

public class CrystalEmeraldTrinketProj extends CrystalTrinketProj
{
    public CrystalEmeraldTrinketProj() {
    }

    public CrystalEmeraldTrinketProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
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

    public Color getParticleColor() {
        return new Color(82, 141, 61);
    }

    public Trail getTrail()
    {
        return new Trail(this, getLevel(), new Color(82, 141, 61), 12.0F, 200, getHeight());
    }
}