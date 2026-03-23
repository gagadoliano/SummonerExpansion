package summonerexpansion.mobs.minions.sentry;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SentryBase;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMinionTextures.caveglowSentry;

public class CaveglowSentry extends SentryBase
{
    public float moveAngle;

    public CaveglowSentry()
    {
        super(3000F, 1000F);
        collision = new Rectangle(0, 0, 30, 68);
        hitBox = new Rectangle(0, 0, 30, 68);
        selectBox = new Rectangle();
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<CaveglowSentry>(900, 900, false, false, 90000, 64)
        {
            public boolean attackTarget(CaveglowSentry mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("caveglowproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, (80.0F * projVel), 900, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(20.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void clientTick()
    {
        super.clientTick();
        getLevel().lightManager.refreshParticleLightFloat(x, y, 180.0F, 0.5F, 165);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 20; ++i)
        {
            getLevel().entityManager.addParticle(x, y, Particle.GType.COSMETIC).movesConstantAngle((float) GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(85, 182, 125));
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = caveglowSentry.initDraw().light(light).rotate(moveAngle, 15, 30).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}