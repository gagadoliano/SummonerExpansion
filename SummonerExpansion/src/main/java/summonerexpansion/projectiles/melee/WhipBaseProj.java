package summonerexpansion.projectiles.melee;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.util.GameMath;
import necesse.engine.util.tween.Easings;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.pathProjectile.PathProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import summonerexpansion.codes.events.ShowWhipAttackEvent;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class WhipBaseProj extends PathProjectile
{
    public Point2D.Float dir;
    public boolean isUpgraded;
    public float frontAttackRange;
    public Color whipColor;

    public WhipBaseProj() {
    }

    public WhipBaseProj(Mob owner, int targetX, int targetY, int animTime, float frontAttackRange, GameDamage damage, int knockback, Color whipColor, boolean isUpgraded)
    {
        this.x = owner.x;
        this.y = owner.y;
        this.dir = GameMath.normalize((float)targetX - owner.x, (float)targetY - owner.y);
        int distance = 1000;
        this.speed = Projectile.getTravelSpeedForMillis(animTime, (float)distance);
        this.setDistance(distance);
        this.frontAttackRange = frontAttackRange;
        this.setDamage(damage);
        this.knockback = knockback;
        this.setOwner(owner);
        this.whipColor = whipColor;
        this.isUpgraded = isUpgraded;
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextFloat(this.frontAttackRange);
        writer.putNextFloat(this.dir.x);
        writer.putNextFloat(this.dir.y);
        writer.putNextBoolean(this.isUpgraded);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        this.frontAttackRange = reader.getNextFloat();
        float dirX = reader.getNextFloat();
        float dirY = reader.getNextFloat();
        this.dir = new Point2D.Float(dirX, dirY);
        this.isUpgraded = reader.getNextBoolean();
    }

    public void init()
    {
        super.init();
        this.isSolid = false;
        this.maxMovePerTick = 12;
        this.height = 0.0F;
        this.piercing = 1000;
        this.setWidth(this.isUpgraded ? 40.0F : 20.0F);
        if (!this.isServer())
        {
            this.getLevel().entityManager.events.addHidden(new ShowWhipAttackEvent(this, 100, whipColor));
        }
    }

    public Trail getTrail() {
        return null;
    }

    public Point2D.Float getPosition(double dist)
    {
        Mob owner = this.getOwner();
        if (owner != null && this.dir != null)
        {
            float currentAttackProgress = this.traveledDistance / (float)this.distance;
            return getWhipPositionFinal(owner.x, owner.y, this.dir, this.frontAttackRange, currentAttackProgress);
        }
        else
        {
            return new Point2D.Float(this.x, this.y);
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
    }

    public static Point2D.Float getWhipPositionFloat(float progress)
    {
        float startX = progress;
        float firstPart = 0.66F;
        if (progress <= firstPart)
        {
            progress *= 1.0F / firstPart;
            progress = Easings.CubicIn.ease(progress);
            return new Point2D.Float(progress, Easings.QuartIn.ease(progress) / 2.0F - startX);
        }
        else
        {
            progress = (progress - firstPart) * (1.0F / (1.0F - firstPart));
            progress = Easings.CubicOut.ease(progress);
            return new Point2D.Float(1.0F - progress / 2.0F, 1.0F - Easings.QuartIn.ease(1.0F - progress) / 2.0F - startX);
        }
    }

    public static WhipPosition getWhipPositionFinal(float startX, float startY, Point2D.Float attackDir, float frontAttackRange, float progress)
    {
        boolean isSecondPart = false;
        float originalProgress = progress;
        if ((double)progress < (double)0.5F)
        {
            progress = 1.0F - progress * 2.0F;
        }

        Point2D.Float currentPos = getWhipPositionFloat(progress);
        float forwardRange = GameMath.lerp(progress, frontAttackRange, Math.max(1.0F, frontAttackRange - frontAttackRange / 4.0F));
        float perpRange = GameMath.lerp(progress, Math.max(1.0F, frontAttackRange - frontAttackRange / 5.0F), frontAttackRange) / 3.0F;
        float currentX = startX + currentPos.x * attackDir.x * forwardRange;
        float currentY = startY + currentPos.x * attackDir.y * forwardRange;
        Point2D.Float perpDir = GameMath.getPerpendicularDir(attackDir);
        currentX += currentPos.y * perpDir.x * perpRange * (float)(attackDir.x < 0.0F ? 1 : -1);
        currentY += currentPos.y * perpDir.y * perpRange * (float)(attackDir.x < 0.0F ? 1 : -1);
        return new WhipPosition(currentX, currentY, attackDir, attackDir, progress, originalProgress, isSecondPart);
    }

    public static class WhipPosition extends Point2D.Float
    {
        public Point2D.Float currentAttackDir;
        public Point2D.Float originalAttackDir;
        public float currentAttackProgress;
        public float originalAttackProgress;
        public boolean isSecondPart;

        public WhipPosition(float x, float y, Point2D.Float currentAttackDir, Point2D.Float originalAttackDir, float currentAttackProgress, float originalAttackProgress, boolean isSecondPart)
        {
            super(x, y);
            this.currentAttackDir = currentAttackDir;
            this.originalAttackDir = originalAttackDir;
            this.currentAttackProgress = currentAttackProgress;
            this.originalAttackProgress = originalAttackProgress;
            this.isSecondPart = isSecondPart;
        }
    }
}