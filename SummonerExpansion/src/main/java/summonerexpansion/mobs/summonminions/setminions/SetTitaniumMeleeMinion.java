package summonerexpansion.mobs.summonminions.setminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ability.BooleanMobAbility;
import necesse.entity.mobs.ai.behaviourTree.AINode;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.leaves.CooldownAttackTargetAINode;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerChargeChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.trails.Trail;
import necesse.entity.trails.TrailVector;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.*;

public class SetTitaniumMeleeMinion extends FlyingAttackingFollowingMob
{
    public Trail trail;
    public float moveAngle;
    public float currentAngle;
    private float toMove;
    protected boolean inAttackState;
    public BooleanMobAbility setAttackState;

    public SetTitaniumMeleeMinion()
    {
        super(10);
        moveAccuracy = 15;
        setSpeed(120.0F);
        setFriction(2.0F);
        collision = new Rectangle(-18, -15, 36, 30);
        hitBox = new Rectangle(-18, -15, 36, 36);
        selectBox = new Rectangle();
        setAttackState = registerAbility(new BooleanMobAbility()
        {
            protected void run(boolean value)
            {
                inAttackState = value;
            }
        });
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) {
        return summonDamage;
    }

    public int getCollisionKnockback(Mob target) {
        return 20;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
        }
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new TitaniumSwordAI<>(400, CooldownAttackTargetAINode.CooldownTimer.TICK, 1000, 100, 1500, 150));
        if (isClient())
        {
            trail = new Trail(this, getLevel(), new Color(140, 139, 139), 16.0F, 200, 0.0F);
            trail.drawOnTop = true;
            trail.removeOnFadeOut = false;
            getLevel().entityManager.addTrail(trail);
        }
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
            moveAngle = (float)Math.toDegrees(Math.atan2(d.y, d.x));
            updateCurrentAngle();
            if (trail != null)
            {
                float trailOffset = 5.0F;
                trail.addPoint(new TrailVector(x + d.x * trailOffset, y + d.y * trailOffset, -d.x, -d.y, trail.thickness, 0.0F));
            }
        }
    }

    public void clientTick()
    {
        super.clientTick();
        float particleAngle = currentAngle + 45.0F - 90.0F;
        getLevel().entityManager.addParticle(x, y, Particle.GType.COSMETIC).sprite(titaniumMeleeParticle.sprite(0, 0, 54)).color((options, lifeTime, timeAlive, lifePercent) ->
        {
            float rMod = (float)GameMath.lerp(lifePercent, 150, 200);
            float gMod = (float)GameMath.lerp(lifePercent, 150, 200);
            float bMod = (float)GameMath.lerp(lifePercent, 150, 200);
            options.color(new Color((int)(rMod), (int)(gMod), (int)(bMod)));
            options.alpha(1.0F - lifePercent);
        }).size((options, lifeTime, timeAlive, lifePercent) -> options.size(54, 54)).rotation((lifeTime, timeAlive, lifePercent) -> particleAngle).lifeTime(200);
    }

    private void updateCurrentAngle()
    {
        if (inAttackState && currentSpeed > 10.0F)
        {
            if (moveAngle > currentAngle && Math.abs(moveAngle - currentAngle) >= 180.0F)
            {
                currentAngle = Math.min(moveAngle, currentAngle - 2.0F) < -180.0F ? 180.0F : currentAngle - 2.0F;
            }
            else if (moveAngle < currentAngle && Math.abs(currentAngle - moveAngle) <= 180.0F)
            {
                currentAngle = Math.max(moveAngle, currentAngle - 2.0F) < -180.0F ? 180.0F : currentAngle - 2.0F;
            }
            else if (moveAngle > currentAngle && Math.abs(moveAngle - currentAngle) <= 180.0F)
            {
                currentAngle = Math.min(moveAngle, currentAngle + 2.0F) > 180.0F ? -180.0F : currentAngle + 2.0F;
            }
            else if (moveAngle < currentAngle && Math.abs(currentAngle - moveAngle) >= 180.0F)
            {
                currentAngle = Math.max(moveAngle, currentAngle + 2.0F) > 180.0F ? -180.0F : currentAngle + 2.0F;
            }
        }
        else
        {
            currentAngle = currentAngle + 2.0F > 180.0F ? -180.0F : currentAngle + 2.0F;
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), titaniumMeleeMinion, i, 2, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void playDeathSound()
    {
        SoundManager.playSound(GameResources.cling, SoundEffect.effect(this).volume(0.2F).pitch(1.5F));
        SoundManager.playSound(GameResources.crackdeath, SoundEffect.effect(this).volume(0.3F));
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 26;
        int drawY = camera.getDrawY(y) - 26;
        DrawOptions options = titaniumMeleeMinion.initDraw().sprite(0, 0, 54).light(light).rotate(currentAngle + 45.0F - 90.0F, 26, 26).pos(drawX, drawY);
        topList.add((tm) -> options.draw());
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected void addShadowDrawables(OrderableDrawables list, Level level, int x, int y, GameLight light, GameCamera camera)
    {
        int drawX = camera.getDrawX(x) - 26;
        int drawY = camera.getDrawY(y) - 26 + 10;
        final DrawOptions options = titaniumMeleeMinion_shadow.initDraw().sprite(0, 0, 54).light(light).rotate(currentAngle + 45.0F - 90.0F, 26, 26).pos(drawX, drawY);
        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager)
            {
                options.draw();
            }
        });
    }

    public void dispose()
    {
        super.dispose();
        if (trail != null)
        {
            trail.removeOnFadeOut = true;
        }
    }

    static class TitaniumSwordAI<T extends SetTitaniumMeleeMinion> extends PlayerFlyingFollowerChargeChaserAI<T>
    {
        public TitaniumSwordAI(int searchDistance, CooldownAttackTargetAINode.CooldownTimer cooldownTimer, int chargeCooldown, int targetStoppingDistance, int teleportDistance, int stoppingDistance)
        {
            super(searchDistance, cooldownTimer, chargeCooldown, targetStoppingDistance, teleportDistance, stoppingDistance);
        }

        public void onRootSet(AINode<T> root, T mob, Blackboard<T> blackboard)
        {
            super.onRootSet(root, mob, blackboard);
            blackboard.onGlobalTick((event) ->
            {
                boolean shouldBeInAttackState = blackboard.getObject(Mob.class, "currentTarget") != null;
                if (mob.inAttackState != shouldBeInAttackState)
                {
                    mob.setAttackState.runAndSend(shouldBeInAttackState);
                }
            });
        }
    }
}