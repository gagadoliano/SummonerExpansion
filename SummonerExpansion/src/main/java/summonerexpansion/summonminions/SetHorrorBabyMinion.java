package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerCirclingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
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

public class SetHorrorBabyMinion extends FlyingAttackingFollowingMob
{
    private float toMove;
    public float moveAngle;
    public static GameTexture texture;
    public int lifeTime = 9000;

    public SetHorrorBabyMinion()
    {
        super(10);
        setSpeed(60.0F);
        setFriction(2.0F);
        moveAccuracy = 15;
        collision = new Rectangle(-18, -15, 36, 30);
        hitBox = new Rectangle(-18, -15, 36, 36);
        selectBox = new Rectangle();
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public int getCollisionKnockback(Mob target) {
        return 15;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            this.collisionHitCooldowns.startCooldown(target);
            this.remove(0.0F, 0.0F, (Attacker)null, true);
        }
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI(this, new PlayerCirclingFollowerCollisionChaserAI(400, (GameDamage)null, 15, -1, 50), new FlyingAIMover());
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
            this.moveAngle = (float)Math.toDegrees(Math.atan2(d.y, d.x)) - 90.0F;
        }
    }

    public void serverTick()
    {
        super.serverTick();
        this.lifeTime--;
        if (this.lifeTime <= 0)
        {
            this.remove(0.0F, 0.0F, (Attacker)null, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 20; ++i)
        {
            this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.COSMETIC).movesConstantAngle((float)GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(10, 10, 10));
        }
    }

    public void playDeathSound()
    {
        SoundManager.playSound(GameResources.fadedeath1, SoundEffect.effect(this).volume(0.5F));
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 16;
        DrawOptions body = texture.initDraw().sprite(0, 0, 32).light(light).rotate(this.moveAngle, 16, 16).pos(drawX, drawY);
        int minLight = 100;
        DrawOptions eyes = texture.initDraw().sprite(1, 0, 32).light(light.minLevelCopy((float)minLight)).rotate(this.moveAngle, 16, 16).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
            eyes.draw();
        });
    }
}
