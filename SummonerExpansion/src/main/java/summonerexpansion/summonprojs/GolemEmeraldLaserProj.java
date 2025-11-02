package summonerexpansion.summonprojs;

import necesse.entity.projectile.laserProjectile.LaserProjectile;
import necesse.entity.trails.Trail;
import java.awt.*;

public class GolemEmeraldLaserProj extends LaserProjectile
{
    public GolemEmeraldLaserProj() {}

    public void init()
    {
        super.init();
        this.givesLight = true;
        this.height = 18.0F;
        this.bouncing = 1000;
        this.piercing = 1000;
    }

    protected int getExtraSpinningParticles() {
        return super.getExtraSpinningParticles() + 3;
    }

    public Color getParticleColor() {
        return new Color(96, 164, 61);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(96, 164, 61), 12.0F, 500, 18.0F);
    }
}