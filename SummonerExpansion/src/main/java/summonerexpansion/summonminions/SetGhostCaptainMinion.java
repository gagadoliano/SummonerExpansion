package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.BooleanMobAbility;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class SetGhostCaptainMinion extends FlyingAttackingFollowingMob
{
    public int lifeTime = 0;
    public Trail trail;
    public float moveAngle;
    boolean isBroadsideAttacking;
    protected BooleanMobAbility chargeBroardsideAttack;

    public SetGhostCaptainMinion()
    {
        super(10);
        moveAccuracy = 15;
        setSpeed(50.0F);
        setFriction(2.0F);
        collision = new Rectangle(-18, -15, 36, 30);
        hitBox = new Rectangle(-18, -15, 36, 36);
        selectBox = new Rectangle();
        chargeBroardsideAttack = registerAbility(new BooleanMobAbility() { protected void run(boolean value) {}});
    }

    public void clientTick()
    {
        super.clientTick();
        Level level = getLevel();
        if (level.tickManager().getTotalTicks() % 2L == 0L)
        {
            level.entityManager.addParticle(x + (float)(GameRandom.globalRandom.nextGaussian() * (double)14.0F), y + (float)(GameRandom.globalRandom.nextGaussian() * (double)30.0F), Particle.GType.COSMETIC).movesConstant(dx / 10.0F, dy / 10.0F).color(new Color(176, 234, 190)).sizeFades(6, 10).height(42.0F);
        }
        level.lightManager.refreshParticleLightFloat(x, y, 200.0F, 0.6F, 10);
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime++;
        if (lifeTime >= 300)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI(this, new PlayerFollowerChaserAI<SetGhostCaptainMinion>(576, 320, false, false, 9000, 100)
        {
            public boolean attackTarget(SetGhostCaptainMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("ghostskull", mob.getLevel(), mob.x, mob.y, target.x, target.y, 110.0F, 640, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(20.0F);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }, new FlyingAIMover());
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 7; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.ghostShip, i, 10, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    protected SoundSettings getHitDeathSound()
    {
        return (new SoundSettings(GameResources.fadedeath3)).volume(0.5F).basePitch(1.5F);
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(GameMath.getTileCoordinate(x), GameMath.getTileCoordinate(y));
        float rotate = GameMath.limit(dx / 10.0F, -10.0F, 10.0F);
        int drawX = camera.getDrawX(x) - 64;
        int drawY = camera.getDrawY(y) - 80;
        drawY -= getFlyingHeight();
        int timePerFrame = 150;
        int spriteIndex = (int)(getWorldEntity().getTime() / (long)timePerFrame) % 4;
        final DrawOptions options = MobRegistry.Textures.ghostShip.initDraw().sprite(spriteIndex, isBroadsideAttacking ? 1 : 0, 128, 160).size(128, 160).rotate(rotate).light(light.minLevelCopy(150.0F)).pos(drawX, drawY).alpha(0.75F);
        topList.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }
}