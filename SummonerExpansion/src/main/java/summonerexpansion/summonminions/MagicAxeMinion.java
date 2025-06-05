package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class MagicAxeMinion extends FlyingAttackingFollowingMob
{
    public static GameTexture texture;
    public float moveAngle;
    public int woodHit;
    private float toMove;

    public MagicAxeMinion()
    {
        super(10);
        setSpeed(40.0F);
        setFriction(1.0F);
        moveAccuracy = 10;
        collision = new Rectangle(6, 6, 18, 16);
        hitBox = new Rectangle(6, 6, 18, 16);
        selectBox = new Rectangle();
    }

    public GameDamage getCollisionDamage(Mob target) {
        return summonDamage;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null)
        {
            ActiveBuff buff = new ActiveBuff(BuffRegistry.getBuff("woodtoolbuff"), target, 60F, this);
            owner.buffManager.addBuff(buff, true);

            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);

            woodHit++;
            if (woodHit >= 10)
            {
                remove(0.0F, 0.0F, null, true);
            }
        }
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI(this, new PlayerFlyingFollowerCollisionChaserAI(400, null, 35, 500, 800, 70), new FlyingAIMover());
    }

    public void tickMovement(float delta)
    {
        toMove += delta;
        while(toMove > 4.0F)
        {
            float oldX = x;
            float oldY = y;
            super.tickMovement(4.0F);
            toMove -= 4.0F;
            Point2D.Float d = GameMath.normalize(oldX - x, oldY - y);
            moveAngle = (float)Math.toDegrees(Math.atan2(d.y, d.x)) - 90.0F;
        }
    }

    public void clientTick()
    {
        super.clientTick();
        this.getLevel().entityManager.addParticle(x + (float)(GameRandom.globalRandom.nextGaussian() * 4.0), y + (float)(GameRandom.globalRandom.nextGaussian() * 4.0), Particle.GType.IMPORTANT_COSMETIC).movesConstant(this.dx / 10.0F, this.dy / 10.0F).color(new Color(226, 147, 0));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 30; ++i)
        {
            this.getLevel().entityManager.addParticle(x, y, Particle.GType.COSMETIC).sprite(GameResources.bubbleParticle.sprite(0, 0, 12)).movesConstantAngle((float) GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(226, 147, 0));
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = texture.initDraw().light(light).rotate(moveAngle, 14, 14).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}