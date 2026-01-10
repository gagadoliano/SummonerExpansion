package summonerexpansion.mobs.summonminions.setminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.SingleSpiritBeamLevelEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ability.BooleanMobAbility;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.pathProjectile.StaticJellyfishProjectile;
import necesse.entity.trails.Trail;
import necesse.entity.trails.TrailVector;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.codes.events.SnowmanSetLevelEvent;
import summonerexpansion.codes.events.TitaniumLightningLevelEvent;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.*;

public class SetTitaniumMagicMinion extends FlyingAttackingFollowingMob
{
    public Trail trail;
    public float moveAngle;
    public float currentAngle;
    private float toMove;
    protected boolean inAttackState;
    public BooleanMobAbility setAttackState;

    public SetTitaniumMagicMinion()
    {
        super(10);
        moveAccuracy = 15;
        setSpeed(120.0F);
        setFriction(2.0F);
        attackCooldown = 1200;
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

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<SetTitaniumMagicMinion>(500, 300, false, false, 900, 150)
        {
            public boolean attackTarget(SetTitaniumMagicMinion mob, Mob target)
            {
                if (canAttack() && !isOnGenericCooldown("attackCooldown"))
                {
                    Point targetPoints = new Point((int)target.x, (int)target.y);
                    TitaniumLightningLevelEvent event = new TitaniumLightningLevelEvent(getAttackOwner(), new GameRandom(10), targetPoints, summonDamage, 0.75F);
                    getLevel().entityManager.events.add(event);
                    startGenericCooldown("attackCooldown", attackCooldown);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), titaniumMagicMinion, i, 2, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
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
        DrawOptions options = titaniumMagicMinion.initDraw().sprite(0, 0, 54).light(light).rotate(currentAngle + 45.0F - 90.0F, 26, 26).pos(drawX, drawY);
        topList.add((tm) -> options.draw());
    }

    public void dispose()
    {
        super.dispose();
        if (trail != null)
        {
            trail.removeOnFadeOut = true;
        }
    }
}