package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class FishSalmonMinion extends AttackingFollowingMob
{
    public static GameTexture texture;
    public int lifeTime = 0;
    public float moveAngle;
    private float toMove;

    public FishSalmonMinion()
    {
        super(10);
        setSpeed(40.0F);
        setFriction(1.0F);
        moveAccuracy = 10;
        collision = new Rectangle(0, 0, 28, 28);
        hitBox = new Rectangle(0, 0, 28, 28);
        selectBox = new Rectangle();
    }

    public GameDamage getCollisionDamage(Mob target) {
        return summonDamage;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        super.handleCollisionHit(target, damage, knockback);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI(600, summonDamage, 50, 800, 9000, 64));
    }

    public void tickMovement(float delta)
    {
        this.toMove += delta;
        while(this.toMove > 4.0F)
        {
            float oldX = this.x;
            float oldY = this.y;
            super.tickMovement(4.0F);
            this.toMove -= 4.0F;
            Point2D.Float d = GameMath.normalize(oldX - this.x, oldY - this.y);
            this.moveAngle = (float)Math.toDegrees(Math.atan2((double)d.y, (double)d.x)) - 90.0F;
        }
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime++;
        if (lifeTime >= 400)
        {
            remove(0.0F, 0.0F, (Attacker)null, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 10; ++i)
        {
            this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.COSMETIC).movesConstantAngle((float)GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(205, 94, 70));
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = texture.initDraw().light(light).rotate(this.moveAngle, 14, 14).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}
